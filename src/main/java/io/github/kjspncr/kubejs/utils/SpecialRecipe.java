package io.github.kjspncr.kubejs.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.UnboundFluidStackJS;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.item.ingredient.IngredientJS;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import me.desht.pneumaticcraft.api.crafting.ingredient.FluidIngredient;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.fluids.FluidStack;

public class SpecialRecipe extends RecipeJS {
    @Override
    public JsonElement writeInputItem(InputItem value) {
        if (value.count > 1) {
            var json = new JsonObject();
            json.addProperty("item", value.ingredient.kjs$getFirst().kjs$getId());
            json.addProperty("type", "pneumaticcraft:stacked_item");
            json.addProperty("count", value.count);
            return json;
        } else {
            return value.ingredient.toJson();
        }
    }

    @Override
    public InputItem readInputItem(Object from) {
        try {
            if (from instanceof JsonObject j && j.has("item")) {
                int c = GsonHelper.getAsInt(j, "count", 1);
                return IngredientJS.of(j.get("item")).kjs$withCount(c);
            }

            return super.readInputItem(from);
        } catch (Exception e) {
            ConsoleJS.SERVER.error("[%s] Failed to read Inputs from %s".formatted(type.id, from), e, RecipesEventJS.SKIP_ERROR);
            return InputItem.EMPTY;
        }
    }
}
