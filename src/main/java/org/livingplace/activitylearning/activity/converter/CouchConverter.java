package org.livingplace.activitylearning.activity.converter;

import java.lang.reflect.Type;

import org.livingplace.activitylearning.event.CouchEvent;
import org.livingplace.scriptsimulator.script.entry.CouchEntry.CouchID;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CouchConverter implements JsonSerializer<CouchEvent>,
JsonDeserializer<CouchEvent>{

	public CouchEvent deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject object = json.getAsJsonObject();
		
		CouchID id = CouchID.valueOf(object.get("id").getAsString());
		long time = object.get("time").getAsLong();
		
		return new CouchEvent(id,time);
	}

	public JsonElement serialize(CouchEvent src, Type typeOfSrc,
			JsonSerializationContext context) {

		JsonObject object = new JsonObject();

		object.addProperty("time", src.getTime());
		object.addProperty("id", src.getCouchID().toString());
		
		return object;
	}

}
