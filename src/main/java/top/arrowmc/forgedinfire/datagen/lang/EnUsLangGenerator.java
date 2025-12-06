package top.arrowmc.forgedinfire.datagen.lang;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import top.arrowmc.forgedinfire.ForgedInFire;
import top.arrowmc.forgedinfire.ModRegister;

public class EnUsLangGenerator extends LanguageProvider {
    public EnUsLangGenerator(PackOutput output) {
        super(output, ForgedInFire.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("top.arrowmc.forgedinfire.maintab","Duandao Dasai");
        add(ModRegister.FORGING_HAMMER.get(), "Forging Hammer");
        add(ModRegister.FORGING_ANVIL_ITEM.get(),"Forging Anvil");
        add("top.arrowmc.forgedinfire.jei","Forging");
        add("gui.forgedinfire.jei.fail.probability","Fail Probability: %.2f");
        add(ModRegister.FORGING_SCRAP.get(), "Forging Scrap");
        add(ModRegister.FORGING_PINCER.get(), "Forging Pincer");
        add(ModRegister.HOT_IRON_ITEM.get(), "Hot Iron");
    }
}
