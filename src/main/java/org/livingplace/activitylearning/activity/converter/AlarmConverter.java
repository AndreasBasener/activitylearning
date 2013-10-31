package org.livingplace.activitylearning.activity.converter;

import java.lang.reflect.Type;

import org.livingplace.activitylearning.data.AlarmData;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AlarmConverter implements JsonSerializer<AlarmData>,
JsonDeserializer<AlarmData>{

	public AlarmData deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {

		JsonObject object = json.getAsJsonObject();
		
		String name = object.get("name").getAsString();
		String descr = object.get("description").getAsString();
		long time = object.get("time").getAsLong();
		
		return new AlarmData(name,descr,time);
	}

	public JsonElement serialize(AlarmData src, Type typeOfSrc,
			JsonSerializationContext context) {
		
		JsonObject object = new JsonObject();

		object.addProperty("time", src.getTime());
		object.addProperty("name", src.getName());
		object.addProperty("description", src.getDescription());
		
		return object;
	}

}
