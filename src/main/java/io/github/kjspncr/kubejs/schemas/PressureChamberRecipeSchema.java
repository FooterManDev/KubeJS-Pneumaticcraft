package io.github.kjspncr.kubejs.schemas;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import io.github.kjspncr.kubejs.PNCRRecipe;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsPressure;

public interface PressureChamberRecipeSchema {
    RecipeKey<InputItem[]> INPUTS = ItemComponents.INPUT_ARRAY.key("inputs");
    RecipeKey<OutputItem[]> RESULTS = ItemComponents.OUTPUT_ARRAY.key("results");

    public class PressureChamberRecipeJS extends PNCRRecipe implements AllowsPressure {
    }

    RecipeSchema SCHEMA = new RecipeSchema(PressureChamberRecipeJS.class, PressureChamberRecipeJS::new,
            INPUTS, RESULTS, PNCRRecipeInterfaces.PRESSURE);
}
