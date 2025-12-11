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
        add("gui.forgedinfire.jei.fail.probability","Fail Probability: %d%%");
        add(ModRegister.FORGING_SCRAP.get(), "Forging Scrap");
        add(ModRegister.FORGING_PINCER.get(), "Forging Pincer");
        add(ModRegister.HOT_IRON_ITEM.get(), "Hot Iron");
        add("config.jade.plugin_forgedinfire.forging_anvil","Forging Anvil");
        add(ModRegister.FORGING_BASE_MATERIAL.get(),"Forging Base Material");
        add("tooltip.forgedinfire.forgingbase.refinecount","Refine Counts: %s");
        add("tooltip.forgedinfire.forgingbase.shifttips","Press SHIFT to view more infos");
        add("tooltip.forgedinfire.forgingbase.material","Materials inside:");
        add("tooltip.forgedinfire.forgingbase.nexttimebroken","Fail probability on next forging: %d%%");
        add("tooltip.forgedinfire.forgingbase.quality.value","%s");
        add("tooltip.forgedinfire.forgingbase.quality","Quality: ");
        add("forgedinfire.ponder.forging_anvil_direction.header","锻造砧");
        add("forgedinfire.ponder.forging_anvil_direction.text_1","锻造砧是用于强化锻造基材的方块");
        add("forgedinfire.ponder.forging_anvil_direction.text_2","先将锻造基材放入锻造砧");
        add("forgedinfire.ponder.forging_anvil_direction.text_3","然后放入锻造材料...");
        add("forgedinfire.ponder.forging_anvil_direction.text_4","注意：锻造材料可以为任何标签为reinforce_material的材料（可以在JEI中查找$reinforce_material）");
        add("forgedinfire.ponder.forging_anvil_direction.text_5","然后用锻造锤右键锻造砧...");
        add("forgedinfire.ponder.forging_anvil_direction.text_6","获得的锻造基材产物将拥有品质，且可以多次锻造");
        add("forgedinfire.ponder.forging_anvil_direction.text_7","产物品质的增减程度由锻造材料的稀有度决定。稀有度越高，品质增减得越多");
        add("forgedinfire.ponder.forging_anvil_direction.text_8","注意，锻造基材在锻造过程中有几率损坏成为锻造废料...");
        add("forgedinfire.ponder.forging_anvil_direction.text_9","...所以请合理规划你的锻造方案");
    }
}
