package org.livingplace.activitylearning;

import java.util.List;

import org.livingplace.activitylearning.data.PositionData;

@Deprecated
public class SlidingWindow {

	private int windowSize;
	private int windowPosition;
	private boolean reachedEnd;
	
	private int listSize;
	
	private List<PositionData> positionData;
	
	private PositionData window[];
	
	public SlidingWindow(int size, List<PositionData> data)
	{
		this.windowSize = size;
		this.positionData = data;
		this.windowPosition = 0;
		this.reachedEnd = false;
		
		this.listSize = positionData.size();
		
		window = new PositionData[size];
		
		for(int i = 0; i < windowSize && i < listSize; i++)
		{
			window[i] = positionData.get(i);
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
				window[i] = positionData.get(windowPosition + i + 1);
				
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
