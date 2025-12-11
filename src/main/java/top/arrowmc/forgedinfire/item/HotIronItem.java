package top.arrowmc.forgedinfire.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.arrowmc.forgedinfire.ModRegister;

public class HotIronItem extends Item {
    public HotIronItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        if (!level.isClientSide) {
            if (!player.getOffhandItem().is(ModRegister.FORGING_PINCER.get()) && !player.isCreative()){
                player.hurt(level.damageSources().onFire(),0.5F);
            }
        }
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
    }


}
