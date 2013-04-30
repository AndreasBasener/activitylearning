package org.livingplace.activitylearning;

import java.util.ArrayList;
import java.util.List;

import org.livingplace.activitylearning.data.PositionData;

public class Pattern implements Comparable<Pattern>{

//	private List<Sequence> sequenceList;
	private Sequence sequence;
	
	private List<Integer> instances;
	
	private double value;
	private double meantime;
	private double stddevtime;
	
	private int patternCount = 0;
	
	private boolean used = false;
	
	public Pattern()
	{
//		this.sequenceList = new ArrayList<Sequence>();
		this.sequence = new Sequence();
		this.instances = new ArrayList<Integer>();
	}
	
	public Pattern(Sequence sequence, int index)
	{
		this();
		this.sequence = sequence;
//		addSequence(sequence);
		increasePatternCount(index);
	}
	public Pattern(Pattern pattern)
	{
//		this.sequenceList = pattern.sequenceList;
		this.sequence = new Sequence(pattern.sequence);
		this.instances = new ArrayList<Integer>();
		this.instances.addAll(instances);
//		increasePatternCount();
//		this.value = pattern.value;
//		this.meantime = pattern.meantime;
//		this.stddevtime = pattern.stddevtime;
//		this.patternCount = pattern.patternCount;
//		this.patternCount = 0;
	}
	
//	public void addSequence(Sequence sequence)
//	{
//		this.sequenceList.add(sequence);
//	}
	
	public void increasePatternCount(int index)
	{
		instances.add(index);
		patternCount++;
	}
	
	public boolean containsSequence(Sequence seq)
	{
//		boolean bool = false;
//		for(Sequence s : sequenceList)
//		{
//			if (s.equals(seq))
//			{
//				bool = true;
//			}
//		}
//		
//		return bool;

		return sequence.equals(seq);
	}
	
	public void evaluate(int eventCount)
	{
		double sum = 0, diff = 0;
		int newsize = 0;
		if(sequence == null || sequence.getSequence().size() == 0)
		{
			this.value = 0;
		}
		else if (sequence.getSequence().size() == 1)
		{
			this.value = (double) patternCount / (double) eventCount;
		}
		else
		{
			for(PositionData p : sequence.getSequence())
			{
				sum += p.getTime();
			}
			meantime = (double) sum / (double) sequence.getSequence().size();
			
			sum = 0;
			for(PositionData p : sequence.getSequence())
			{
				diff = p.getTime() - this.meantime;
				sum += diff * diff;
			}
			stddevtime = Math.sqrt((double) sum / (double) sequence.getSequence().size());

			newsize = eventCount -
					  (sequence.getSequence().size() * patternCount) +
					  sequence.getSequence().size() +
					  patternCount;
			
			if (newsize == 0)
				this.value = 0;
			else
				this.value = (double) eventCount / (double) newsize;
		}
//		System.out.println("Value :" + value);
	}
	
	public boolean extend()
	{
		return sequence.extend();
	}
	
	public String toString()
	{
		String s = "Value: " + value + " Count: " + patternCount + " - " + sequence;
		return s;
	}
	
	public boolean equals(Object o)
	{
		if(o == this)
			return true;
		
		if(!(o instanceof Pattern))
			return false;
		
		Pattern p = (Pattern) o;
		
		return this.sequence.equals(p.sequence) &&
				this.meantime == p.meantime &&
				this.patternCount == p.patternCount &&
				this.stddevtime == p.stddevtime &&
				this.value == p.value;
	}
	
	public int compareTo(Pattern o) {
		if (o.value > value)
			return 1;
		else if (o.value == value)
			return 0;
		else
			return -1;
	}
	
	public double distanceTo(Pattern p)
	{
		int overlaps = 0;
		int size1 = this.getSequence().getSequence().size();
		int size2 = p.getSequence().getSequence().size();
		int maxsize = 0;
		double sim = 0;
		double diff = this.meantime - p.getMeantime();
//		if(((int)this.meantime == (int)p.getMeantime()) 
//				|| (this.instances.size() == 2) 
//				|| (p.getInstances().size() == 2))
//			overlaps++;
		
		 if(Math.abs(diff) < 0.1)
			 overlaps++;
		 
		for(int i = 0; i < size1 && i < size2; i++)
		{
			PositionData p1 = this.getSequence().getSequence().get(i);
			PositionData p2 = p.getSequence().getSequence().get(i);
			if(p1.equals(p2))
				overlaps++;
		}
		
		if(size1 > size2)
		{
			sim = (double)size2 / (double)size1;
			maxsize = size1;
		}
		else
		{
			sim = (double)size1 / (double)size2;
			maxsize = size2;
		}
		
		sim *= (double)overlaps;
		sim /= maxsize;
		
		return sim;
	}

//	/**
//	 * @return the sequenceList
//	 */
//	public List<Sequence> getSequenceList() {
//		return sequenceList;
//	}
//
//	/**
//	 * @param sequenceList the sequenceList to set
//	 */
//	public void setSequenceList(List<Sequence> sequenceList) {
//		this.sequenceList = sequenceList;
//	}

	/**
	 * @return the instances
	 */
	public List<Integer> getInstances() {
		return instances;
	}

	/**
	 * @param instances the instances to set
	 */
	public void setInstances(List<Integer> instances) {
		this.instances = instances;
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
	 * @return the patternCount
	 */
	public int getPatternCount() {
		return patternCount;
	}

	/**
	 * @param patternCount the patternCount to set
	 */
	public void setPatternCount(int patternCount) {
		this.patternCount = patternCount;
	}

	/**
	 * @return the sequence
	 */
	public Sequence getSequence() {
		return sequence;
	}

	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
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

	/**
	 * @return the used
	 */
	public boolean isUsed() {
		return used;
	}

	/**
	 * @param used the used to set
	 */
	public void setUsed(boolean used) {
		this.used = used;
	}
	
	
}
