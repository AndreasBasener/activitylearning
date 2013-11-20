package org.livingplace.activitylearning.event;

import org.livingplace.scriptsimulator.script.entry.WaterEntry.WaterID;
import org.livingplace.scriptsimulator.script.entry.WaterEntry.WaterState;

public class WaterEvent extends Event{

	private WaterID id;
	private WaterState state;
	
	public WaterEvent(String data)
	{
		super();
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		id = WaterID.valueOf(strarr[1]);
		state = WaterState.valueOf(strarr[2]);
	}
	
	public WaterEvent(WaterID id, WaterState state, long time)
	{
		this.id = id;
		this.state = state;
		this.time = time;
	}
	
	public double distanceTo(IEvent event) {
		if(event == this)
			return 0;
		if(event instanceof WaterEvent)
		{
			WaterEvent d = (WaterEvent) event;
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
		if(!(o instanceof WaterEvent))
			return false;
		
		WaterEvent data = (WaterEvent) o;
		
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
