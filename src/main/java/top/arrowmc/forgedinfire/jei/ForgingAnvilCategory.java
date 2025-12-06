package top.arrowmc.forgedinfire.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import top.arrowmc.forgedinfire.ForgedInFire;
import top.arrowmc.forgedinfire.ModRegister;
import top.arrowmc.forgedinfire.recipe.ForgingRecipe;

public class ForgingAnvilCategory implements IRecipeCategory<ForgingRecipe>{

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ForgedInFire.MODID, "forging");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(ForgedInFire.MODID,
            "textures/gui/forging_anvil.png");

    private IDrawable background;
    private IDrawable icon;

    public static final RecipeType<ForgingRecipe> FORGING_RECIPE = new RecipeType<>(UID,ForgingRecipe.class);

    public ForgingAnvilCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, 175, 85);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModRegister.FORGING_HAMMER.get()));
    }

    @Override
    public RecipeType<ForgingRecipe> getRecipeType() {
        return FORGING_RECIPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("top.arrowmc.forgedinfire.jei");
    }

    @Override
    @SuppressWarnings("removal")
    public @Nullable IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, ForgingRecipe recipe, IFocusGroup focuses) {
        addFailProbability(builder,recipe);
    }

    public void addFailProbability(IRecipeExtrasBuilder builder,ForgingRecipe recipe){
        double probability = recipe.getBrokenProbability();
        Component probabilityString = Component.translatable("gui.forgedinfire.jei.fail.probability", probability);
        builder.addText(probabilityString, getWidth() - 20, 10)
                .setPosition(-5,-5,getWidth(),getHeight(), HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM)
                .setTextAlignment(HorizontalAlignment.RIGHT)
                .setColor(0xFF505050);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder iRecipeLayoutBuilder, ForgingRecipe forgingRecipe, IFocusGroup iFocusGroup) {
        if (forgingRecipe.getIngredients().get(1).isEmpty()) {
            iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 59, 35).addIngredients(forgingRecipe.getIngredients().get(0));
        }else {
            iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 58, 45).addIngredients(forgingRecipe.getIngredients().get(0));
            iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.INPUT, 58, 26).addIngredients(forgingRecipe.getIngredients().get(1));
        }

        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT, 107, 36).addItemStack(forgingRecipe.getResultItem(null));
        iRecipeLayoutBuilder.addSlot(RecipeIngredientRole.OUTPUT,153,65).addItemStack(ModRegister.FORGING_SCRAP.get().getDefaultInstance());
    }
}
