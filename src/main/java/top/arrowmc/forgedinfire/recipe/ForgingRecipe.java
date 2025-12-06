package top.arrowmc.forgedinfire.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.arrowmc.forgedinfire.ForgedInFire;

public class ForgingRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final NonNullList<Ingredient> input;
    private final ItemStack output;
    private final double brokenProbability;

    public ForgingRecipe(NonNullList<Ingredient> inputs, ItemStack outputs, ResourceLocation id,double brokenProbability) {
        this.id = id;
        this.input = inputs;
        this.output = outputs;
        this.brokenProbability = brokenProbability;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide) return false;
        return input.get(0).test(pContainer.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return input;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output;
    }

    //获取锻造失败概率
    public double getBrokenProbability() {
        return brokenProbability;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ForgingRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "forging";
    }

    public static class Serializer implements RecipeSerializer<ForgingRecipe> {

        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ForgedInFire.MODID, "forging");

        @Override
        public ForgingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            output.setCount(1);
            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
            double brokenProbability = GsonHelper.getAsDouble(pSerializedRecipe, "broken_probability",0D);
            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new ForgingRecipe(inputs, output, pRecipeId,brokenProbability);
        }

        @Override
        public @Nullable ForgingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }
            ItemStack output = pBuffer.readItem();
            double brokenProbability = pBuffer.readDouble();
            return new ForgingRecipe(inputs, output, pRecipeId,brokenProbability);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ForgingRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.input.size());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
            pBuffer.writeDouble(pRecipe.getBrokenProbability());
        }
    }
}
