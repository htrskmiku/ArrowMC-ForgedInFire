package top.arrowmc.forgedinfire.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import top.arrowmc.forgedinfire.ForgedInFire;
import top.arrowmc.forgedinfire.ModRegister;

public class ModItemModelGenerator extends ItemModelProvider {
    public ModItemModelGenerator(PackOutput output,ExistingFileHelper existingFileHelper) {
        super(output, ForgedInFire.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
    }
}
