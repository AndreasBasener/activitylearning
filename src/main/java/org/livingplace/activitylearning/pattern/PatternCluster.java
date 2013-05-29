package org.livingplace.activitylearning.pattern;

import java.util.ArrayList;
import java.util.List;

import org.livingplace.activitylearning.Helper;

public class PatternCluster {

	private List<Pattern> patternList;
	
	public PatternCluster()
	{
		this.patternList = new ArrayList<Pattern>();
	}
	
	public void addPattern(Pattern pattern)
	{
		this.patternList.add(pattern);
	}

	/**
	 * @return the patternList
	 */
	public List<Pattern> getPatternList() {
		return patternList;
	}

	/**
	 * @param patternList the patternList to set
	 */
	public void setPatternList(List<Pattern> patternList) {
		this.patternList = patternList;
	}
	
	public boolean containsPattern(Pattern pattern)
	{
		return patternList.contains(pattern);
	}
	
	public boolean containsPatternSequence(Pattern pattern)
	{
		boolean bool = false;
		
		for(Pattern p: patternList)
		{
			bool = p.containsPatternSequence(pattern);
		}
		
		return bool;
	}
	
	public boolean isSimilar(Pattern pattern)
	{
		for(Pattern p: patternList)
		{
			if(p.distanceTo(pattern) > Helper.MIN_SIMILAR)
				return true;
		}
		return false;
	}
	
	public void merge(PatternCluster pc)
	{
		if (pc == null)
			return;
		
		for(Pattern p : pc.getPatternList())
		{
			if(!patternList.contains(p))
			{
				patternList.add(p);
			}
		}
	}
	
	public String toString()
	{
		String s = "Clustersize: " + patternList.size() + "\n";
		for(Pattern p: patternList)
			s += "Pattern: " + p + "\n";
		return s;
	}
	
}
