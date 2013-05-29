package org.livingplace.activitylearning.data;

import org.livingplace.scriptsimulator.script.entry.PowerEntry.PowerID;
import org.livingplace.scriptsimulator.script.entry.PowerEntry.PowerState;

public class PowerData extends Data{

	private PowerID id;
	private PowerState state;
	
	public PowerData(String data)
	{
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		id = PowerID.valueOf(strarr[1]);
		state = PowerState.valueOf(strarr[2]);
		
	}
	
	public double distanceTo(IData data) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toShortString() {
		// TODO Auto-generated method stub
		return null;
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
