package top.arrowmc.forgedinfire.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import top.arrowmc.forgedinfire.ModRegister;

import java.util.function.Consumer;

public class ModRecipeGenerator extends RecipeProvider implements IConditionBuilder {
    public ModRecipeGenerator(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.MISC, ModRegister.FORGING_BASE_MATERIAL.get())
                .requires(ModRegister.FORGING_BASE_MATERIAL.get())
                .unlockedBy("has_base_material",has(ModRegister.FORGING_BASE_MATERIAL.get()))
                .save(pWriter);
    }
}
