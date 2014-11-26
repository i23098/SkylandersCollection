package collections.skylanders.installer.service;

import java.util.HashMap;
import java.util.Map;

import collections.serverapi.service.ItemServiceExtraConverter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class JsonItemServiceExtraConverter implements ItemServiceExtraConverter<JsonElement> {
    
    @Override
    public JsonElement fromString(String extra) {
        if (extra == null) {
            return null;
        }
        
        return new JsonParser().parse(extra);
    }
    
    @Override
    public String toString(JsonElement extra) {
        if (extra == null) {
            return null;
        }
        
        return extra.toString();
    }

    @Override
    public Map<String, Object> toMap(JsonElement extra) {
        if (extra.isJsonNull()) {
            return null;
        }
        
        return convertJson(extra.getAsJsonObject());
    }
    
    Object convertJson(JsonElement jsonElement) {
        if (jsonElement.isJsonPrimitive()) {
            return convertJson(jsonElement.getAsJsonPrimitive());
        }
        
        if (jsonElement.isJsonArray()) {
            return convertJson(jsonElement.getAsJsonArray());
        }
        
        return convertJson(jsonElement.getAsJsonObject());
    }
    
    Object convertJson(JsonPrimitive jsonPrimitive) {
        if (jsonPrimitive.isBoolean()) {
            return jsonPrimitive.getAsBoolean();
        }
        
        if (jsonPrimitive.isNumber()) {
            return jsonPrimitive.getAsNumber();
        }
        
        return jsonPrimitive.getAsString();
    }
    
    Map<String, Object> convertJson(JsonObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            if (!entry.getValue().isJsonNull()) {
                map.put(entry.getKey(), convertJson(entry.getValue()));
            }
        }
        
        return map;
    }
}
