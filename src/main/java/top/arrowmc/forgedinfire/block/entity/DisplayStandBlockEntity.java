package top.arrowmc.forgedinfire.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import top.arrowmc.forgedinfire.ModRegister;

public class DisplayStandBlockEntity extends BlockEntity {

    private final ItemStackHandler inventory = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide) {
                level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
            };

        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    private static int age;

    private static int SLOT = 0;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public DisplayStandBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }
    public DisplayStandBlockEntity(BlockPos pPos, BlockState pBlockState) {
        this(ModRegister.DISPLAY_STAND_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public ItemStack getDisplayStack() {
        return inventory.getStackInSlot(SLOT);
    }

    public void playerInteract(Player player, Level pLevel, InteractionHand pHand) {
        if (pHand == InteractionHand.MAIN_HAND) {
            if (player.getMainHandItem().isEmpty()) {
                inventory.setStackInSlot(SLOT, ItemStack.EMPTY);
                return;
            }
            ItemStack itemStack = player.getMainHandItem().copy();
            itemStack.setCount(1);
            inventory.setStackInSlot(SLOT, itemStack);

        }
    }

    private ItemStack getItemStack() {
        return inventory.getStackInSlot(0);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, DisplayStandBlockEntity pBlockEntity){
        ItemStack itemStack = pBlockEntity.getItemStack();
        if (!itemStack.isEmpty()) {
            CompoundTag compoundTag = itemStack.getOrCreateTag();
            if (compoundTag.contains("age")) {
                int age =  compoundTag.getInt("age");
                if (age++ > 3600) {
                    age = 0;
                }
                compoundTag.putInt("age", age);
            }else {
                compoundTag.putInt("age", 0);
            }
            pLevel.sendBlockUpdated(pPos,pBlockEntity.getBlockState(),pBlockEntity.getBlockState(),3);
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
        lazyItemHandler = LazyOptional.of(() -> inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", inventory.serializeNBT());
        super.saveAdditional(pTag);
    }


    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        inventory.deserializeNBT(pTag.getCompound("inventory"));
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }
}
