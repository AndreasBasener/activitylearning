/**
 * 
 */
package org.livingplace.activitylearning.sensor;

import org.livingplace.scriptsimulator.script.entry.UbisenseMockupData;

import com.google.gson.Gson;


/**
 * @author Andreas Basener
 *
 */
public class UbisenseMockupSensor extends Sensor {

	private UbisenseMockupData data;
	
	
	public UbisenseMockupSensor(String name, Gson gson)
	{
		super(name,gson);
		this.name = name;
	}
	
	public String getName() 
	{
		return this.name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getType() 
	{
		return this.getClass().getSimpleName();
	}

	/**
	 * @return the data
	 */
	public UbisenseMockupData getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(UbisenseMockupData data) {
		this.data = data;
	}

}
