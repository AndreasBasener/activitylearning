/**
 * 
 */
package org.livingplace.activitylearning.activity.converter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.livingplace.activitylearning.activity.Activity;
import org.livingplace.activitylearning.event.IEvent;
import org.livingplace.activitylearning.pattern.Pattern;
import org.livingplace.activitylearning.pattern.Cluster;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @author Andreas Basener
 *
 */
public class ActivityConverter implements JsonSerializer<Activity>,
JsonDeserializer<Activity>{

	public Activity deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		
//		JsonObject object = json.getAsJsonObject();
		
		
		return null;
	}

	public JsonElement serialize(Activity src, Type typeOfSrc,
			JsonSerializationContext context) {

		JsonObject object = new JsonObject();
		Cluster pc = src.getPc();
		
		//List types wird benötigt, weil JsonArray kein contains unterstützt.
		List<String> types = new ArrayList<String>();
		JsonArray typesArray = new JsonArray();
		
//		List<JsonElement> patternElementList = new ArrayList<JsonElement>();
		JsonArray patternArray = new JsonArray();
		
		for(Pattern p: pc.getPatternList())
		{
			JsonArray dataArray = new JsonArray();
			for(IEvent d: p.getSequence().getDataSequence())
			{
				String typename = d.getClass().getSimpleName();
				if(!types.contains(typename))
				{
					types.add(typename);
					typesArray.add(new JsonPrimitive(typename));
				}
				JsonElement patternElement = context.serialize(d);
//				patternElementList.add(patternElement);
				dataArray.add(patternElement);
			}
			patternArray.add(dataArray);
		}
		
		object.add("containedTypes", typesArray);
		object.add("patternList", patternArray);
		
		return object;
	}

}
