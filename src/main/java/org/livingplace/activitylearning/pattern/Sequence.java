package org.livingplace.activitylearning.pattern;

import java.util.ArrayList;
import java.util.List;

import org.livingplace.activitylearning.data.IData;

public class Sequence {
	
	private int startIndex;
	
	private List<IData> dataList;
	private List<IData> dataSequence;
	
	public Sequence()
	{
		this.dataSequence = new ArrayList<IData>();
	}
	
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
		this.dataSequence.addAll(sequence.dataSequence);
		this.dataList = sequence.dataList;
		this.startIndex = sequence.startIndex;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == this)
			return true;
		
		if(!(o instanceof Sequence))
			return false;
		
		Sequence s = (Sequence) o;
		
		return dataSequence.equals(s.dataSequence);
		
//		boolean bool = false;
//		if(dataSequence.size() == s.dataSequence.size())
//		{
//			for(int i = 0; i < dataSequence.size(); i++)
//			{
//				bool = dataSequence.get(i).equals(s.dataSequence.get(i));
//				if(!bool)
//					return false;
//			}
//			return true;
//		}
//		else
//		{
//			return false;
//		}
	}
	
	public boolean extend()
	{
		boolean extended = false;
		//Event voranstellen
//		if(startIndex > 0)
//		{
//			this.dataSequence.add(0, dataList.get(startIndex-1));
//			
//			this.startIndex -= 1;
//			extended = true;
//		}
		//Event anhängen
		if((startIndex + dataSequence.size()) < dataList.size())
		{
			this.dataSequence.add(dataList.get(startIndex + dataSequence.size()));
			extended = true;
		}
		return extended;
	}
	
//	public Point3D getCenter()
//	{
//		double x = 0, y = 0, z = 0, count = 0;
//		
//		for(IData d: dataSequence)
//		{
//			if(d instanceof PositionData)
//			{
//				PositionData p = (PositionData) d;
//				x += p.getX();
//				y += p.getY();
//				count++;
//			}
//		}
//		if(count > 0)
//		{
//			x /= count;
//			y /= count;
//			
//			return new Point3D(x, y, z);
//		}
//		
//		return null;
//	}

//	public double bestMatch(List<IData> data)
//	{
//		List<IData> list1;
//		List<IData> list2;
//		
//		int lengthdiff = dataSequence.size() - data.size();
//		int maxlength = data.size();
//		
//		int matches[];
//		
//		if(lengthdiff < 0)
//		{
//			list1 = data;
//			list2 = dataSequence;
//			matches = new int[(lengthdiff * -1) + 1];
//		}
//		else
//		{
//			list1 = dataSequence;
//			list2 = data;
//			matches = new int[lengthdiff + 1];
//		}
//		
//		for(int i = 0; i <= lengthdiff; i++)
//		{
//			for(int j = 0; j < list2.size(); j++)
//			{
//				if(list1.get(j+i).equals(list2.get(j)))
//					matches[i]++;
//			}
//		}
//		
//		int bestmatch = 0;
//		
//		for(int i: matches)
//		{
//			if(i > bestmatch)
//				bestmatch = i;
//		}
//		
//		if(maxlength == 0)
//			return -1;
//		else
//			return bestmatch/maxlength;
//	}
	
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
