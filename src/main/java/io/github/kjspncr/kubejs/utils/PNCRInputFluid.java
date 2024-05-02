package io.github.kjspncr.kubejs.utils;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import me.desht.pneumaticcraft.api.crafting.ingredient.FluidIngredient;

/**
 * Concrete record class wrapper for Pneumaticcraft's FluidIngredient class to
 * allow it to interface with KubeJS's recipe registry.
 */
public record PNCRInputFluid(FluidIngredient ingredient) implements InputFluid {

    public static final PNCRInputFluid EMPTY = new PNCRInputFluid(FluidIngredient.EMPTY);

    @Override
    public long kjs$getAmount() {
        return ingredient.getAmount();
    }
}
