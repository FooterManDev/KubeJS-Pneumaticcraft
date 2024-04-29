package io.github.kjspncr.kubejs;

import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import io.github.kjspncr.kubejs.schemas.*;
import me.desht.pneumaticcraft.api.crafting.PneumaticCraftRecipeTypes;

public class PNCRKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        event.namespace("pneumaticcraft")
                .register(PneumaticCraftRecipeTypes.ASSEMBLY_LASER, LaserRecipeSchema.SCHEMA)
                .register(PneumaticCraftRecipeTypes.EXPLOSION_CRAFTING, ExplosionCraftingRecipeSchema.SCHEMA)
                .register(PneumaticCraftRecipeTypes.PRESSURE_CHAMBER, PressureChamberRecipeSchema.SCHEMA)
                .register(PneumaticCraftRecipeTypes.REFINERY, RefineryRecipeSchema.SCHEMA)
                .register(PneumaticCraftRecipeTypes.THERMO_PLANT, ThermoPlantRecipeSchema.SCHEMA)
        // .register("amadron", AmadronRecipeSchema.SCHEMA)
        ;
    }
}
