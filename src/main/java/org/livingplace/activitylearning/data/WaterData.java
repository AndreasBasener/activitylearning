package org.livingplace.activitylearning.data;

import org.livingplace.scriptsimulator.script.entry.WaterEntry.WaterID;
import org.livingplace.scriptsimulator.script.entry.WaterEntry.WaterState;

public class WaterData extends Data{

	private WaterID id;
	private WaterState state;
	
	public WaterData(String data)
	{
		super();
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		id = WaterID.valueOf(strarr[1]);
		state = WaterState.valueOf(strarr[2]);
	}
	
	public WaterData(WaterID id, WaterState state, long time)
	{
		this.id = id;
		this.state = state;
		this.time = time;
	}
	
	public double distanceTo(IData data) {
		if(data == this)
			return 0;
		if(data instanceof WaterData)
		{
			WaterData d = (WaterData) data;
			if(id.equals(d.id))
			{
				if(state.equals(d.state))
				{
					return 0;
				}
				else
				{
					return 0.5;
				}
			}
			else
			{
				return 1;
			}
		}
		return 1;
	}
	
	public boolean equals(Object o)
	{
		if(o == null)
			return false;
		if(!(o instanceof WaterData))
			return false;
		
		WaterData data = (WaterData) o;
		
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
