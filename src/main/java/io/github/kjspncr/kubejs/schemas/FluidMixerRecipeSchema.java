package io.github.kjspncr.kubejs.schemas;

import dev.latvian.mods.kubejs.fluid.EmptyFluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import io.github.kjspncr.kubejs.PNCRRecipe;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsPressure;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsTime;

public interface FluidMixerRecipeSchema {
    RecipeKey<InputFluid> INPUT1 = FluidComponents.INPUT.key("input1");
    RecipeKey<InputFluid> INPUT2 = FluidComponents.INPUT.key("input2");

    RecipeKey<OutputFluid> FLUID_OUTPUT = FluidComponents.OUTPUT.key("fluid_output")
            .optional(EmptyFluidStackJS.INSTANCE).allowEmpty();
    RecipeKey<OutputItem> ITEM_OUTPUT = ItemComponents.OUTPUT.key("item_output")
            .optional(OutputItem.EMPTY).allowEmpty();

    public class FluidMixerRecipeJS extends PNCRRecipe implements AllowsPressure, AllowsTime {
    }

    RecipeSchema SCHEMA = new RecipeSchema(FluidMixerRecipeJS.class, FluidMixerRecipeJS::new,
            INPUT1, INPUT2, ITEM_OUTPUT, FLUID_OUTPUT,
            PNCRRecipeInterfaces.PRESSURE, PNCRRecipeInterfaces.TIME);
}
