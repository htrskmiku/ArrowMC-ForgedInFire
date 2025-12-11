package top.arrowmc.forgedinfire;

import com.mojang.logging.LogUtils;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import top.arrowmc.forgedinfire.block.render.DisplayStandBlockEntityRender;
import top.arrowmc.forgedinfire.block.render.ForgingAnvilBlockEntityRender;
import top.arrowmc.forgedinfire.datagen.*;
import top.arrowmc.forgedinfire.datagen.lang.EnUsLangGenerator;
import top.arrowmc.forgedinfire.datagen.lang.ZhCnLangGenerator;
import top.arrowmc.forgedinfire.ponder.ModPonder;

import java.util.concurrent.CompletableFuture;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ForgedInFire.MODID)
public class ForgedInFire {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "forgedinfire";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "forgedinfire" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "forgedinfire" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "forgedinfire" namespace

    // Creates a new food item with the id "forgedinfire:example_id", nutrition 1 and saturation 2
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(2f).build())));

    // Creates a creative tab with the id "forgedinfire:example_tab" for the example item, that is placed after the combat tab


    @SuppressWarnings("removal")
    public ForgedInFire() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModRegister.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        //noinspection removal
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            PonderIndex.addPlugin(new ModPonder());
        }

        @SubscribeEvent
        public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModRegister.FORGING_ANVIL_BLOCK_ENTITY.get(), ForgingAnvilBlockEntityRender::new);
            event.registerBlockEntityRenderer(ModRegister.DISPLAY_STAND_BLOCK_ENTITY.get(), DisplayStandBlockEntityRender::new);
        }
    }

    @Mod.EventBusSubscriber(modid = MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModDatagenEvents {
        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
            ModBlockTagsProvider blockTagsProvider= generator.addProvider(event.includeServer(),new ModBlockTagsProvider(output,lookupProvider,existingFileHelper));
            generator.addProvider(event.includeServer(),new ModItemModelGenerator(output,existingFileHelper));
            generator.addProvider(event.includeServer(),new ModItemTagsGenerator(output,lookupProvider,blockTagsProvider.contentsGetter(),existingFileHelper));
            generator.addProvider(event.includeServer(),new ModRecipeGenerator(output));
            generator.addProvider(event.includeServer(),new EnUsLangGenerator(output));
            generator.addProvider(event.includeServer(),new ZhCnLangGenerator(output));
            generator.addProvider(event.includeServer(),ModLootTableProvider.createBlockLootTable(output));
        }
    }
}
