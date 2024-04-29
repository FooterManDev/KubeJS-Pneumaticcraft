package io.github.kjspncr.kubejs.schemas;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import io.github.kjspncr.kubejs.PNCRRecipe;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsBonusOutput;

public interface HeatFrameCoolingRecipeSchema {
    RecipeKey<InputFluid> INPUT = FluidComponents.INPUT.key("input");
    RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result");

    RecipeKey<Integer> MAX_TEMP = NumberComponent.INT.key("max_temp")
            .optional(273).alwaysWrite();

    public class HeatFrameRecipeJS extends PNCRRecipe implements AllowsBonusOutput {
    }

    RecipeSchema SCHEMA = new RecipeSchema(HeatFrameRecipeJS.class, HeatFrameRecipeJS::new,
            INPUT, RESULT, MAX_TEMP, PNCRRecipeInterfaces.BONUS_OUTPUT);
}
