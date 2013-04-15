package org.livingplace.activitylearning.event;

import org.joda.time.DateTime;
import org.livingplace.activitylearning.data.IData;

/**
 * 
 * @author Andreas Basener
 *
 */
public interface IEvent extends Comparable<IEvent>{

	public DateTime getTime();
	
	public IData getData();
	
	public int distanceTo(IEvent event);
	
	
	
}
