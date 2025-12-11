package top.arrowmc.forgedinfire.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import top.arrowmc.forgedinfire.ForgedInFire;
import top.arrowmc.forgedinfire.ModRegister;
import top.arrowmc.forgedinfire.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsGenerator extends ItemTagsProvider {

    public ModItemTagsGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, ForgedInFire.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(Tags.Items.TOOLS)
                .add(ModRegister.FORGING_HAMMER.get())
                .add(ModRegister.FORGING_PINCER.get());

        tag(ModTags.Items.REINFORCE_MATERIAL)
                .add(Items.IRON_INGOT)
                .add(Items.GOLD_INGOT)
                .add(Items.DIAMOND)
                .add(Items.EMERALD)
                .add(Items.NETHERITE_INGOT);
    }
}
