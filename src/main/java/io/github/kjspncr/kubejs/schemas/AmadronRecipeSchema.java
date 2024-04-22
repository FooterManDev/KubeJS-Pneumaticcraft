package io.github.kjspncr.kubejs.schemas;

import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import io.github.kjspncr.kubejs.utils.AmadronRecipe;

import javax.json.JsonObject;

public interface AmadronRecipeSchema {
    RecipeKey<Boolean> STATIC = BooleanComponent.BOOLEAN.key("static").optional(false);
    OrRecipeComponent<InputItem, InputFluid> INPUT = ItemComponents.INPUT.or(FluidComponents.INPUT);
    OrRecipeComponent<OutputItem, OutputFluid> OUTPUT = ItemComponents.OUTPUT.or(FluidComponents.OUTPUT);
    RecipeKey<Integer> LEVEL = NumberComponent.INT.key("level").optional(1);

    RecipeSchema SCHEMA = new RecipeSchema(AmadronRecipe.class, AmadronRecipe::new, INPUT.key("input"), OUTPUT.key("output"), LEVEL, STATIC);
}
