package top.arrowmc.forgedinfire;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = ForgedInFire.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.DoubleValue FORGING_BROKEN_PROBABILITY = BUILDER.comment("Basic forging broken probability").defineInRange("brokenProbability", 0.01, 0, 1.0D);

    private static final ForgeConfigSpec.DoubleValue BROKEN_PROBABILITY_INCREASE = BUILDER.comment("Increase how much broken probability when material's refining counts raised").defineInRange("increaseProbabilityPerRefining", 0.01, 0, 1.0D);
    // a list of strings that are treated as resource locations for items
//    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER.comment("A list of items to log on common setup.").defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static double forgingBrokenProbability;
    public static double brokenProbabilityIncrease;
//    public static Set<Item> items;

//    @SuppressWarnings("removal")
//    private static boolean validateItemName(final Object obj) {
//        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
//    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        forgingBrokenProbability= FORGING_BROKEN_PROBABILITY.get();
        brokenProbabilityIncrease= BROKEN_PROBABILITY_INCREASE.get();
    }
}
