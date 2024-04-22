package io.github.kjspncr.kubejs.schemas;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.EnumComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import me.desht.pneumaticcraft.api.crafting.recipe.AssemblyRecipe;

public interface LaserRecipeSchema {
    RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("input");
    RecipeKey<OutputItem> OUTPUT = ItemComponents.OUTPUT.key("result");
    RecipeKey<AssemblyRecipe.AssemblyProgramType> PROGRAM = new EnumComponent<>(AssemblyRecipe.AssemblyProgramType.class).key("program");

    RecipeSchema SCHEMA = new RecipeSchema(RecipeJS.class, RecipeJS::new, INPUT, OUTPUT, PROGRAM);
}
