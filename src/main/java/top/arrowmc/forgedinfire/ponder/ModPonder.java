package top.arrowmc.forgedinfire.ponder;


import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import top.arrowmc.forgedinfire.ForgedInFire;

public class ModPonder implements PonderPlugin {

    @Override
    public String getModId() {
        return ForgedInFire.MODID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        ForgingAnvilPonder.register(helper);
    }
}
