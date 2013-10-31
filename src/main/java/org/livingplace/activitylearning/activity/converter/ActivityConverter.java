/**
 * 
 */
package org.livingplace.activitylearning.activity.converter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.livingplace.activitylearning.activity.Activity;
import org.livingplace.activitylearning.data.IData;
import org.livingplace.activitylearning.pattern.Pattern;
import org.livingplace.activitylearning.pattern.PatternCluster;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
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
		PatternCluster pc = src.getPc();
		
		List<String> types = new ArrayList<String>();
		
		for(Pattern p: pc.getPatternList())
		{
			for(IData d: p.getSequence().getDataSequence())
			{
				String typename = d.getClass().getSimpleName();
				if(!types.contains(typename))
					types.add(typename);
			}
		}
		
		return object;
	}

}
