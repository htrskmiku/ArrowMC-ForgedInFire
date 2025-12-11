package top.arrowmc.forgedinfire.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.arrowmc.forgedinfire.Config;
import top.arrowmc.forgedinfire.ModRegister;
import top.arrowmc.forgedinfire.ModUtils;
import top.arrowmc.forgedinfire.kubejs.ForgingAnvilCraftedEventJS;
import top.arrowmc.forgedinfire.kubejs.ForgingAnvilCraftingEventJS;
import top.arrowmc.forgedinfire.kubejs.KubeJSIntegration;
import top.arrowmc.forgedinfire.quality.MaterialQualityManager;
import top.arrowmc.forgedinfire.recipe.ForgingRecipe;
import top.arrowmc.forgedinfire.tags.ModTags;

import java.util.Objects;
import java.util.Optional;

public class ForgingAnvilBlockEntity extends BlockEntity implements WorldlyContainer {

    private final ItemStackHandler itemStackHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int INGREDIENT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public ForgingAnvilBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }
    public ForgingAnvilBlockEntity(BlockPos worldPosition, BlockState blockState) {
        this(ModRegister.FORGING_ANVIL_BLOCK_ENTITY.get(), worldPosition, blockState);
    }

    public ItemStackHandler getItemStackHandler() {
        return itemStackHandler;
    }

    public void playerInteract(Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand) {
        if (pHand != InteractionHand.MAIN_HAND) {
            return;
        }
        if(!pPlayer.getMainHandItem().is(ModRegister.FORGING_HAMMER.get())) {
            if (pPlayer.getItemInHand(pHand).isEmpty()) {
                for (int i = itemStackHandler.getSlots() - 1; i >= 0; i--) {
                    if (!itemStackHandler.getStackInSlot(i).isEmpty()) {
                        pPlayer.setItemSlot(EquipmentSlot.MAINHAND, itemStackHandler.getStackInSlot(i));
                        itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
                        pLevel.playSound(null,pPos.getX(),pPos.getY(),pPos.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,0.1f,1.2f);
                        setChanged();
                        break;
                    }
                }
            } else {
                for (int i = 0; i < 2; i++) {
                    if (itemStackHandler.getStackInSlot(i).isEmpty()) {

                        putItemInSlot(pPlayer.getMainHandItem(), pPlayer.isCreative(), i);
                        setChanged();
                        break;
                    }
                }
            }
        }else {

            boolean completed = processCrafting(pPlayer,pLevel);
            if (completed) {
                setChanged();
                pLevel.playSound(null,pPos.getX(),pPos.getY(),pPos.getZ(), SoundEvents.ANVIL_USE, SoundSource.BLOCKS,0.3f,0.8f);
                if (!level.isClientSide) {
                    addFlameParticle(pLevel,pPos);
                }

            }else {
                pLevel.playSound(null,pPos.getX(),pPos.getY(),pPos.getZ(), SoundEvents.ANVIL_LAND, SoundSource.BLOCKS,0.3f,0.8f);
            }
        }


    }

    private void addFlameParticle(Level pLevel,BlockPos pPos) {
        ServerLevel serverlevel = (ServerLevel)pLevel;
        double x = pPos.getX() + 0.5;
        double y = pPos.getY() + 1;
        double z = pPos.getZ() + 0.5;
        serverlevel.sendParticles(ParticleTypes.FLAME,x,y,z,20,0D,0D,0D,0.2D);
    }

    /**
     * 处理锻造基材（带NBT合成）
     * @param pLevel
     * @return
     */
    private boolean processForgingCrafting(Level pLevel) {
        ItemStack refiningSlotStack = itemStackHandler.getStackInSlot(INGREDIENT_SLOT);
        ItemStack inputSlotStack = itemStackHandler.getStackInSlot(INPUT_SLOT);
        if (refiningSlotStack.is(ModTags.Items.REINFORCE_MATERIAL)) {
            double brokenProbability = getMaterialBrokenProbability(inputSlotStack);
            double p = pLevel.getRandom().nextDouble();
            ItemStack output;
            if (p < brokenProbability) {
                BlockPos pos = this.getBlockPos();
                output = new ItemStack(ModRegister.FORGING_SCRAP.get());
                if (inputSlotStack.hasTag()){
                    output.setTag(inputSlotStack.getTag());
                }
                output.setCount(1);
                pLevel.playSeededSound(null,pos.getX(),pos.getY(),pos.getZ(),SoundEvents.ITEM_BREAK,SoundSource.BLOCKS,0.3f,1.0f,1);
            }else {
                output = inputSlotStack.copy();
                CompoundTag outputTag = output.getOrCreateTag();
                ListTag listTag;
                int refineCounts = 1;
                if (outputTag.contains("refine_counts")){
                    refineCounts = outputTag.getInt("refine_counts");
                    refineCounts ++;
                    outputTag.putInt("refine_counts",refineCounts);
                }else {
                    outputTag.putInt("refine_counts",refineCounts);
                }

                double materialQuality = MaterialQualityManager.getQuality(refiningSlotStack.getItem());
                if (materialQuality > 0) {
                    double quality = 0;
                    if (outputTag.contains("quality")){
                        quality = outputTag.getDouble("quality");
                    }
                    double a = ((pLevel.getRandom().nextDouble()-0.5) * materialQuality);
                    quality += a;
                    if (quality > 1D) {
                        quality = 1D;
                    }
                    if (quality < 0D) {
                        quality = 0;
                    }
                    outputTag.putDouble("quality",quality);
                }

                if (outputTag.contains("refine_materials")){
                    listTag = outputTag.getList("refine_materials", Tag.TAG_COMPOUND);
                    CompoundTag materialTag = new CompoundTag();
                    materialTag.putString("material",ModUtils.getItemResourceLocation(refiningSlotStack.getItem()));
                    listTag.add(materialTag);
                }else {
                    listTag = new ListTag();
                    CompoundTag materialTag = new CompoundTag();
                    materialTag.putString("material",ModUtils.getItemResourceLocation(refiningSlotStack.getItem()));
                    listTag.add(materialTag);
                    outputTag.put("refine_materials",listTag);
                }
            }

            //output.setTag(outputTag);
            itemStackHandler.setStackInSlot(OUTPUT_SLOT, output);
            itemStackHandler.extractItem(INPUT_SLOT, 1, false);
            itemStackHandler.extractItem(INGREDIENT_SLOT, 1, false);
            return true;
        }
        return false;
    }

    /**
     * 处理合成
     * @param pPlayer
     * @param pLevel
     * @return
     */
    private boolean processCrafting(Player pPlayer,Level pLevel){

        boolean canRecipeCraft = canCraft();
        NonNullList<ItemStack> items = NonNullList.withSize(2,ItemStack.EMPTY);
        for(int i = 0; i < items.size(); i++){
            items.set(i, itemStackHandler.getStackInSlot(i));
        }//获取物品Slot List

        if(ModUtils.isKubeJSLoaded()){
            ItemStack outputStack = itemStackHandler.getStackInSlot(OUTPUT_SLOT);
            ForgingAnvilCraftingEventJS eventJS = new ForgingAnvilCraftingEventJS(pPlayer,items,canRecipeCraft,outputStack);
            if (KubeJSIntegration.FORGING_ANVIL_CRAFTING_EVENT.hasListeners()){
                KubeJSIntegration.FORGING_ANVIL_CRAFTING_EVENT.post(eventJS);
            }
        }//检测KubeJS是否存在

        if (itemStackHandler.getStackInSlot(INPUT_SLOT).is(ModRegister.FORGING_BASE_MATERIAL.get())) {
            if (processForgingCrafting(pLevel)){
                ItemStack itemStack = itemStackHandler.getStackInSlot(OUTPUT_SLOT);
                if (ModUtils.isKubeJSLoaded()){
                    ForgingAnvilCraftedEventJS eventJS = new ForgingAnvilCraftedEventJS(pPlayer,items,itemStack);
                    if (KubeJSIntegration.FORGING_ANVIL_CRAFTED_EVENT.hasListeners()){
                        KubeJSIntegration.FORGING_ANVIL_CRAFTED_EVENT.post(eventJS);
                    }
                }
                return true;
            }else {
                return false;
            }
        }

        if (canRecipeCraft){
            Optional<ForgingRecipe> recipeOptional = getCurrentRecipe();
            ItemStack outputStack = recipeOptional.get().getResultItem(null);
            double brokenProbability = recipeOptional.get().getBrokenProbability();
            NonNullList<Ingredient> inputs = recipeOptional.get().getIngredients();
            itemStackHandler.extractItem(INPUT_SLOT, 1, false);
            if(!inputs.get(INGREDIENT_SLOT).isEmpty()){
                itemStackHandler.extractItem(INGREDIENT_SLOT, 1, false);
            }
            if (pLevel.getRandom().nextDouble() < brokenProbability) {
                ItemStack scrapItemStack = new ItemStack(ModRegister.FORGING_SCRAP.get(),1);
                itemStackHandler.setStackInSlot(OUTPUT_SLOT, scrapItemStack);
            }else {
                itemStackHandler.setStackInSlot(OUTPUT_SLOT, outputStack.copyWithCount(1));
            }

            NonNullList<ItemStack> inputStacks = NonNullList.withSize(2,ItemStack.EMPTY);
            for(int i = 0; i < inputs.size(); i++){
                inputStacks.set(i,itemStackHandler.getStackInSlot(i));
            }

            if (ModUtils.isKubeJSLoaded()){
                ForgingAnvilCraftedEventJS eventJS = new ForgingAnvilCraftedEventJS(pPlayer,inputStacks,outputStack);
                if (KubeJSIntegration.FORGING_ANVIL_CRAFTED_EVENT.hasListeners()){
                    KubeJSIntegration.FORGING_ANVIL_CRAFTED_EVENT.post(eventJS);
                }
            }

            return true;
        }
        return false;
    }


    public NonNullList<ItemStack> getItemStacks() {

        NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            items.set(i, itemStackHandler.getStackInSlot(i));
        }
        return items;
    }

    public void putItemInSlot(ItemStack stack,boolean isCreative,int slot){
        if (stack.isEmpty()){return;}
        ItemStack itemPutIn = stack.copy();
        itemPutIn.setCount(1);
        if (!isCreative){
            stack.shrink(1);
        }
        this.itemStackHandler.setStackInSlot(slot, itemPutIn);

    }

    private Optional<ForgingRecipe> getCurrentRecipe(){
        SimpleContainer container = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            container.setItem(i, itemStackHandler.getStackInSlot(i));
        }
        return this.level.getRecipeManager().getRecipeFor(ForgingRecipe.Type.INSTANCE, container, level);
    }

    private boolean hasRecipe(){
        Optional<ForgingRecipe> recipe = getCurrentRecipe();
        if (recipe.isPresent()){
            return true;
        }
        return false;
    }

    private boolean canCraft(){
        return hasRecipe() && canInsertIntoOutputSlot();
    }

    private boolean canInsertIntoOutputSlot() {
        return this.itemStackHandler.getStackInSlot(OUTPUT_SLOT).isEmpty();
    }

    private double getMaterialBrokenProbability(ItemStack inputStack){
        CompoundTag nbt = inputStack.getOrCreateTag();
        if (nbt.contains("refine_counts")){
            return nbt.getInt("refine_counts") * Config.brokenProbabilityIncrease + Config.forgingBrokenProbability;
        }else {
            return 0;
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemStackHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemStackHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    public void dropStacks(){
        SimpleContainer container = new SimpleContainer(itemStackHandler.getSlots());
        for(int slot = 0; slot < itemStackHandler.getSlots(); slot++){
            container.setItem(slot,itemStackHandler.getStackInSlot(slot));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        return new int[]{2};//仅输出Slot2
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        if(pDirection == Direction.DOWN && pIndex == OUTPUT_SLOT){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int getContainerSize() {
        return itemStackHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < itemStackHandler.getSlots(); i++) {
            if (!itemStackHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return itemStackHandler.getStackInSlot(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        ItemStack itemstack = Objects.requireNonNullElse(this.itemStackHandler.getStackInSlot(pSlot), ItemStack.EMPTY);
        this.itemStackHandler.setStackInSlot(pSlot, ItemStack.EMPTY);
        itemstack.setCount(pAmount);
        return itemstack.copy();
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return itemStackHandler.getStackInSlot(pSlot).copyAndClear();
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        itemStackHandler.setStackInSlot(pSlot, pStack);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        for(int slot = 0; slot < itemStackHandler.getSlots(); slot++){
            itemStackHandler.setStackInSlot(slot, ItemStack.EMPTY);
        }
    }
}
