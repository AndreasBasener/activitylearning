package org.livingplace.activitylearning.activity;

import org.livingplace.activitylearning.pattern.PatternCluster;

import com.google.gson.Gson;

/**
 * 
 * @author Andreas Basener
 *
 */
public class Activity {
	
	private PatternCluster pc;
	private Gson gson;
	
	private String gsonString;

	public Activity(PatternCluster pc, Gson gson)
	{
		this.pc = pc;
		this.gson = gson;
		
		gsonString = gson.toJson(this);
	}

	/**
	 * @return the pc
	 */
	public PatternCluster getPc() {
		return pc;
	}

	/**
	 * @param pc the pc to set
	 */
	public void setPc(PatternCluster pc) {
		this.pc = pc;
	}

	/**
	 * @return the gson
	 */
	public Gson getGson() {
		return gson;
	}

	/**
	 * @param gson the gson to set
	 */
	public void setGson(Gson gson) {
		this.gson = gson;
	}

	/**
	 * @return the gsonString
	 */
	public String getGsonString() {
		return gsonString;
	}

	/**
	 * @param gsonString the gsonString to set
	 */
	public void setGsonString(String gsonString) {
		this.gsonString = gsonString;
	}

}
