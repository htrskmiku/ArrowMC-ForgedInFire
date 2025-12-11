package top.arrowmc.forgedinfire.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import top.arrowmc.forgedinfire.ForgedInFire;

public class ModTags {
    public static class Blocks{


        public static TagKey<Block> tag(String name){
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ForgedInFire.MODID,name));
        }
    }
    public static class Items {
        public static final TagKey<Item> REINFORCE_MATERIAL = tag("reinforce_material");

        public static TagKey<Item> tag(String name){
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ForgedInFire.MODID,name));
        }
    }
}
