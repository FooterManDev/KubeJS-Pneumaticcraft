package io.github.kjspncr.kubejs;

import org.apache.http.config.Registry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.UnboundFluidStackJS;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.ingredient.IngredientJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import io.github.kjspncr.kubejs.utils.PNCRInputFluid;
import me.desht.pneumaticcraft.api.crafting.ingredient.FluidIngredient;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.EmptyFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

/*
 * Base PNCRRecipe class that handles serialization and deserialization.
 *
 * The KubeJS data model, as far as I can tell, starts by reading all registered recipes, including
 * the datapack recipe JSONS, and parses them into RecipeJS objects using the specified type.
 * The specific JSON fields are specified by the RecipeSchema registrations, and the types within
 * the recipe are deserialized by readInputItem() and readInputFluid() respectively. During this
 * time, registries are not available, which means tag lookups cannot be resolved. It is for this
 * reason that we cannot use the deserialization functionality provided by PNCR's FluidIngredient
 * because it attempts a tag lookup and resolves to an empty fluid during this phase.
 *
 * Once all the recipe JSONs are loaded, KubeJS scripts are loaded, which can perform modifications
 * in all the parsed RecipeJS objects and add new ones for any of the RecipeSchemas that we've
 * registered. This means that the input and output types can be almost any object which we need
 * to gracefully serialize and deserialize. For the recipe JSONs themselves, we parse all the
 * fluid inputs into a PNCRInputFluid instance.
 *
 * User defined recipes can pass in any manner of fluid type. For example:
 *   e.recipes.fluid_mixer(Fluid.of('forge:crude_oil', 200), Fluid.lava(200))
 *     .outputs(...)
 *
 * Thus, we need to handle the KubeJS fluid types as well in readInputFluid().
 *
 * After all the KubeJS scripts are finished running, the writeInputItem() and writeInputFluid()
 * methods are used to convert the RecipeJS objects back to JSON to pass to the mods. I may be
 * completely wrong about this, but this is what I've gathered from poking it with a stick and
 * seeing what comes out.
 */
public class PNCRRecipe extends RecipeJS {

    @Override
    public JsonElement writeInputItem(InputItem value) {
        if (value.count > 1) {
            var json = new JsonObject();
            json.addProperty("item", value.ingredient.kjs$getFirst().kjs$getId());
            json.addProperty("type", "pneumaticcraft:stacked_item");
            json.addProperty("count", value.count);
            return json;
        } else {
            return value.ingredient.toJson();
        }
    }

    @Override
    public InputItem readInputItem(Object from) {
        try {
            if (from instanceof JsonObject j && j.has("item")) {
                int c = GsonHelper.getAsInt(j, "count", 1);
                return IngredientJS.of(j.get("item")).kjs$withCount(c);
            }
            return super.readInputItem(from);
        } catch (Exception e) {
            ConsoleJS.SERVER.error("[%s] Failed to read Inputs from %s".formatted(type.id, from), e,
                    RecipesEventJS.SKIP_ERROR);
            return InputItem.EMPTY;
        }
    }

    @Override
    public JsonElement writeInputFluid(InputFluid value) {
        if (value instanceof PNCRInputFluid fluid) {
            return fluid.ingredient().toJson();
        } else if (value instanceof UnboundFluidStackJS fluid) {
            ResourceLocation rl = new ResourceLocation(fluid.getId());
            TagKey<Fluid> tagKey = TagKey.create(Registries.FLUID, rl);
            FluidIngredient f = FluidIngredient.of(
                    (int) fluid.kjs$getAmount(), fluid.getNbt(), false, tagKey);
            return f.toJson();
        } else if (value instanceof FluidStackJS fluid) {
            // Convert the FluidStackJS to a Pneumaticcraft FluidIngredient to use its JSON
            // serialization functions.
            FluidIngredient f = FluidIngredient.of(
                    FluidStackHooksForge.toForge(fluid.getFluidStack()));
            return f.toJson();
        }
        ConsoleJS.SERVER.warn("Input fluid %s of type %s could not be serialized.".formatted(
                value, value.getClass()));
        return FluidIngredient.EMPTY.toJson();
    }

    @Override
    public InputFluid readInputFluid(Object from) {
        if (from instanceof PNCRInputFluid fluid) {
            return fluid;
        } else if (from instanceof FluidStack fluid) {
            // Invoked when a recipe is declared in a KubeJS script with a FluidStack
            // argument. Rare, but possible.
            return new PNCRInputFluid(FluidIngredient.of(fluid));
        } else if (from instanceof UnboundFluidStackJS fluid) {
            // Invoked when a recipe is declared in a KubeJS script with a
            // UnboundFluidStackJS argument.
            //
            // This happens when Fluid.of() is given a ResourceLocation as a string, which
            // can either be a tag or a fluid ID.
            ResourceLocation rl = new ResourceLocation(fluid.getId());
            TagKey<Fluid> tagKey = TagKey.create(Registries.FLUID, rl);
            // Since registries are not loaded, a registry lookup of a tag will return an
            // EmptyFluid.
            Fluid fl = RegistryInfo.FLUID.getValue(rl);
            if (fl instanceof EmptyFluid) {
                FluidIngredient f = FluidIngredient.of(
                        (int) fluid.kjs$getAmount(), fluid.getNbt(), false, tagKey);
                return new PNCRInputFluid(f);
            }
            // Otherwise return the fluid with just the ResourceLocation
            FluidIngredient f = FluidIngredient.of(
                    (int) fluid.kjs$getAmount(), fluid.getNbt(), false, rl);
            return new PNCRInputFluid(f);
        } else if (from instanceof FluidStackJS fluid) {
            // Invoked when a recipe is declared in a KubeJS script with a FluidStackJS
            // argument.
            FluidIngredient f = FluidIngredient.of(
                    FluidStackHooksForge.toForge(fluid.getFluidStack()));
            return new PNCRInputFluid(f);
        } else if (from instanceof JsonObject json) {
            // Invoked when we read in the recipe datapack JSONs, returning the parsed
            // fluid. Also invoked if a KubeJS script declares a custom recipe with
            // e.custom()
            //
            // In this branch, we must use the overloads of FluidIngredient.of that do not
            // attempt ResourceLocation resolution.
            int amount = GsonHelper.getAsInt(json, "amount", 0);
            CompoundTag nbt = null;
            if (json.has("nbt")) {
                try {
                    nbt = TagParser.parseTag(json.get("nbt").getAsString());
                } catch (CommandSyntaxException e) {
                    throw new JsonSyntaxException(e);
                }
            }
            boolean fuzzyNBT = GsonHelper.getAsBoolean(json, "fuzzyNBT", false);
            if (json.has("fluid")) {
                ResourceLocation fluidId = new ResourceLocation(json.get("fluid").getAsString());
                return new PNCRInputFluid(FluidIngredient.of(amount, nbt, fuzzyNBT, fluidId));
            }
            if (json.has("tag")) {
                ResourceLocation rl = new ResourceLocation(json.get("tag").getAsString());
                // Do not try and resolve the tag key with a registry lookup. Registries are not
                // available at the time this addon is loaded.
                TagKey<Fluid> tagKey = TagKey.create(Registries.FLUID, rl);
                return new PNCRInputFluid(FluidIngredient.of(amount, nbt, fuzzyNBT, tagKey));
            }
            throw new JsonSyntaxException("Fluid ingredient %s must have 'fluid' or 'tag' field!"
                    .formatted(json.toString()));
        }
        ConsoleJS.SERVER.warn("Unknown input fluid %s of type %s".formatted(from, from.getClass()));
        return PNCRInputFluid.EMPTY;
    }
}
