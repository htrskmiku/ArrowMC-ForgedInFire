package top.arrowmc.forgedinfire;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.arrowmc.forgedinfire.block.ForgingAnvilBlock;
import top.arrowmc.forgedinfire.block.entity.ForgingAnvilBlockEntity;
import top.arrowmc.forgedinfire.item.ForgingHammerItem;
import top.arrowmc.forgedinfire.item.ForgingPincerItem;
import top.arrowmc.forgedinfire.item.HotIronItem;
import top.arrowmc.forgedinfire.recipe.ForgingRecipe;

public class ModRegister {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ForgedInFire.MODID);
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ForgedInFire.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ForgedInFire.MODID);
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ForgedInFire.MODID);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(Registries.RECIPE_SERIALIZER,ForgedInFire.MODID);

    public static RegistryObject<CreativeModeTab> CREATIVE_TAB;

    public static RegistryObject<Block> FORGING_ANVIL_BLOCK;
    public static RegistryObject<BlockItem> FORGING_ANVIL_ITEM;
    public static RegistryObject<BlockEntityType<ForgingAnvilBlockEntity>> FORGING_ANVIL_BLOCK_ENTITY;

    public static RegistryObject<Item> FORGING_HAMMER;

    public static RegistryObject<Item> FORGING_SCRAP;
    public static RegistryObject<Item> FORGING_PINCER;
    public static RegistryObject<Item> HOT_IRON_ITEM;

    public static RegistryObject<RecipeSerializer<ForgingRecipe>> FORGING_RECIPE;

    static {
        FORGING_ANVIL_BLOCK = BLOCKS.register("forging_anvil",() -> new ForgingAnvilBlock(BlockBehaviour.Properties.of().strength(5.0F,1200.0F).requiresCorrectToolForDrops().mapColor(MapColor.METAL)));
        FORGING_ANVIL_BLOCK_ENTITY = BLOCK_ENTITIES.register("forging_anvil",() -> BlockEntityType.Builder.of(ForgingAnvilBlockEntity::new,FORGING_ANVIL_BLOCK.get()).build(null));
        FORGING_ANVIL_ITEM = ITEMS.register("forging_anvil",() -> new BlockItem(FORGING_ANVIL_BLOCK.get(),new Item.Properties()));
        FORGING_HAMMER = ITEMS.register("forging_hammer",()-> new ForgingHammerItem(new Item.Properties().stacksTo(1)));
        FORGING_SCRAP = ITEMS.register("forging_scrap",()-> new Item(new Item.Properties().stacksTo(64)));
        FORGING_PINCER = ITEMS.register("forging_pincer",()-> new ForgingPincerItem(new Item.Properties().stacksTo(1)));
        HOT_IRON_ITEM = ITEMS.register("hot_iron",() -> new HotIronItem(new Item.Properties().stacksTo(64)));

        FORGING_RECIPE = RECIPES.register("forging",() -> ForgingRecipe.Serializer.INSTANCE);

        CREATIVE_TAB = CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder().withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> FORGING_ANVIL_ITEM.get().getDefaultInstance())
                .title(Component.translatable("top.arrowmc.forgedinfire.maintab"))
                .displayItems((parameters, output) -> {
            output.accept(FORGING_ANVIL_ITEM.get());
            output.accept(FORGING_HAMMER.get());
            output.accept(FORGING_SCRAP.get());
            output.accept(FORGING_PINCER.get());
            output.accept(HOT_IRON_ITEM.get());
        }).build());

    }

    public static void register(IEventBus bus){
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        RECIPES.register(bus);
        CREATIVE_MODE_TABS.register(bus);
    }
}
