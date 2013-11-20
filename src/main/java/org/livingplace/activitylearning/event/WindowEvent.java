package org.livingplace.activitylearning.event;

import org.livingplace.scriptsimulator.script.entry.WindowEntry.WindowAction;
import org.livingplace.scriptsimulator.script.entry.WindowEntry.WindowID;

public class WindowEvent extends Event{

	private WindowID id;
	private WindowAction action;
	
	public WindowEvent(String data)
	{
		super();
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		id = WindowID.valueOf(strarr[1]);
		action = WindowAction.valueOf(strarr[2]);
	}
	
	public double distanceTo(IEvent event) {
		if(event == this)
			return 0;
		if(event instanceof WindowEvent)
		{
			WindowEvent d = (WindowEvent) event;
			if(id.equals(d.id))
			{
				if(action.equals(d.action))
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
		if(!(o instanceof WindowEvent))
			return false;
		
		WindowEvent data = (WindowEvent) o;
		
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
