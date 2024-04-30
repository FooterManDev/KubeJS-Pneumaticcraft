package io.github.kjspncr.kubejs.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import net.minecraft.util.GsonHelper;

public record TemperatureRange(Integer min_temp, Integer max_temp) implements RecipeComponent<TemperatureRange> {

    public static TemperatureRange EMPTY = new TemperatureRange(null, null);

    @Override
    public Class<?> componentClass() {
        return Integer.class;
    }

    @Override
    public JsonElement write(RecipeJS recipe, TemperatureRange value) {
        var json = new JsonObject();
        if (value.min_temp != null) {
            json.addProperty("min_temp", value.min_temp);
        }
        if (value.max_temp != null) {
            json.addProperty("max_temp", value.max_temp);
        }
        return json;
    }

    @Override
    public TemperatureRange read(RecipeJS recipe, Object from) {
        if (from instanceof JsonObject json) {
            Integer min_temp = null;
            Integer max_temp = null;
            if (json.has("min_temp")) {
                min_temp = Integer.valueOf(GsonHelper.getAsInt(json, "min_temp"));
            }
            if (json.has("max_temp")) {
                max_temp = Integer.valueOf(GsonHelper.getAsInt(json, "max_temp"));
            }
            return new TemperatureRange(min_temp, max_temp);
        }
        throw new IllegalStateException("Expected an object containing min_temp or max_temp!");
    }

}
