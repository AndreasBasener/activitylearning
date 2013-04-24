package org.livingplace.activitylearning;

import java.util.ArrayList;
import java.util.List;

import org.livingplace.activitylearning.data.PositionData;

public class Sequence {

	private List<PositionData> sequence;
	private int startIndex;
	
	private List<PositionData> parentList;
	
	public Sequence()
	{
		this.sequence = new ArrayList<PositionData>();
	}
	
	public Sequence(PositionData data, int startIndex, List<PositionData> source)
	{
		this();
		this.startIndex = startIndex;
		this.parentList = source;
		addElement(data);
	}
	
	public Sequence(List<PositionData> data, int startIndex, List<PositionData> source)
	{
		this();
		this.startIndex = startIndex;
		this.parentList = source;
		addList(data);
	}
	
	public void addElement(PositionData element)
	{
		this.sequence.add(element);
	}
	
	public void addList(List<PositionData> list)
	{
		this.sequence.addAll(list);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == this)
			return true;
		
		if(!(o instanceof Sequence))
			return false;
		
		Sequence s = (Sequence) o;
		
		boolean bool = false;
		if(sequence.size() == s.sequence.size())
		{
			for(int i = 0; i < sequence.size(); i++)
			{
				bool = sequence.get(i).equals(s.sequence.get(i));
				if (!bool)
					return false;
			}

			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void extend()
	{
		if(startIndex > 0)
		{
			PositionData data = parentList.get(startIndex-1);
			
			List<PositionData> list = new ArrayList<PositionData>();
			list.add(data);
			list.addAll(sequence);
			
			this.sequence = list;
			this.startIndex -= 1;
		}
		if((startIndex + sequence.size()) < (parentList.size() - 1))
		{
			this.parentList.add(parentList.get(startIndex + sequence.size()));
		}
	}

	/**
	 * @return the sequence
	 */
	public List<PositionData> getSequence() {
		return sequence;
	}

	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(List<PositionData> sequence) {
		this.sequence = sequence;
	}
}
