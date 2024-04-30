package io.github.kjspncr.kubejs.schemas;

import com.mojang.datafixers.util.Either;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import io.github.kjspncr.kubejs.utils.AmadronRecipe;

public interface AmadronOffersRecipeSchema {
    RecipeKey<Either<InputFluid, InputItem>> INPUT = FluidComponents.INPUT_OR_ITEM.key("input");
    RecipeKey<Either<OutputFluid, OutputItem>> OUTPUT = FluidComponents.OUTPUT_OR_ITEM.key("output");
    RecipeKey<Integer> LEVEL = NumberComponent.INT.key("level")
            .optional(0).alwaysWrite();
    RecipeKey<Boolean> STATIC = BooleanComponent.BOOLEAN.key("static")
            .optional(true).alwaysWrite();

    RecipeSchema SCHEMA = new RecipeSchema(AmadronRecipe.class, AmadronRecipe::new,
            INPUT, OUTPUT, LEVEL, STATIC);
}
