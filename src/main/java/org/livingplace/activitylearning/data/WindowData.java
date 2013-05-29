package org.livingplace.activitylearning.data;

import org.livingplace.scriptsimulator.script.entry.WindowEntry.WindowAction;
import org.livingplace.scriptsimulator.script.entry.WindowEntry.WindowID;

public class WindowData extends Data{

	private WindowID id;
	private WindowAction action;
	
	public WindowData(String data)
	{
		super();
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		id = WindowID.valueOf(strarr[1]);
		action = WindowAction.valueOf(strarr[2]);
	}
	
	public double distanceTo(IData data) {
		if(data == this)
			return 0;
		if(data instanceof WindowData)
		{
			WindowData s = (WindowData) data;
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
		if(!(o instanceof WindowData))
			return false;
		
		WindowData data = (WindowData) o;
		
		return id.equals(data.id) &&
				action.equals(data.action);
	}

	public String toShortString()
	{
		return id + ":" + action;
	}
	
	public String toString()
	{
		return "ID: " + id + " Action: " + action;
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
