package top.arrowmc.forgedinfire.kubejs;

import dev.latvian.mods.kubejs.player.PlayerEventJS;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.arrowmc.forgedinfire.kubejs.wrapper.ForgingAnvilCraftedDataWrapper;

import java.util.Collection;

public class ForgingAnvilCraftedEventJS extends PlayerEventJS {

    public ForgingAnvilCraftedDataWrapper wrapper;

    public ForgingAnvilCraftedEventJS(Player pPlayer, NonNullList<ItemStack> ingredients, ItemStack output) {
        super();
        wrapper = new ForgingAnvilCraftedDataWrapper(pPlayer,ingredients,output);
    }

    @Override
    public Player getEntity() {
        return wrapper.getPlayer();
    }

    @Info("Get current player using forging anvil.")
    public Player getPlayer(){
        return wrapper.getPlayer();
    }

    @Info("Get current recipe's result")
    public ItemStack getResultItem(){
        return wrapper.getResult();
    }


    @Info("Get current input ingredients.")
    public Collection<ItemStack> getIngredients(){
        return wrapper.getIngredients();
    }
}
