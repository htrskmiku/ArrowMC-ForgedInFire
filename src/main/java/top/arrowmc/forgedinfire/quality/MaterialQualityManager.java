package top.arrowmc.forgedinfire.quality;

import net.minecraft.world.item.Item;
import top.arrowmc.forgedinfire.ModUtils;

import java.util.HashMap;

public class MaterialQualityManager {
    private static final HashMap<String,Double> ITEM_QUALITY_MAP = new HashMap<>();
    static{
        ITEM_QUALITY_MAP.put("minecraft:iron_ingot",0.1);
        ITEM_QUALITY_MAP.put("minecraft:gold_ingot",0.2);
        ITEM_QUALITY_MAP.put("minecraft:netherite_ingot",0.3);
        ITEM_QUALITY_MAP.put("minecraft:diamond",0.4);
        ITEM_QUALITY_MAP.put("forgedinfire:hot_iron",0.5);
    }


    public static double getQuality(Item item){
        return ITEM_QUALITY_MAP.getOrDefault(ModUtils.getItemResourceLocation(item),0.0);
    }
}
