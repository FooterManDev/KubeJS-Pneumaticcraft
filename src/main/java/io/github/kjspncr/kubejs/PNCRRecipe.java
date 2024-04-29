package io.github.kjspncr.kubejs;

import org.checkerframework.checker.units.qual.min;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.ingredient.IngredientJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentBuilder;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentBuilderMap;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import io.github.kjspncr.kubejs.utils.PneumaticcraftInputFluid;
import me.desht.pneumaticcraft.api.crafting.ingredient.FluidIngredient;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.fluids.FluidStack;

public class PNCRRecipe extends RecipeJS {

    public static RecipeKey<Boolean> EXOTHERMIC = BooleanComponent.BOOLEAN.key("exothermic").optional(false);
    public static RecipeKey<Float> PRESSURE = NumberComponent.FLOAT.key("pressure").optional(0F);

    protected static RecipeKey<Integer> MIN_TEMP = NumberComponent.ANY_INT.key("min_temp").optional(0);
    protected static RecipeKey<Integer> MAX_TEMP = NumberComponent.ANY_INT.key("max_temp").optional(0);
    public static RecipeKey<RecipeComponentBuilderMap> TEMPERATURE = new RecipeComponentBuilder(2)
            .add(MIN_TEMP)
            .add(MAX_TEMP)
            .key("temperature")
            .optional(RecipeComponentBuilderMap.EMPTY);

    // Used by ThermoPlant, maybe have subclasses with setters?
    public static RecipeKey<Float> AIR_USE_MULTIPLIER = NumberComponent.ANY_FLOAT.key("air_use_multiplier")
            .optional(1f);
    public static RecipeKey<Float> SPEED = NumberComponent.ANY_FLOAT.key("speed").optional(1f);

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
            ConsoleJS.SERVER.error("[%s] Failed to read Inputs from %s".formatted(type.id, from), e,
                    RecipesEventJS.SKIP_ERROR);
            return InputItem.EMPTY;
        }
    }

    @Override
    public JsonElement writeInputFluid(InputFluid value) {
        if (value instanceof PneumaticcraftInputFluid fluid) {
            return fluid.ingredient().toJson();
        } else if (value instanceof FluidIngredient fluid) {
            return fluid.toJson();
        } else if (value instanceof FluidStackJS fluid) {
            return fluid.toJson();
        }
        ConsoleJS.SERVER.warn("Input fluid %s of type %s could not be serialized.".formatted(
                value, value.getClass()));
        return FluidIngredient.EMPTY.toJson();
    }

    @Override
    public InputFluid readInputFluid(Object from) {
        if (from instanceof PneumaticcraftInputFluid fluid) {
            return fluid;
        } else if (from instanceof FluidIngredient fluid) {
            return new PneumaticcraftInputFluid(fluid);
        } else if (from instanceof FluidStack fluid) {
            return new PneumaticcraftInputFluid(FluidIngredient.of(fluid));
        } else if (from instanceof FluidStackJS fluid) {
            FluidIngredient f = FluidIngredient.of(FluidStackHooksForge.toForge(fluid.getFluidStack()));
            return new PneumaticcraftInputFluid(f);
        } else if (from instanceof JsonObject j) {
            FluidIngredient f = FluidIngredient.Serializer.INSTANCE.parse(j);
            return new PneumaticcraftInputFluid(f);
        }
        ConsoleJS.SERVER.warn("Unknown input fluid %s of type %s".formatted(from, from.getClass()));
        return PneumaticcraftInputFluid.EMPTY;
    }

    @Info("Sets this recipe to be exothermic.")
    public RecipeJS exothermic() {
        return setValue(PNCRRecipe.EXOTHERMIC, true);
    }

    @Info("Sets the minimum pressure required for this recipe.")
    public RecipeJS pressure(float pressure) {
        return setValue(PNCRRecipe.PRESSURE, pressure);
    }

    // @Info("Sets the minimum temperature required for this recipe.")
    // public RecipeJS min_temp(int min_temp) {
    // RecipeComponentBuilderMap temp = getValue(PNCRRecipe.TEMPERATURE);
    // temp.put(PNCRRecipe.MIN_TEMP, min_temp);
    // return setValue(PNCRRecipe.TEMPERATURE, temp);
    // }

    // @Info("Sets the maximum temperature required for this recipe.")
    // public RecipeJS max_temp(int max_temp) {
    // RecipeComponentBuilderMap temp = getValue(PNCRRecipe.TEMPERATURE);
    // temp.put(PNCRRecipe.MIN_TEMP, max_temp);
    // return setValue(PNCRRecipe.TEMPERATURE, temp);
    // }

    @Info("Sets the air use multiplier for this recipe.")
    public RecipeJS air_use_multiplier(float air_use_multiplier) {
        return setValue(PNCRRecipe.AIR_USE_MULTIPLIER, air_use_multiplier);
    }

    @Info("Sets the processing speed for this recipe.")
    public RecipeJS speed(float speed) {
        return setValue(PNCRRecipe.SPEED, speed);
    }
}
