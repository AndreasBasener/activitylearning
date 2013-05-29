package org.livingplace.activitylearning.data;

import org.livingplace.scriptsimulator.script.entry.WindowEntry.WindowAction;
import org.livingplace.scriptsimulator.script.entry.WindowEntry.WindowID;

public class WindowData extends Data{

	private WindowID id;
	private WindowAction action;
	
	public WindowData(String data)
	{
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		id = WindowID.valueOf(strarr[1]);
		action = WindowAction.valueOf(strarr[2]);
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
	public WindowID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(WindowID id) {
		this.id = id;
	}

	/**
	 * @return the action
	 */
	public WindowAction getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(WindowAction action) {
		this.action = action;
	}

}
