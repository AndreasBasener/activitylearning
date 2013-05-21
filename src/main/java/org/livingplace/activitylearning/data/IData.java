package org.livingplace.activitylearning.data;

import org.livingplace.activitylearning.Copy;

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
