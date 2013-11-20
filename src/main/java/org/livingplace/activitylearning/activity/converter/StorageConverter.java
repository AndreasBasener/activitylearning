package org.livingplace.activitylearning.activity.converter;

import java.lang.reflect.Type;

import org.livingplace.activitylearning.event.StorageEvent;
import org.livingplace.scriptsimulator.script.entry.StorageEntry.StorageAction;
import org.livingplace.scriptsimulator.script.entry.StorageEntry.StorageID;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class StorageConverter implements JsonSerializer<StorageEvent>,
JsonDeserializer<StorageEvent>{

	public StorageEvent deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {

		JsonObject object = json.getAsJsonObject();
		
		StorageID id = StorageID.valueOf(object.get("id").getAsString());
		StorageAction action = StorageAction.valueOf(object.get("action").getAsString());
		long time = object.get("time").getAsLong();
		
		return new StorageEvent(id,action,time);
	}

	public JsonElement serialize(StorageEvent src, Type typeOfSrc,
			JsonSerializationContext context) {

		JsonObject object = new JsonObject();
		
		object.addProperty("time", src.getTime());
		object.addProperty("id", src.getId().toString());
		object.addProperty("action", src.getAction().toString());
		
		return object;
	}

}
