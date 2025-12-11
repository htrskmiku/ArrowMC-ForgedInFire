package top.arrowmc.forgedinfire.kubejs;

import dev.latvian.mods.kubejs.player.PlayerEventJS;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.arrowmc.forgedinfire.kubejs.wrapper.ForgingAnvilCraftingDataWrapper;

import java.util.Collection;

public class ForgingAnvilCraftingEventJS extends PlayerEventJS {

    public ForgingAnvilCraftingDataWrapper wrapper;

    public ForgingAnvilCraftingEventJS(Player pPlayer, NonNullList<ItemStack> ingredients,boolean hasRecipe,ItemStack output) {
        super();
        wrapper = new ForgingAnvilCraftingDataWrapper(pPlayer, ingredients, hasRecipe,output);
    }

    @Override
    public Player getEntity() {
        return wrapper.getPlayer();
    }

    @Info("While the craft has a available recipe,it returns true,or it returns false.")
    public boolean hasRecipe(){
        return wrapper.hasRecipe();
    }

    @Info("Return a Collection of ingredient ItemStack(2 input slot).")
    public Collection<ItemStack> getIngredients() {
        return wrapper.getIngredients();
    }

    @Info("Return the output slot stack.")
    public ItemStack getOutputStack() {
        return wrapper.getOutputStack();
    }
}
