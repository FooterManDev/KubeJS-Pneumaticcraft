package io.github.kjspncr.kubejs;

import dev.latvian.mods.kubejs.fluid.EmptyFluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.typings.Info;
import io.github.kjspncr.kubejs.utils.BonusOutput;
import io.github.kjspncr.kubejs.utils.TemperatureRange;

/*
 * Public container class that contains all optional recipe keys and their setters.
 * Recipe schemas will define a local class instance that only implements the interfaces that they need.
 *
 * The template argument type allows for the implementing class to reference itself so that
 * the methods provided by the interface are chainable.
 */
public class PNCRRecipeInterfaces {

    protected interface RecipeJSInterface {
        public <T> RecipeJS setValue(RecipeKey<T> key, T value);
    }

    public static RecipeKey<Float> AIR_USE_MULTIPLIER = NumberComponent.ANY_FLOAT.key("air_use_multiplier")
            .optional(1f);

    public interface AllowsAirUseMultiplier<T> extends RecipeJSInterface {
        @SuppressWarnings("unchecked")
        @Info("Sets the air use multiplier for this recipe.")
        default public T air_use_multiplier(float air_use_multiplier) {
            setValue(AIR_USE_MULTIPLIER, air_use_multiplier);
            return (T) this;
        }
    }

    public static RecipeKey<BonusOutput> BONUS_OUTPUT = BonusOutput.EMPTY.key("bonus_output")
            .optional(BonusOutput.EMPTY);

    public interface AllowsBonusOutput<T> extends RecipeJSInterface {
        @SuppressWarnings("unchecked")
        @Info("Set bonus output on this recipe.")
        default public T bonus_output(float limit, float multiplier) {
            setValue(BONUS_OUTPUT, new BonusOutput(limit, multiplier));
            return (T) this;
        }
    }

    public static RecipeKey<Boolean> EXOTHERMIC = BooleanComponent.BOOLEAN.key("exothermic").optional(false);

    public interface AllowsExothermic<T> extends RecipeJSInterface {
        @SuppressWarnings("unchecked")
        @Info("Sets this recipe to be exothermic.")
        default public T exothermic() {
            setValue(PNCRRecipeInterfaces.EXOTHERMIC, true);
            return (T) this;
        }
    }

    public static RecipeKey<Float> PRESSURE = NumberComponent.FLOAT.key("pressure").optional(0F);

    public interface AllowsPressure<T> extends RecipeJSInterface {
        @SuppressWarnings("unchecked")
        @Info("Sets the minimum pressure required for this recipe.")
        default public T pressure(float pressure) {
            setValue(PRESSURE, pressure);
            return (T) this;
        }
    }

    public static RecipeKey<Float> SPEED = NumberComponent.ANY_FLOAT.key("speed").optional(1f);

    public interface AllowsSpeed<T> extends RecipeJSInterface {
        @SuppressWarnings("unchecked")
        @Info("Sets the processing speed for this recipe.")
        default public T speed(float speed) {
            setValue(SPEED, speed);
            return (T) this;
        }
    }

    public static RecipeKey<TemperatureRange> TEMPERATURE = TemperatureRange.EMPTY.key("temperature")
            .optional(TemperatureRange.EMPTY);

    public interface AllowsTemperature<T> extends RecipeJSInterface {
        @SuppressWarnings("unchecked")
        @Info("Sets the minimum temperature required for this recipe.")
        default public T min_temp(int min_temp) {
            setValue(TEMPERATURE, new TemperatureRange(min_temp, null));
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        @Info("Sets the maximum temperature required for this recipe.")
        default public T max_temp(int max_temp) {
            setValue(TEMPERATURE, new TemperatureRange(null, max_temp));
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        @Info("Sets the temperature range required for this recipe.")
        default public T temperature(int min_temp, int max_temp) {
            setValue(TEMPERATURE, new TemperatureRange(min_temp, max_temp));
            return (T) this;
        }
    }

    public static RecipeKey<Integer> TIME = NumberComponent.INT.key("time").optional(20);

    public interface AllowsTime<T> extends RecipeJSInterface {
        @SuppressWarnings("unchecked")
        @Info("Sets the processing time in ticks required for this recipe.")
        default public T time(int time) {
            setValue(TIME, time);
            return (T) this;
        }
    }

    public static RecipeKey<InputFluid> FLUID_INPUT = FluidComponents.INPUT.key("fluid_input")
            .optional(EmptyFluidStackJS.INSTANCE);
    public static RecipeKey<InputItem> ITEM_INPUT = ItemComponents.INPUT.key("item_input")
            .optional(InputItem.EMPTY);

    // RecipeSchemas that take one item, one fluid, or exactly one of each.
    public interface AllowsItemOrFluidInput<T> extends RecipeJSInterface {
        @SuppressWarnings("unchecked")
        @Info("Sets the recipe's inputs.")
        default public T inputs(InputItem item) {
            setValue(ITEM_INPUT, item);
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        @Info("Sets the recipe's inputs.")
        default public T inputs(InputFluid fluid) {
            setValue(FLUID_INPUT, fluid);
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        @Info("Sets the recipe's inputs.")
        default public T inputs(InputFluid fluid, InputItem item) {
            setValue(ITEM_INPUT, item);
            setValue(FLUID_INPUT, fluid);
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        @Info("Sets the recipe's inputs.")
        default public T inputs(InputItem item, InputFluid fluid) {
            setValue(ITEM_INPUT, item);
            setValue(FLUID_INPUT, fluid);
            return (T) this;
        }
    }

    public static RecipeKey<OutputFluid> FLUID_OUTPUT = FluidComponents.OUTPUT.key("fluid_output")
            .optional(EmptyFluidStackJS.INSTANCE);
    public static RecipeKey<OutputItem> ITEM_OUTPUT = ItemComponents.OUTPUT.key("item_output")
            .optional(OutputItem.EMPTY);

    // RecipeSchemas that output one item, one fluid, or exactly one of each.
    public interface AllowsItemOrFluidOutput<T> extends RecipeJSInterface {
        @SuppressWarnings("unchecked")
        @Info("Sets the recipe's outputs.")
        default public T outputs(OutputFluid fluid) {
            setValue(FLUID_OUTPUT, fluid);
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        @Info("Sets the recipe's outputs.")
        default public T outputs(OutputItem item) {
            setValue(ITEM_OUTPUT, item);
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        @Info("Sets the recipe's outputs.")
        default public T outputs(OutputItem item, OutputFluid fluid) {
            setValue(ITEM_OUTPUT, item);
            setValue(FLUID_OUTPUT, fluid);
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        @Info("Sets the recipe's outputs.")
        default public T outputs(OutputFluid fluid, OutputItem item) {
            setValue(ITEM_OUTPUT, item);
            setValue(FLUID_OUTPUT, fluid);
            return (T) this;
        }
    }
}
