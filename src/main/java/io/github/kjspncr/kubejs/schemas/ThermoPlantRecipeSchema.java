package io.github.kjspncr.kubejs.schemas;

import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import io.github.kjspncr.kubejs.PNCRRecipe;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsExothermic;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsItemOrFluidInput;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsItemOrFluidOutput;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsPressure;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsTemperature;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsAirUseMultiplier;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsSpeed;

public interface ThermoPlantRecipeSchema {

    public class ThermoPlantRecipeJS extends PNCRRecipe implements
            AllowsExothermic<ThermoPlantRecipeJS>, AllowsPressure<ThermoPlantRecipeJS>,
            AllowsTemperature<ThermoPlantRecipeJS>, AllowsAirUseMultiplier<ThermoPlantRecipeJS>,
            AllowsSpeed<ThermoPlantRecipeJS>, AllowsItemOrFluidInput<ThermoPlantRecipeJS>,
            AllowsItemOrFluidOutput<ThermoPlantRecipeJS> {
    }

    RecipeSchema SCHEMA = new RecipeSchema(ThermoPlantRecipeJS.class, ThermoPlantRecipeJS::new,
            PNCRRecipeInterfaces.FLUID_INPUT, PNCRRecipeInterfaces.ITEM_INPUT,
            PNCRRecipeInterfaces.FLUID_OUTPUT, PNCRRecipeInterfaces.ITEM_OUTPUT,
            PNCRRecipeInterfaces.EXOTHERMIC, PNCRRecipeInterfaces.PRESSURE,
            PNCRRecipeInterfaces.TEMPERATURE,
            PNCRRecipeInterfaces.AIR_USE_MULTIPLIER, PNCRRecipeInterfaces.SPEED).constructor();
}
