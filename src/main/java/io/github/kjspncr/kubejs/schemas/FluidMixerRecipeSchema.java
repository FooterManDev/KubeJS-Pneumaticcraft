package io.github.kjspncr.kubejs.schemas;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import io.github.kjspncr.kubejs.PNCRRecipe;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsItemOrFluidOutput;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsPressure;
import io.github.kjspncr.kubejs.PNCRRecipeInterfaces.AllowsTime;

public interface FluidMixerRecipeSchema {
    RecipeKey<InputFluid> INPUT1 = FluidComponents.INPUT.key("input1");
    RecipeKey<InputFluid> INPUT2 = FluidComponents.INPUT.key("input2");

    public class FluidMixerRecipeJS extends PNCRRecipe implements AllowsPressure<FluidMixerRecipeJS>,
            AllowsTime<FluidMixerRecipeJS>, AllowsItemOrFluidOutput<FluidMixerRecipeJS> {
    }

    RecipeSchema SCHEMA = new RecipeSchema(FluidMixerRecipeJS.class, FluidMixerRecipeJS::new,
            INPUT1, INPUT2, PNCRRecipeInterfaces.ITEM_OUTPUT, PNCRRecipeInterfaces.FLUID_OUTPUT,
            PNCRRecipeInterfaces.PRESSURE, PNCRRecipeInterfaces.TIME);
}
