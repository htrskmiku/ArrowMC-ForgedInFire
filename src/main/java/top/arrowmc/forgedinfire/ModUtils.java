package top.arrowmc.forgedinfire;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;

public class ModUtils {
    public static boolean isKubeJSLoaded(){
        return ModList.get().isLoaded("kubejs");
    }
    public static String getItemResourceLocation(Item item){
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }

    /**
     * 为组件选择颜色：（-∞,0.25）RED;[0.25,0.5)YELLOW;[0.5,0.75)GREEN;[0.75,+∞）GOLD
     */
    public static MutableComponent chooseComponentColor(MutableComponent component, double value){
        if (value < 0.25){
            return component.withStyle(ChatFormatting.RED);
        }else if (value < 0.5){
            return component.withStyle(ChatFormatting.YELLOW);
        }else if (value < 0.75){
            return component.withStyle(ChatFormatting.GREEN);
        }else{
            return component.withStyle(ChatFormatting.GOLD);
        }
    }
}
