package org.livingplace.activitylearning.sensor;

public interface ISensor {
	/**
	 * Returns the name of the <code>Sensor</code>.
	 * @return
	 */
	public String getName();

	/**
	 * Sets the name of the <code>Sensor</code>.
	 * @param name
	 */
	public void setName(String name);

	/**
	 * Returns the type of the <code>Sensor</code>.
	 * 
	 * @return the type of the <code>Sensor</code>
	 */
	public String getType();
}
