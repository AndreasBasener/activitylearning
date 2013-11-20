package org.livingplace.activitylearning.activity.converter;

import java.lang.reflect.Type;

import org.livingplace.activitylearning.event.WaterEvent;
import org.livingplace.scriptsimulator.script.entry.WaterEntry.WaterID;
import org.livingplace.scriptsimulator.script.entry.WaterEntry.WaterState;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class WaterConverter implements JsonSerializer<WaterEvent>,
JsonDeserializer<WaterEvent>{

	public WaterEvent deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {

		JsonObject object = json.getAsJsonObject();
		
		WaterID id = WaterID.valueOf(object.get("id").getAsString());
		WaterState state = WaterState.valueOf(object.get("state").getAsString());
		long time = object.get("time").getAsLong();
		
		return new WaterEvent(id,state,time);
	}

	public JsonElement serialize(WaterEvent src, Type typeOfSrc,
			JsonSerializationContext context) {

		JsonObject object = new JsonObject();
		
		object.addProperty("time", src.getTime());
		object.addProperty("id", src.getId().toString());
		object.addProperty("state", src.getState().toString());
		
		return object;
	}

}
