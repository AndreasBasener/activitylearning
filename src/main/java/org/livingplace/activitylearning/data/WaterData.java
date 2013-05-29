package org.livingplace.activitylearning.data;

import org.livingplace.scriptsimulator.script.entry.WaterEntry.WaterID;
import org.livingplace.scriptsimulator.script.entry.WaterEntry.WaterState;

public class WaterData extends Data{

	private WaterID id;
	private WaterState state;
	
	public WaterData(String data)
	{
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		id = WaterID.valueOf(strarr[1]);
		state = WaterState.valueOf(strarr[2]);
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
	public WaterID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(WaterID id) {
		this.id = id;
	}

	/**
	 * @return the state
	 */
	public WaterState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(WaterState state) {
		this.state = state;
	}

}
