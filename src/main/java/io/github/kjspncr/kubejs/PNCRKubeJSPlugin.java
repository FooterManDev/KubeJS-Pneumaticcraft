package io.github.kjspncr.kubejs;

import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import io.github.kjspncr.KubeJSPneumaticCraft;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import io.github.kjspncr.kubejs.schemas.*;
import me.desht.pneumaticcraft.PneumaticCraftRepressurized;
import me.desht.pneumaticcraft.common.PneumaticCraftAPIHandler;

public class PNCRKubeJSPlugin extends KubeJSPlugin {
    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        event.namespace("pneumaticcraft")
                .register("assembly_laser", LaserRecipeSchema.SCHEMA)
                .register("explosion_crafting", ExplosionCraftingRecipeSchema.SCHEMA)
                .register("pressure_chamber", PressureChamberRecipeSchema.SCHEMA)
                .register("refinery", RefineryRecipeSchema.SCHEMA)
                .register("amadron", AmadronRecipeSchema.SCHEMA)
                ;
    }
}

