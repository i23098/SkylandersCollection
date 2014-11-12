package collections.skylanders.installer.service;

import collections.serverapi.service.ItemServiceExtraParser;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ItemServiceJsonExtraParser implements ItemServiceExtraParser<JsonElement> {

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
}
