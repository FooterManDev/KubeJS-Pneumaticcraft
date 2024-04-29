package io.github.kjspncr.kubejs.schemas;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import io.github.kjspncr.kubejs.PNCRRecipe;

public interface PressureChamberRecipeSchema {
    RecipeKey<InputItem[]> INPUTS = ItemComponents.INPUT_ARRAY.key("inputs");
    RecipeKey<OutputItem[]> RESULTS = ItemComponents.OUTPUT_ARRAY.key("results");

    RecipeSchema SCHEMA = new RecipeSchema(PNCRRecipe.class, PNCRRecipe::new, INPUTS, RESULTS,
            PNCRRecipe.PRESSURE);
}
