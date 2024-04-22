package io.github.kjspncr.kubejs.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import net.minecraft.nbt.CompoundTag;

public class PNCRComponents {
    RecipeComponent<InputFluid> FLUID_INPUT = new RecipeComponent<>() {

        @Override
        public Class<?> componentClass() {
            return InputFluid.class;
        }

        @Override
        public JsonElement write(RecipeJS recipe, InputFluid value) {
            var stack = ((FluidStackJS) value);
            return stack.kjs$isEmpty() ? null : new JsonPrimitive(stack.getFluidStack().write(new CompoundTag()).toString());
        }

        @Override
        public InputFluid read(RecipeJS recipe, Object from) {
            return null;
        }
    };
}
