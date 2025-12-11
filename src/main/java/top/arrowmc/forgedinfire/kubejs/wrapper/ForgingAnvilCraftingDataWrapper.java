package top.arrowmc.forgedinfire.kubejs.wrapper;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ForgingAnvilCraftingDataWrapper {
    private NonNullList<ItemStack> ingredient;
    private Player player;
    private boolean hasRecipe;
    private ItemStack outputStack;


    public ForgingAnvilCraftingDataWrapper(Player player, NonNullList<ItemStack> ingredients, boolean hasRecipe,ItemStack outputStack) {
        this.player = player;
        this.ingredient = ingredients;
        this.hasRecipe = hasRecipe;
    }

    public NonNullList<ItemStack> getIngredients() {
        return ingredient;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean hasRecipe() {
        return hasRecipe;
    }

    public ItemStack getOutputStack() {return outputStack;}
}
