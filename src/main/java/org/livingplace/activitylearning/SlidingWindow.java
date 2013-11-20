package org.livingplace.activitylearning;

import java.util.List;

import org.livingplace.activitylearning.event.PositionEvent;

@Deprecated
public class SlidingWindow {

	private int windowSize;
	private int windowPosition;
	private boolean reachedEnd;
	
	private int listSize;
	
	private List<PositionEvent> positionEvent;
	
	private PositionEvent window[];
	
	public SlidingWindow(int size, List<PositionEvent> data)
	{
		this.windowSize = size;
		this.positionEvent = data;
		this.windowPosition = 0;
		this.reachedEnd = false;
		
		this.listSize = positionEvent.size();
		
		window = new PositionEvent[size];
		
		for(int i = 0; i < windowSize && i < listSize; i++)
		{
			window[i] = positionEvent.get(i);
			if(i == listSize -1)
				reachedEnd = true;
		}
	}
	
	public void slideWindow()
	{
		if (!reachedEnd)
		{
			for(int i = 0; i < windowSize; i++)
			{
				window[i] = positionEvent.get(windowPosition + i + 1);
				
			}
			windowPosition++;
			if(windowPosition + windowSize == listSize)
				reachedEnd = true;
		}
	}
	
	public String toString()
	{
		String s = "";
		for (int i = 0; i < window.length; i++)
		{
			s += window[i].getTime() + " ";
		}
//		s += reachEnd;
		return s;
	}
	
	public boolean hasReachedEnd()
	{
		return this.reachedEnd;
	}
}
