package org.livingplace.activitylearning.pattern;

import java.util.ArrayList;
import java.util.List;

import org.livingplace.activitylearning.Helper;

public class PatternCluster {
	
	private Pattern centroid;

	private List<Pattern> patternList;
	
	public PatternCluster()
	{
		this.patternList = new ArrayList<Pattern>();
	}
	
	public PatternCluster(Pattern centroid)
	{
		this();
		this.patternList.add(centroid);
		this.centroid = centroid;
	}
	
	public void addPattern(Pattern pattern)
	{
		this.patternList.add(pattern);
		calcNewCentroid();
	}
	
	private void calcNewCentroid()
	{
		double meandist[] = new double[patternList.size()];
		int index = 0;
		for(Pattern p1: patternList)
		{
			for(Pattern p2: patternList)
			{
				meandist[index] += p1.distanceTo(p2);
			}
			meandist[index] /= patternList.size();
			
			index++;
		}
		
		double smallest = Double.MAX_VALUE;
		index = -1;
		for(int i = 0; i < meandist.length; i++)
		{
			if(meandist[i] < smallest)
			{
				smallest = meandist[i];
				index = i;
			}
		}
		if(index != -1)
		{
			centroid = patternList.get(index);
		}
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
			if(p.distanceTo(pattern) < Helper.MAX_DISTANCE)
				return true;
		}
		return false;
	}
	
	public boolean isSimilar(PatternCluster cluster)
	{
		double dist = centroid.distanceTo(cluster.getCentroid());
//		System.out.println(dist);
		if(this.centroid.distanceTo(cluster.getCentroid()) < Helper.MIN_DISTANCE_COMPRESS)
			return true;
		else
			return false;
	}
	
	public double distanceToCentroid(Pattern pattern)
	{
		return centroid.distanceTo(pattern);
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
	
	/**
	 * @return the centroid
	 */
	public Pattern getCentroid() {
		return centroid;
	}

	/**
	 * @param centroid the centroid to set
	 */
	public void setCentroid(Pattern centroid) {
		this.centroid = centroid;
	}
}
