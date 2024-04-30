package io.github.kjspncr.kubejs.utils;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import net.minecraft.util.GsonHelper;

public class TemperatureRange implements RecipeComponent<TemperatureRange> {

    public static TemperatureRange EMPTY = new TemperatureRange();

    @Nullable
    private Integer min_temp;

    @Nullable
    private Integer max_temp;

    public TemperatureRange() {
    }

    public TemperatureRange(Integer min_temp, Integer max_temp) {
        this.min_temp = min_temp;
        this.max_temp = max_temp;
    }

    public void min_temp(Integer min_temp) {
        this.min_temp = min_temp;
    }

    public void max_temp(Integer max_temp) {
        this.max_temp = max_temp;
    }

    public void temperature(Integer min_temp, Integer max_temp) {
        min_temp(min_temp);
        max_temp(max_temp);
    }

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
