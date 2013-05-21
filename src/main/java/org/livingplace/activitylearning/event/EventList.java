package org.livingplace.activitylearning.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Andreas Basener
 *
 */
public class EventList {

	private List<IEvent> eventlist;
	
	
	public EventList()
	{
		this.eventlist = new ArrayList<IEvent>();
	}
	
	public void addEvent(IEvent event)
	{
		eventlist.add(event);
		Collections.sort(eventlist);
	}
}
