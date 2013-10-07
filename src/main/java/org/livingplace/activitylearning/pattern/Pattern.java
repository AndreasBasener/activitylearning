package org.livingplace.activitylearning.pattern;

import java.util.ArrayList;
import java.util.List;

import org.livingplace.activitylearning.Helper;
import org.livingplace.activitylearning.data.IData;

public class Pattern implements Comparable<Pattern>{

//	private List<Sequence> sequenceList;
	/**
	 * Die Sequenz, die dieses <code>Pattern</code> beschreibt.
	 */
	private Sequence sequence;
	
	/**
	 * Liste der Indizes, an denen das Muster im Datenstrom vorkommt.
	 */
	private List<Integer> instances;

	/**
	 * Gibt an, wie häufig das Pattern im Datenstrom vorkommt.
	 */
	private int patternCount = 0;
	
	private double value;
	private double meantime;
	private double stddevtime;
	
	
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
		if(sequence == null || sequence.getDataSequence().size() == 0)
		{
			this.value = 0;
		}
		else if (sequence.getDataSequence().size() == 1)
		{
			this.value = (double) patternCount / (double) eventCount;
		}
		else
		{
			for(IData d : sequence.getDataSequence())
			{
				sum += d.getTime();
			}
			meantime = (double) sum / (double) sequence.getDataSequence().size();
			
			sum = 0;
			for(IData d : sequence.getDataSequence())
			{
				diff = d.getTime() - this.meantime;
				sum += diff * diff;
			}
			stddevtime = Math.sqrt((double) sum / (double) sequence.getDataSequence().size());

			newsize = eventCount -
					  (sequence.getDataSequence().size() * patternCount) +
					  sequence.getDataSequence().size() +
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
		Sequence s1 = sequence;
		Sequence s2 = p.getSequence();
		
		int size1 = s1.getDataSequence().size();
		int size2 = s2.getDataSequence().size();
		
		double mean1 = this.meantime;
		double mean2 = p.meantime;
		double mean = 0;
		
		if(mean1 > mean2)
			mean = mean2 / mean1;
		else
			mean = mean1 / mean2;
		
		int sizediff = size1 - size2; 
		if(sizediff < 0)
		{
			Sequence temp = s1;
			s1 = s2;
			s2 = temp;
			
			int temps = size1;
			size1 = size2;
			size2 = temps;
			
			sizediff *= -1;
		}
		
		double dist[] = new double[sizediff + 1];
		for(int i = 0; i <= sizediff; i++)
		{
			for(int j = 0; j < size2; j++)
			{
				dist[i] += s1.getDataSequence().get(j + i).distanceTo(s2.getDataSequence().get(j));
			}
		}
		
		double mindist = Double.MAX_VALUE;
		double meandist = 0;
		for(double d: dist)
		{
			meandist += d;
			if(d < mindist)
			{
				mindist = d;
			}
		}
		meandist /= dist.length;
		
//		mindist += mean;
		
//		mindist /= size1;
//		if(this.sequence.getDataSequence().size() > 1)
//		{
//			System.out.println(this);
//			System.out.println(p);
//			System.out.println("Mindist: " + mindist + " sizediff: " + sizediff);
//		}
		return mindist + sizediff;
	}
	
//	public double distanceTo(Pattern p)
//	{
//		int overlaps = 0;
//		int size1 = this.getSequence().getDataSequence().size();
//		int size2 = p.getSequence().getDataSequence().size();
//		int maxsize = 0;
//		double sim = 0;
//		double mean1 = this.meantime;
//		double mean2 = p.getMeantime();
//		double mean = 0;
//		
//		if(mean1 > mean2)
//			mean = mean2 / mean1;
//		else
//			mean = mean1 / mean2;
//
//		for(int i = 0; i < size1 && i < size2; i++)
//		{
//			IData p1 = this.getSequence().getDataSequence().get(i);
//			IData p2 = p.getSequence().getDataSequence().get(i);
//			if(p1.equals(p2))
//				overlaps++;
//		}
//		
//		if(size1 > size2)
//		{
////			sim = (double)size2 / (double)size1;
//			maxsize = size1;
//		}
//		else
//		{
////			sim = (double)size1 / (double)size2;
//			maxsize = size2;
//		}
//		
//		sim = (double) overlaps / (double) maxsize;
//
//		sim *= 1 - Helper.MEAN_OVERLAP_RATIO;
//		
//		sim += mean * Helper.MEAN_OVERLAP_RATIO;
//		
//		return sim;
//	}
	
	public boolean containsPatternSequence(Pattern pattern)
	{
		boolean bool = false;
		List<IData> s1 = sequence.getDataSequence();
		List<IData> s2 = pattern.getSequence().getDataSequence();
		
		int index = s1.indexOf(s2.get(0));
		
		if((index + s2.size() > s1.size()) || index == -1) // Sequenz ist zu lang, um noch zu passen
			return false;
		
		for(int i= index; i < s2.size(); i++)
		{
			bool = s1.get(index+i).equals(s2.get(i));
			if(!bool)
			{
				return false;
			}
		}
		
		return true;
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
