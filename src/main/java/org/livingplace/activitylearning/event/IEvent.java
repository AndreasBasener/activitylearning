package org.livingplace.activitylearning.event;


/**
 * 
 * @author Andreas Basener
 *
 */
public interface IEvent {

	public double distanceTo(IEvent event);
	public String toShortString();
	
	public long getTime();
	public void setTime(long time);
	
	public Copy getCopy();
	public void setCopy(Copy copy);
	
}
