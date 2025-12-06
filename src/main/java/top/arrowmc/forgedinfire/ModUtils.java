package top.arrowmc.forgedinfire;

import net.minecraftforge.fml.ModList;

public class ModUtils {
    public static boolean isKubeJSLoaded(){
        return ModList.get().isLoaded("kubejs");
    }
}
