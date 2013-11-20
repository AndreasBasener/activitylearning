package org.livingplace.activitylearning.activity.converter;

import java.lang.reflect.Type;

import org.livingplace.activitylearning.event.PowerEvent;
import org.livingplace.scriptsimulator.script.entry.PowerEntry.PowerID;
import org.livingplace.scriptsimulator.script.entry.PowerEntry.PowerState;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PowerConverter implements JsonSerializer<PowerEvent>,
JsonDeserializer<PowerEvent>{

	public PowerEvent deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject object = json.getAsJsonObject();
		
		PowerID id = PowerID.valueOf(object.get("id").getAsString());
		PowerState state = PowerState.valueOf(object.get("state").getAsString());
		long time = object.get("time").getAsLong();
		
		return new PowerEvent(id,state,time);
	}

	public JsonElement serialize(PowerEvent src, Type typeOfSrc,
			JsonSerializationContext context) {

		JsonObject object = new JsonObject();
		
		object.addProperty("time", src.getTime());
		object.addProperty("id", src.getId().toString());
		object.addProperty("state", src.getState().toString());
		
		return object;
	}

}
