package io.github.kjspncr.kubejs.schemas;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import io.github.kjspncr.kubejs.PNCRRecipe;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsTemperature;

public interface RefineryRecipeSchema {
    RecipeKey<InputFluid> INPUT = FluidComponents.INPUT.key("input");
    RecipeKey<OutputFluid[]> RESULTS = FluidComponents.OUTPUT_ARRAY.key("results");

    public class RefineryRecipeJS extends PNCRRecipe implements AllowsTemperature<RefineryRecipeJS> {
    }

    RecipeSchema SCHEMA = new RecipeSchema(
            RefineryRecipeJS.class, RefineryRecipeJS::new,
            INPUT, RESULTS, PNCRRecipeInterfaces.TEMPERATURE);
}
