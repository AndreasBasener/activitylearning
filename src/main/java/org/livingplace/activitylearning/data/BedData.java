package org.livingplace.activitylearning.data;

import org.livingplace.scriptsimulator.script.entry.BedEntry.SleepState;

public class BedData extends Data{

	private SleepState state;
	
	public BedData(String data)
	{
		super();
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		state = SleepState.valueOf(strarr[1]);
	}

	public double distanceTo(IData data) {
		if(data == this)
			return 0;
		if(data instanceof BedData)
		{
			BedData s = (BedData) data;
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
		if(!(o instanceof BedData))
			return false;
		
		BedData data = (BedData) o;
		
		return state.equals(data.state);
	}

	public String toShortString()
	{
		return state.toString();
	}
	
	public String toString()
	{
		return "State: " + state;
	}
	
	/**
	 * @return the state
	 */
	public SleepState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(SleepState state) {
		this.state = state;
	}

}
