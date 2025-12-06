package top.arrowmc.forgedinfire.kubejs.wrapper;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ForgingAnvilCraftedDataWrapper {


    private ItemStack result;
    private NonNullList<ItemStack> ingredient;
    private Player player;


    public ForgingAnvilCraftedDataWrapper(Player player, NonNullList<ItemStack> ingredients, ItemStack result) {
        this.player = player;
        this.ingredient = ingredients;
        this.result = result;
    }

    public ItemStack getResult() {
        return result;
    }

    public NonNullList<ItemStack> getIngredients() {
        return ingredient;
    }

    public Player getPlayer() {
        return player;
    }
}
