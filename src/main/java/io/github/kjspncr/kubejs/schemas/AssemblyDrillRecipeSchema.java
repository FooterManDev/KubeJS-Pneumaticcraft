package io.github.kjspncr.kubejs.schemas;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.EnumComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import io.github.kjspncr.kubejs.PNCRRecipe;
import me.desht.pneumaticcraft.api.crafting.recipe.AssemblyRecipe.AssemblyProgramType;

public interface AssemblyDrillRecipeSchema {
    RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("input");
    RecipeKey<OutputItem> OUTPUT = ItemComponents.OUTPUT.key("result");
    RecipeKey<AssemblyProgramType> PROGRAM = new EnumComponent<>(
            AssemblyProgramType.class)
            .key("program")
            .optional(AssemblyProgramType.DRILL).alwaysWrite();

    RecipeSchema SCHEMA = new RecipeSchema(PNCRRecipe.class, PNCRRecipe::new, INPUT, OUTPUT, PROGRAM);
}
