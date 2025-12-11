package top.arrowmc.forgedinfire.datagen.loottable;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import top.arrowmc.forgedinfire.ModRegister;

import java.util.Collections;
import java.util.stream.Collectors;

public class ModBlockLootTableGenerator extends BlockLootSubProvider {
    public ModBlockLootTableGenerator() {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModRegister.FORGING_ANVIL_BLOCK.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModRegister.BLOCKS.getEntries()
                .stream().map(RegistryObject::get)
                .collect(Collectors.toList());
    }
}
