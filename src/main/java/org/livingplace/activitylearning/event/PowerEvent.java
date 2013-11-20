package org.livingplace.activitylearning.event;

import org.livingplace.scriptsimulator.script.entry.PowerEntry.PowerID;
import org.livingplace.scriptsimulator.script.entry.PowerEntry.PowerState;

public class PowerEvent extends Event{

	private PowerID id;
	private PowerState state;
	
	public PowerEvent(String data)
	{
		super();
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		id = PowerID.valueOf(strarr[1]);
		state = PowerState.valueOf(strarr[2]);
		
	}
	
	public PowerEvent(PowerID id, PowerState state, long time)
	{
		this.id = id;
		this.state = state;
		this.time = time;
	}
	
	public double distanceTo(IEvent event) {
		if(event == this)
			return 0;
		if(event instanceof PowerEvent)
		{
			PowerEvent d = (PowerEvent) event;
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
		if(!(o instanceof PowerEvent))
			return false;
		
		PowerEvent data = (PowerEvent) o;
		
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
