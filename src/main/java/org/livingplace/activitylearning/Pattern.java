package org.livingplace.activitylearning;

import java.util.ArrayList;
import java.util.List;

public class Pattern {

	private List<Sequence> sequenceList;
	
	private double value;
	private double meantime;
	private double stddevtime;
	
	public Pattern()
	{
		this.sequenceList = new ArrayList<Sequence>();
	}
	
	public Pattern(Sequence sequence)
	{
		this();
		addSequence(sequence);
		increasePatternCount();
	}
	
	public void addSequence(Sequence sequence)
	{
		this.sequenceList.add(sequence);
	}
	
	public void increasePatternCount()
	{
		
	}
	
	public boolean containsSequence(Sequence seq)
	{
		boolean bool = false;
		for(Sequence s : sequenceList)
		{
			if (s.equals(seq))
			{
				bool = true;
			}
		}
		
		return bool;
	}
	
	public void evaluate(int eventCount)
	{
		double sum = 0, diff = 0;
		int newsize = 0;
		if(sequenceList.size() == 0)
		{
			this.value = 0;
		}
		else if (sequenceList.size() == 1)
		{
			this.value = sequenceList.size() / eventCount;
		}
		else
		{
			for(Sequence s : sequenceList)
			{
				sum += s.getSequence().get(0).getTime();
			}
			meantime = sum / sequenceList.size();
			
			sum = 0;
			for(Sequence s : sequenceList)
			{
				diff = s.getSequence().get(0).getTime() - this.meantime;
				sum += diff * diff;
			}
			stddevtime = Math.sqrt(sum / sequenceList.size());

			newsize = eventCount -
					  (sequenceList.get(0).getSequence().size() * sequenceList.size()) +
					  sequenceList.size() +
					  sequenceList.get(0).getSequence().size();
			
			if (newsize == 0)
				this.value = 0;
			else
				this.value = eventCount / newsize;
		}
	}

	/**
	 * @return the sequenceList
	 */
	public List<Sequence> getSequenceList() {
		return sequenceList;
	}

	/**
	 * @param sequenceList the sequenceList to set
	 */
	public void setSequenceList(List<Sequence> sequenceList) {
		this.sequenceList = sequenceList;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * @return the meantime
	 */
	public double getMeantime() {
		return meantime;
	}

	/**
	 * @param meantime the meantime to set
	 */
	public void setMeantime(double meantime) {
		this.meantime = meantime;
	}

	/**
	 * @return the stddevtime
	 */
	public double getStddevtime() {
		return stddevtime;
	}

	/**
	 * @param stddevtime the stddevtime to set
	 */
	public void setStddevtime(double stddevtime) {
		this.stddevtime = stddevtime;
	}
	
	public void extend()
	{
		for(Sequence s: sequenceList)
		{
			s.extend();
		}
	}
	
}
