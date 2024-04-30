package io.github.kjspncr.kubejs.utils;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import me.desht.pneumaticcraft.api.crafting.ingredient.FluidIngredient;

/**
 * Concrete record class wrapper for Pneumaticcraft's FluidIngredient class to
 * allow it to interface with KubeJS's recipe registry.
 */
public record PneumaticcraftInputFluid(FluidIngredient ingredient) implements InputFluid {
    public static final PneumaticcraftInputFluid EMPTY = new PneumaticcraftInputFluid(FluidIngredient.EMPTY);

    @Override
    public boolean kjs$isEmpty() {
        return ingredient.isEmpty();
    }

    @Override
    public long kjs$getAmount() {
        return ingredient.getAmount();
    }
}
