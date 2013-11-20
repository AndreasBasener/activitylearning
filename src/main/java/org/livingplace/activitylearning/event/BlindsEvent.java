package org.livingplace.activitylearning.event;

import org.livingplace.scriptsimulator.script.entry.BlindsEntry.BlindsID;
import org.livingplace.scriptsimulator.script.entry.BlindsEntry.BlindsState;

public class BlindsEvent extends Event{

	private BlindsID id;
	private BlindsState state;
	
	public BlindsEvent(String data)
	{
		super();
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		id = BlindsID.valueOf(strarr[1]);
		state = BlindsState.valueOf(strarr[2]);
	}
	
	public double distanceTo(IEvent event) {
		if(event == this)
			return 0;
		if(event instanceof BlindsEvent)
		{
			BlindsEvent d = (BlindsEvent) event;
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
		if(!(o instanceof BlindsEvent))
			return false;
		
		BlindsEvent data = (BlindsEvent) o;
		
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
	public BlindsID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(BlindsID id) {
		this.id = id;
	}

	/**
	 * @return the state
	 */
	public BlindsState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(BlindsState state) {
		this.state = state;
	}

}
