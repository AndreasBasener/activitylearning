package org.livingplace.activitylearning.data;

import org.livingplace.activitylearning.Copy;
import org.livingplace.scriptsimulator.script.entry.StorageEntry.StorageAction;
import org.livingplace.scriptsimulator.script.entry.StorageEntry.StorageID;

public class StorageData implements IData{

	private long time;
	private Copy copy;
	
	private StorageID id;
	
	private StorageAction action;
	
	public StorageData(StorageID id, StorageAction action)
	{
		this.id = id;
		this.action = action;
	}
	
	/**
	 * Calculates the distance to the given <code>Object</code>. If the data is an instance of
	 * <code>StorageData</code> and is equal to this instance, then the returned distance is 0. 
	 * If data is an instance of <code>StorageData</code> and is not equal to this instance, 
	 * the returned distance is 0. If data is not an instance of <code>StorageData</code>, -1 
	 * is returned.  
	 */
	public double distanceTo(IData data) {
		if(data == this)
			return 0;
		if(data instanceof StorageData)
		{
			StorageData s = (StorageData) data;
			if(this.equals(s))
				return 0;
			else
				return 1;
		}
		return -1;
	}
	
	public boolean equals(Object o)
	{
		if(o == this)
			return true;
		if(! (o instanceof StorageData))
			return false;
		
		StorageData s = (StorageData) o;
		
		return s.id.equals(id) &&
			   s.action.equals(action);
	}

	public String toString()
	{
		return "ID: " + id + " Action: " + action;
	}
	
	public String toShortString()
	{
		return id + ":" + action;
	}
	
	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the copy
	 */
	public Copy getCopy() {
		return copy;
	}

	/**
	 * @param copy the copy to set
	 */
	public void setCopy(Copy copy) {
		this.copy = copy;
	}

	/**
	 * @return the id
	 */
	public StorageID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(StorageID id) {
		this.id = id;
	}

	/**
	 * @return the action
	 */
	public StorageAction getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(StorageAction action) {
		this.action = action;
	}

}
