package org.livingplace.activitylearning.activity.converter;

import java.lang.reflect.Type;

import org.livingplace.activitylearning.event.BedEvent;
import org.livingplace.scriptsimulator.script.entry.BedEntry.SleepState;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BedConverter implements JsonSerializer<BedEvent>,
JsonDeserializer<BedEvent>{

	public BedEvent deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject object = json.getAsJsonObject();
		
		SleepState state = SleepState.valueOf(object.get("state").getAsString());
		long time = object.get("time").getAsLong();
		
		return new BedEvent(state,time);
	}

	public JsonElement serialize(BedEvent src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject object = new JsonObject();

		object.addProperty("time", src.getTime());
		object.addProperty("state", src.getState().toString());
		
		return object;
	}

}
