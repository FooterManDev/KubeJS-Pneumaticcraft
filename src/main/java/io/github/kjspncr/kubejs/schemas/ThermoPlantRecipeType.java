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
import io.github.kjspncr.kubejs.utils.PneumaticcraftInputFluid;

public interface ThermoPlantRecipeType {
    RecipeKey<InputFluid> INPUT_FLUID = FluidComponents.INPUT.key("fluid_input")
            .optional(PneumaticcraftInputFluid.EMPTY).allowEmpty();
    RecipeKey<InputItem> INPUT_ITEM = ItemComponents.INPUT.key("item_input")
            .optional(InputItem.EMPTY).allowEmpty();
    RecipeKey<OutputFluid> OUTPUT_FLUID = FluidComponents.OUTPUT.key("fluid_output")
            .optional(EmptyFluidStackJS.INSTANCE).allowEmpty();
    RecipeKey<OutputItem> OUTPUT_ITEM = ItemComponents.OUTPUT.key("item_output")
            .optional(OutputItem.EMPTY).allowEmpty();

    RecipeSchema SCHEMA = new RecipeSchema(PNCRRecipe.class, PNCRRecipe::new,
            INPUT_FLUID, INPUT_ITEM, OUTPUT_FLUID, OUTPUT_ITEM,
            PNCRRecipe.EXOTHERMIC, PNCRRecipe.PRESSURE, PNCRRecipe.TEMPERATURE,
            PNCRRecipe.AIR_USE_MULTIPLIER, PNCRRecipe.SPEED);
}
