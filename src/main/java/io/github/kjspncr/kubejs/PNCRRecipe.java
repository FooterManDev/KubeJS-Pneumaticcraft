package io.github.kjspncr.kubejs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
import net.minecraft.util.GsonHelper;
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
        } else if (value instanceof FluidIngredient fluid) {
            return fluid.toJson();
        } else if (value instanceof FluidStackJS fluid) {
            return fluid.toJson();
        }
        ConsoleJS.SERVER.warn("Input fluid %s of type %s could not be serialized.".formatted(
                value, value.getClass()));
        return FluidIngredient.EMPTY.toJson();
    }

    @Override
    public InputFluid readInputFluid(Object from) {
        if (from instanceof PNCRInputFluid fluid) {
            return fluid;
        } else if (from instanceof FluidIngredient fluid) {
            return new PNCRInputFluid(fluid);
        } else if (from instanceof FluidStack fluid) {
            return new PNCRInputFluid(FluidIngredient.of(fluid));
        } else if (from instanceof FluidStackJS fluid) {
            FluidIngredient f = FluidIngredient.of(FluidStackHooksForge.toForge(fluid.getFluidStack()));
            return new PNCRInputFluid(f);
        } else if (from instanceof JsonObject j) {
            // TODO: this is potentially problematic because this calls
            // ForgeRegistries.FLUIDS and the fluid registry is not available at startup
            // This causes some recipe parsing errors on first load, which disappear after
            // a /reload. As a hack, users can force call /reload when the server starts lol
            FluidIngredient f = FluidIngredient.Serializer.INSTANCE.parse(j);
            return new PNCRInputFluid(f);
        }
        ConsoleJS.SERVER.warn("Unknown input fluid %s of type %s".formatted(from, from.getClass()));
        return PNCRInputFluid.EMPTY;
    }
}
