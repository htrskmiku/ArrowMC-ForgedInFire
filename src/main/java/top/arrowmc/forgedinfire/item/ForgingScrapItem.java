package top.arrowmc.forgedinfire.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import top.arrowmc.forgedinfire.Config;

import java.util.List;
import java.util.Optional;

public class ForgingScrapItem extends Item {
    public ForgingScrapItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag nbt = pStack.getOrCreateTag();
        if (nbt.contains("refine_materials")) {
            if (Screen.hasShiftDown()) {
                pTooltipComponents.add(Component.translatable("tooltip.forgedinfire.forgingbase.material").withStyle(ChatFormatting.GREEN));
                ListTag listTag = nbt.getList("refine_materials", Tag.TAG_COMPOUND);
                for (int i = 0; i < listTag.size(); i++) {
                    String material = listTag.getCompound(i).getString("material");
                    Optional<Holder.Reference<Item>> optional = ForgeRegistries.ITEMS.getDelegate(ResourceLocation.parse(material));
                    if (optional.isPresent()) {
                        optional.ifPresent(holder -> pTooltipComponents.add(holder.get().getDefaultInstance().getHoverName().copy().withStyle(ChatFormatting.GREEN)));
                    }
                }
            }else {
                if (nbt.contains("refine_counts")) {
                    int refineCounts = nbt.getInt("refine_counts");
                    pTooltipComponents.add(Component.translatable("tooltip.forgedinfire.forgingbase.refinecount", refineCounts).withStyle(ChatFormatting.YELLOW));
                }
                pTooltipComponents.add(Component.translatable("tooltip.forgedinfire.forgingbase.shifttips").withStyle(ChatFormatting.GOLD));
            }
        }
    }
}
