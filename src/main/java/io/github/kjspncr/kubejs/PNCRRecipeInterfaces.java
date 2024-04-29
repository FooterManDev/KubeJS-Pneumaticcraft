package io.github.kjspncr.kubejs;

import org.apache.commons.lang3.NotImplementedException;

import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BooleanComponent;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentBuilder;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentBuilderMap;
import dev.latvian.mods.kubejs.typings.Info;

/*
 * Public container class that contains all optional recipe keys and their setters.
 * Recipe schemas will define a local class instance that only implements the interfaces that they need.
 */
public class PNCRRecipeInterfaces {

    protected interface RecipeJSInterface {
        public <T> RecipeJS setValue(RecipeKey<T> key, T value);
    }

    public static RecipeKey<Float> AIR_USE_MULTIPLIER = NumberComponent.ANY_FLOAT.key("air_use_multiplier")
            .optional(1f);

    public interface AllowsAirUseMultiplier extends RecipeJSInterface {
        @Info("Sets the air use multiplier for this recipe.")
        default public RecipeJS air_use_multiplier(float air_use_multiplier) {
            return setValue(AIR_USE_MULTIPLIER, air_use_multiplier);
        }
    }

    protected static RecipeKey<Float> LIMIT = NumberComponent.FLOAT.key("limit");
    protected static RecipeKey<Float> MULTIPLIER = NumberComponent.FLOAT.key("multiplier");
    public static RecipeKey<RecipeComponentBuilderMap> BONUS_OUTPUT = new RecipeComponentBuilder(2)
            .add(LIMIT)
            .add(MULTIPLIER)
            .key("bonus_output")
            .optional(RecipeComponentBuilderMap.EMPTY);

    public interface AllowsBonusOutput extends RecipeJSInterface {
        @Info("Set bonus output on this recipe. Not implemented.")
        default public RecipeJS bonus_output() {
            throw new NotImplementedException();
        }
    }

    public static RecipeKey<Boolean> EXOTHERMIC = BooleanComponent.BOOLEAN.key("exothermic").optional(false);

    public interface AllowsExothermic extends RecipeJSInterface {
        @Info("Sets this recipe to be exothermic.")
        default public RecipeJS exothermic() {
            return setValue(PNCRRecipeInterfaces.EXOTHERMIC, true);
        }
    }

    public static RecipeKey<Float> PRESSURE = NumberComponent.FLOAT.key("pressure").optional(0F);

    public interface AllowsPressure extends RecipeJSInterface {
        @Info("Sets the minimum pressure required for this recipe.")
        default public RecipeJS pressure(float pressure) {
            return setValue(PRESSURE, pressure);
        }
    }

    public static RecipeKey<Float> SPEED = NumberComponent.ANY_FLOAT.key("speed").optional(1f);

    public interface AllowsSpeed extends RecipeJSInterface {
        @Info("Sets the processing speed for this recipe.")
        default public RecipeJS speed(float speed) {
            return setValue(SPEED, speed);
        }
    }

    protected static RecipeKey<Integer> MIN_TEMP = NumberComponent.ANY_INT.key("min_temp")
            .optional(0);
    protected static RecipeKey<Integer> MAX_TEMP = NumberComponent.ANY_INT.key("max_temp")
            .optional(0);
    public static RecipeKey<RecipeComponentBuilderMap> TEMPERATURE = new RecipeComponentBuilder(2)
            .add(MIN_TEMP)
            .add(MAX_TEMP)
            .key("temperature")
            .optional(RecipeComponentBuilderMap.EMPTY);

    public interface AllowsTemperature extends RecipeJSInterface {
        @Info("Sets the minimum temperature required for this recipe.")
        default public RecipeJS min_temp(int min_temp) {
            return setValue(MIN_TEMP, min_temp);
        }

        @Info("Sets the maximum temperature required for this recipe.")
        default public RecipeJS max_temp(int max_temp) {
            return setValue(MAX_TEMP, max_temp);
        }
    }

    public static RecipeKey<Integer> TIME = NumberComponent.INT.key("time").optional(20);

    public interface AllowsTime extends RecipeJSInterface {
        @Info("Sets the processing time in ticks required for this recipe.")
        default public RecipeJS time(int time) {
            return setValue(TIME, time);
        }
    }

}
