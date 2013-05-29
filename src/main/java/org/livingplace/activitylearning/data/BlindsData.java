package org.livingplace.activitylearning.data;

import org.livingplace.scriptsimulator.script.entry.BlindsEntry.BlindsAction;
import org.livingplace.scriptsimulator.script.entry.BlindsEntry.BlindsID;

public class BlindsData extends Data{

	private BlindsID id;
	private BlindsAction action;
	
	public BlindsData(String data)
	{
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		id = BlindsID.valueOf(strarr[1]);
		action = BlindsAction.valueOf(strarr[2]);
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
	 * @return the action
	 */
	public BlindsAction getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(BlindsAction action) {
		this.action = action;
	}

}
