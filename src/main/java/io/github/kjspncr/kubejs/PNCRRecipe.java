package io.github.kjspncr.kubejs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.ingredient.IngredientJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import io.github.kjspncr.kubejs.utils.PNCRInputFluid;
import me.desht.pneumaticcraft.api.crafting.ingredient.FluidIngredient;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

/*
 * Base PNCRRecipe class that handles serialization and deserialization.
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
        } else if (value instanceof FluidStackJS fluid) {
            // Convert the FluidStackJS to a Pneumaticcraft FluidIngredient to use its JSON
            // serialization functions.
            FluidIngredient f = FluidIngredient.of(
                    FluidStackHooksForge.toForge(fluid.getFluidStack()));
            return f.toJson();
        }
        // TODO implement custom handling for fluid tags, this is not easily supported
        // by kubejs, the inputfluid will be of type Ingredient maybe?
        ConsoleJS.SERVER.warn("Input fluid %s of type %s could not be serialized.".formatted(
                value, value.getClass()));
        return FluidIngredient.EMPTY.toJson();
    }

    @Override
    public InputFluid readInputFluid(Object from) {
        if (from instanceof PNCRInputFluid fluid) {
            return fluid;
        } else if (from instanceof FluidStack fluid) {
            return new PNCRInputFluid(FluidIngredient.of(fluid));
        } else if (from instanceof FluidStackJS fluid) {
            FluidIngredient f = FluidIngredient.of(
                    FluidStackHooksForge.toForge(fluid.getFluidStack()));
            return new PNCRInputFluid(f);
        } else if (from instanceof JsonObject json) {
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
