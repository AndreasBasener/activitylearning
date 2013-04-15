package org.livingplace.activitylearning.sensor;

public class TemperatureSensor implements ISensor {

	private String name;
	
	private int temperature;

//	public TemperatureSensor() 
//	{
//	}
	
	public TemperatureSensor(String name)
	{
		this.name = name;
	}

	public String getName() 
	{
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.getClass().getSimpleName();
	}

	/**
	 * @return the temperature
	 */
	public int getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

}
