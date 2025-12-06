package top.arrowmc.forgedinfire.datagen.loottable;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import top.arrowmc.forgedinfire.ModRegister;

import java.util.Collections;

public class ModBlockLootTableGenerator extends BlockLootSubProvider {
    protected ModBlockLootTableGenerator() {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModRegister.FORGING_ANVIL_BLOCK.get());
    }
}
