package io.github.kjspncr.kubejs.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.item.ingredient.IngredientJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class AmadronRecipe extends RecipeJS {
    @Override
    public JsonElement writeInputItem(InputItem value) {
        var json = new JsonObject();
        json.addProperty("type", "ITEM");
        json.addProperty("id", value.ingredient.kjs$getFirst().kjs$getId());
        json.addProperty("amount", value.count);
        json.remove("item");
        return json;
    }

    @Override
    public InputItem readInputItem(Object from) {
        try {
            if (from instanceof JsonObject j && j.has("id")) {
                int c = GsonHelper.getAsInt(j, "amount", 1);
                return IngredientJS.of(j.get("id")).kjs$withCount(c);
            }

            return super.readInputItem(from);
        } catch (Exception e) {
            ConsoleJS.SERVER.error("[%s] Failed to read Inputs from %s".formatted(type.id, from), e,
                    RecipesEventJS.SKIP_ERROR);
            return InputItem.EMPTY;
        }
    }

    @Override
    public JsonElement writeOutputItem(OutputItem value) {
        var json = new JsonObject();
        json.addProperty("type", "ITEM");
        json.addProperty("id", value.item.kjs$getId());
        json.addProperty("amount", value.getCount());
        return json;
    }

    @Override
    public OutputItem readOutputItem(Object from) {
        try {
            if (from instanceof JsonObject j && j.has("item")) {
                int c = GsonHelper.getAsInt(j, "count", 1);
                return OutputItem.of(j.get("id")).withCount(c);
            }

            return OutputItem.of(from);
        } catch (Exception e) {
            ConsoleJS.SERVER.error("[%s] Failed to read Inputs from %s".formatted(type.id, from), e,
                    RecipesEventJS.SKIP_ERROR);
            return OutputItem.EMPTY;
        }
    }

    @Override
    public JsonElement writeInputFluid(InputFluid value) {
        var json = new JsonObject();
        var fluid = ((FluidStackJS) value).getFluidStack();
        json.addProperty("type", "FLUID");
        json.addProperty("id", fluid.getName().getString().toLowerCase());
        json.addProperty("amount", fluid.getAmount());
        json.remove("fluid");
        return json;
    }

    @Override
    public InputFluid readInputFluid(Object from) {
        try {
            if (from instanceof JsonObject j && j.has("id")) {
                int c = GsonHelper.getAsInt(j, "amount", 1);
                return (InputFluid) j.get("id");
            }
        } catch (Exception e) {
            ConsoleJS.SERVER.error("[%s] Failed to read Fluid input from %s".formatted(type.id, from), e,
                    RecipesEventJS.SKIP_ERROR);
        }
        return super.readInputFluid(from);
    }

    @Override
    public JsonElement writeOutputFluid(OutputFluid value) {
        var json = new JsonObject();
        var fluid = ((FluidStackJS) value).getFluidStack();
        json.addProperty("type", "FLUID");
        json.addProperty("id", fluid.getName().getString());
        json.addProperty("amount", fluid.getAmount());
        json.remove("fluid");
        return json;
    }

    @Override
    public OutputFluid readOutputFluid(Object from) {
        try {
            if (from instanceof JsonObject j && j.has("id")) {
                int c = GsonHelper.getAsInt(j, "amount", 1);
                return (OutputFluid) j.get("id");
            }
        } catch (Exception e) {
            ConsoleJS.SERVER.error("[%s] Failed to read Fluid output from %s".formatted(type.id, from), e,
                    RecipesEventJS.SKIP_ERROR);
        }
        return super.readOutputFluid(from);
    }

    @Override
    public RecipeJS id(ResourceLocation _id) {
        return super.id(new ResourceLocation("amadron/" + _id));
    }
}
