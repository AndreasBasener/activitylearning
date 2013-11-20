package org.livingplace.activitylearning.event;

public class Event  implements IEvent{

	protected long time;
	
	protected Copy copy;
	
	public Event()
	{
		copy = Copy.FALSE;
	}
	
	public double distanceTo(IEvent event) {
		return 1;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
		
	}

	public Copy getCopy() {
		return copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}

	public String toShortString() {
		return "";
	}

}
