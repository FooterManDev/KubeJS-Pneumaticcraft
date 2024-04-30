package io.github.kjspncr.kubejs.schemas;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import io.github.kjspncr.kubejs.PNCRRecipe;

public interface FuelQualityRecipeSchema {
    RecipeKey<InputFluid> FLUID = FluidComponents.INPUT.key("fluid");
    RecipeKey<Float> BURN_RATE = NumberComponent.FLOAT.key("burn_rate");
    RecipeKey<Integer> AIR_PER_BUCKET = NumberComponent.INT.key("air_per_bucket");

    RecipeSchema SCHEMA = new RecipeSchema(PNCRRecipe.class, PNCRRecipe::new,
            FLUID, BURN_RATE, AIR_PER_BUCKET);
}
