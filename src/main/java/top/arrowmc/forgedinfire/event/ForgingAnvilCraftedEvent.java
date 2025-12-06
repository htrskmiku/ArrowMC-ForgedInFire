package top.arrowmc.forgedinfire.event;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ForgingAnvilCraftedEvent extends PlayerEvent {

    private final ItemStack result;
    private final Player player;
    private final NonNullList<ItemStack> ingredients;

    public ForgingAnvilCraftedEvent(Player player, ItemStack result, NonNullList<ItemStack> ingredients) {
        super(player);
        this.player = player;
        this.result = result;
        this.ingredients = ingredients;

    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getResultStack() {
        return result;
    }

    /**
     * 返回ItemStack
     * @return
     */
    public NonNullList<ItemStack> getIngredients() {
        return ingredients;
    }
}
