package org.livingplace.activitylearning.data;

import org.livingplace.scriptsimulator.script.entry.BedEntry.SleepState;

public class BedData extends Data{

	private SleepState state;
	
	public BedData(String data)
	{
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		state = SleepState.valueOf(strarr[1]);
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
