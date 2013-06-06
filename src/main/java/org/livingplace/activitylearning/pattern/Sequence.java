package org.livingplace.activitylearning.pattern;

import java.util.ArrayList;
import java.util.List;

import org.livingplace.activitylearning.data.IData;
import org.livingplace.activitylearning.data.PositionData;
import org.livingplace.scriptsimulator.Point3D;

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
		
		boolean bool = false;
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
			this.dataSequence.add(0, dataList.get(startIndex-1));
			
			this.startIndex -= 1;
			extended = true;
		}
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
