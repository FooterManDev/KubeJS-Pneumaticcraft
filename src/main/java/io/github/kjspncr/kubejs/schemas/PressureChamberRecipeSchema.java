package io.github.kjspncr.kubejs.schemas;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import io.github.kjspncr.kubejs.utils.SpecialRecipe;

public interface PressureChamberRecipeSchema {
    RecipeKey<InputItem[]> INPUTS = ItemComponents.INPUT_ARRAY.key("inputs");
    RecipeKey<Double> PRESSURE = NumberComponent.DOUBLE.key("pressure").optional(0D);
    RecipeKey<OutputItem[]> RESULTS = ItemComponents.OUTPUT_ARRAY.key("results");

    RecipeSchema SCHEMA = new RecipeSchema(SpecialRecipe.class, SpecialRecipe::new, INPUTS, RESULTS, PRESSURE);
}
