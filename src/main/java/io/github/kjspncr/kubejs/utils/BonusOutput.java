package io.github.kjspncr.kubejs.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import net.minecraft.util.GsonHelper;

public record BonusOutput(Float limit, Float multiplier) implements RecipeComponent<BonusOutput> {

    public static BonusOutput EMPTY = new BonusOutput(null, null);

    @Override
    public Class<?> componentClass() {
        return Float.class;
    }

    @Override
    public JsonElement write(RecipeJS recipe, BonusOutput value) {
        var json = new JsonObject();
        if (value.limit != null && value.multiplier != null) {
            json.addProperty("limit", value.limit);
            json.addProperty("multiplier", value.multiplier);
        }
        return json;
    }

    @Override
    public BonusOutput read(RecipeJS recipe, Object from) {
        if (from instanceof JsonObject json) {
            Float limit = null;
            Float multiplier = null;
            if (json.has("limit") && json.has("multiplier")) {
                limit = Float.valueOf(GsonHelper.getAsFloat(json, "limit"));
                multiplier = Float.valueOf(GsonHelper.getAsFloat(json, "multiplier"));
            }
            return new BonusOutput(limit, multiplier);
        }
        throw new IllegalStateException("Expected an object containing both limit and multiplier!");
    }

}
