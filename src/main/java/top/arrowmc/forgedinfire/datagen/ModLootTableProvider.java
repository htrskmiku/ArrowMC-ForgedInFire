package top.arrowmc.forgedinfire.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import top.arrowmc.forgedinfire.ModRegister;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class ModLootTableProvider implements LootTableSubProvider {

    public ModLootTableProvider() {}

    public static LootTableProvider createBlockLootTable(PackOutput pOutput) {
        return new LootTableProvider(pOutput, Collections.emptySet(),List.of(new LootTableProvider.SubProviderEntry(ModLootTableProvider::new, LootContextParamSets.BLOCK)));
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> pOutput) {
    }
}
