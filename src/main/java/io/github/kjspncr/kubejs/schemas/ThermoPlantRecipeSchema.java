package io.github.kjspncr.kubejs.schemas;

import dev.latvian.mods.kubejs.fluid.EmptyFluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import io.github.kjspncr.kubejs.PNCRRecipe;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsExothermic;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsPressure;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsTemperature;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsAirUseMultiplier;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsSpeed;
import io.github.kjspncr.kubejs.utils.PneumaticcraftInputFluid;

public interface ThermoPlantRecipeSchema {
    RecipeKey<InputFluid> INPUT_FLUID = FluidComponents.INPUT.key("fluid_input")
            .optional(PneumaticcraftInputFluid.EMPTY).allowEmpty();
    RecipeKey<InputItem> INPUT_ITEM = ItemComponents.INPUT.key("item_input")
            .optional(InputItem.EMPTY).allowEmpty();
    RecipeKey<OutputFluid> OUTPUT_FLUID = FluidComponents.OUTPUT.key("fluid_output")
            .optional(EmptyFluidStackJS.INSTANCE).allowEmpty();
    RecipeKey<OutputItem> OUTPUT_ITEM = ItemComponents.OUTPUT.key("item_output")
            .optional(OutputItem.EMPTY).allowEmpty();

    public class ThermoPlantRecipeJS extends PNCRRecipe implements
            AllowsExothermic, AllowsPressure,
            AllowsTemperature, AllowsAirUseMultiplier,
            AllowsSpeed {
    }

    RecipeSchema SCHEMA = new RecipeSchema(ThermoPlantRecipeJS.class, ThermoPlantRecipeJS::new,
            INPUT_FLUID, INPUT_ITEM, OUTPUT_FLUID, OUTPUT_ITEM,
            PNCRRecipeInterfaces.EXOTHERMIC, PNCRRecipeInterfaces.PRESSURE, PNCRRecipeInterfaces.TEMPERATURE,
            PNCRRecipeInterfaces.AIR_USE_MULTIPLIER, PNCRRecipeInterfaces.SPEED);
}
