package org.livingplace.activitylearning;

import java.util.ArrayList;
import java.util.List;

import org.livingplace.activitylearning.data.IData;
import org.livingplace.activitylearning.data.PositionData;

public class Sequence {

	@Deprecated
	private List<PositionData> sequence;
	@Deprecated
	private List<PositionData> eventList;
	
	private int startIndex;
	
	private List<IData> dataList;
	private List<IData> dataSequence;
	
	public Sequence()
	{
		this.sequence = new ArrayList<PositionData>();
		this.dataSequence = new ArrayList<IData>();
	}
	
//	public Sequence(PositionData data, int startIndex, List<PositionData> source)
//	{
//		this();
//		this.startIndex = startIndex;
//		this.eventList = source;
//		addElement(data);
//	}
	
//	public Sequence(List<PositionData> data, int startIndex, List<PositionData> source)
//	{
//		this();
//		this.startIndex = startIndex;
//		this.eventList = source;
//		addList(data);
//	}
	
	public Sequence(IData data, int index, List<IData> source)
	{
		this();
		dataSequence.add(data);
		this.startIndex = index;
		this.dataList = source;
	}
	
	public Sequence(List<IData> data, int index, List<IData> source)
	{
		this();
		this.startIndex = index;
		this.dataList = source;
		this.dataSequence.addAll(data);
	}
	
	public Sequence(Sequence sequence)
	{
		this();
		this.sequence.addAll(sequence.getSequence());
		this.eventList = sequence.eventList;
		this.dataSequence.addAll(sequence.dataSequence);
		this.dataList = sequence.dataList;
		this.startIndex = sequence.startIndex;
	}
	
	@Deprecated
	public void addElement(PositionData element)
	{
		this.sequence.add(element);
	}
	@Deprecated
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
//		if(sequence.size() == s.sequence.size())
//		{
//			for(int i = 0; i < sequence.size(); i++)
//			{
//				bool = sequence.get(i).equals(s.sequence.get(i));
//				if (!bool)
//					return false;
//			}
//
//			return true;
//		}
		if(dataSequence.size() == s.dataSequence.size())
		{
			for(int i = 0; i < dataSequence.size(); i++)
			{
				bool = dataSequence.get(i).equals(s.dataSequence.get(i));
				if(!bool)
					return false;
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean extend()
	{
		boolean extended = false;
		if(startIndex > 0)
		{
//			PositionData data = eventList.get(startIndex-1);
			
//			List<PositionData> list = new ArrayList<PositionData>();
			
//			list.add(data);
//			list.addAll(sequence);

			this.dataSequence.add(0, dataList.get(startIndex-1));
			
//			this.sequence = list;
			this.startIndex -= 1;
			extended = true;
		}
		if((startIndex + dataSequence.size()) < dataList.size())
		{
			this.dataSequence.add(dataList.get(startIndex + dataSequence.size()));
			extended = true;
		}
//		if((startIndex + sequence.size()) < eventList.size())
//		{
//			this.sequence.add(eventList.get(startIndex + sequence.size()));
//			extended = true;
//		}
//		System.out.println("Ende: " + this);
		return extended;
	}

	public String toString()
	{
		String s = "Index: " + startIndex + " Sequence: ";
		for(IData d: dataSequence)
		{
			s += d.toShortString() + " ";
		}
		return s;
	}
	
	/**
	 * @return the sequence
	 */
	@Deprecated
	public List<PositionData> getSequence() {
		return sequence;
	}

	/**
	 * @param sequence the sequence to set
	 */
	@Deprecated
	public void setSequence(List<PositionData> sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * @return the dataList
	 */
	public List<IData> getDataList() {
		return dataList;
	}

	/**
	 * @param dataList the dataList to set
	 */
	public void setDataList(List<IData> dataList) {
		this.dataList = dataList;
	}

	/**
	 * @return the dataSequence
	 */
	public List<IData> getDataSequence() {
		return dataSequence;
	}

	/**
	 * @param dataSequence the dataSequence to set
	 */
	public void setDataSequence(List<IData> dataSequence) {
		this.dataSequence = dataSequence;
	}
	
}
