package org.livingplace.activitylearning;

import java.util.ArrayList;
import java.util.List;

import org.livingplace.activitylearning.data.PositionData;

public class Pattern {

//	private List<Sequence> sequenceList;
	private Sequence sequence;
	
	private double value;
	private double meantime;
	private double stddevtime;
	
	private int patternCount = 0;
	
	public Pattern()
	{
//		this.sequenceList = new ArrayList<Sequence>();
		this.sequence = new Sequence();
	}
	
	public Pattern(Sequence sequence)
	{
		this();
		this.sequence = sequence;
//		addSequence(sequence);
		increasePatternCount();
	}
	public Pattern(Pattern pattern)
	{
//		this.sequenceList = pattern.sequenceList;
		this.sequence = new Sequence(pattern.sequence);
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
	
	public void increasePatternCount()
	{
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
	
	
}
