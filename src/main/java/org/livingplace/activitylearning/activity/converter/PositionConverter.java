package org.livingplace.activitylearning.activity.converter;

import java.lang.reflect.Type;

import org.livingplace.activitylearning.event.PositionEvent;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PositionConverter implements JsonSerializer<PositionEvent>,
JsonDeserializer<PositionEvent>{

	public PositionEvent deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		
		JsonObject object = json.getAsJsonObject();
		
		double x = object.get("x").getAsDouble();
		double y = object.get("y").getAsDouble();
		
		long time = object.get("time").getAsLong();
		
		return new PositionEvent(time,x,y);
	}

	public JsonElement serialize(PositionEvent src, Type typeOfSrc,
			JsonSerializationContext context) {

		JsonObject object = new JsonObject();
		
		object.addProperty("time", src.getTime());
		object.addProperty("x", src.getX());
		object.addProperty("y", src.getY());
		object.addProperty("fspace", src.getfSpace().getLabel().toString());
		
		return object;
	}

}
