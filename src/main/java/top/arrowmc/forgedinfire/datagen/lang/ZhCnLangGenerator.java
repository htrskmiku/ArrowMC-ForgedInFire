package top.arrowmc.forgedinfire.datagen.lang;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import top.arrowmc.forgedinfire.ForgedInFire;
import top.arrowmc.forgedinfire.ModRegister;

public class ZhCnLangGenerator extends LanguageProvider {
    public ZhCnLangGenerator(PackOutput output) {
        super(output, ForgedInFire.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add("top.arrowmc.forgedinfire.maintab","锻刀大赛");
        add(ModRegister.FORGING_HAMMER.get(), "锻造锤");
        add(ModRegister.FORGING_ANVIL_ITEM.get(),"锻造砧");
        add("top.arrowmc.forgedinfire.jei","大锤八十锻造");
        add("gui.forgedinfire.jei.fail.probability","锻造失败概率:%.2f");
        add(ModRegister.FORGING_SCRAP.get(),"锻造废料");
        add(ModRegister.FORGING_PINCER.get(), "铁匠钳");
        add(ModRegister.HOT_IRON_ITEM.get(), "烧红的铁");
    }
}
