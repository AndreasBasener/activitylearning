package org.livingplace.activitylearning.data;


/**
 * 
 * @author Andreas Basener
 *
 */
public interface IData {

	public double distanceTo(IData data);
	
	public long getTime();
	public void setTime(long time);
	
	public Copy getCopy();
	public void setCopy(Copy copy);
	
	public String toShortString();
}
