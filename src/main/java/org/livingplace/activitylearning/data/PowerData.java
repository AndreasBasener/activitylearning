package org.livingplace.activitylearning.data;

import org.livingplace.scriptsimulator.script.entry.PowerEntry.PowerID;
import org.livingplace.scriptsimulator.script.entry.PowerEntry.PowerState;

public class PowerData extends Data{

	private PowerID id;
	private PowerState state;
	
	public PowerData(String data)
	{
		super();
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		id = PowerID.valueOf(strarr[1]);
		state = PowerState.valueOf(strarr[2]);
		
	}
	
	public double distanceTo(IData data) {
		if(data == this)
			return 0;
		if(data instanceof PowerData)
		{
			PowerData s = (PowerData) data;
			if(this.equals(s))
				return 0;
			else
				return 1;
		}
		return -1;
	}
	
	public boolean equals(Object o)
	{
		if(o == null)
			return false;
		if(!(o instanceof PowerData))
			return false;
		
		PowerData data = (PowerData) o;
		
		return id.equals(data.id) &&
				state.equals(data.state);
	}

	public String toShortString()
	{
		return id + ":" + state;
	}
	
	public String toString()
	{
		return "ID: " + id + " State: " + state;
	}

	/**
	 * @return the id
	 */
	public PowerID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(PowerID id) {
		this.id = id;
	}

	/**
	 * @return the state
	 */
	public PowerState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(PowerState state) {
		this.state = state;
	}

}
