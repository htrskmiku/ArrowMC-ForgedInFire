package top.arrowmc.forgedinfire;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import top.arrowmc.forgedinfire.jei.ForgingAnvilCategory;
import top.arrowmc.forgedinfire.recipe.ForgingRecipe;

import java.util.List;

@JeiPlugin
public class ForgedInFireJEI implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(ForgedInFire.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ForgingAnvilCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<ForgingRecipe> polishingRecipes = recipeManager.getAllRecipesFor(ForgingRecipe.Type.INSTANCE);
        registration.addRecipes(ForgingAnvilCategory.FORGING_RECIPE, polishingRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        IModPlugin.super.registerGuiHandlers(registration);
    }
}
