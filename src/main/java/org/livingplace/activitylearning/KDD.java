package org.livingplace.activitylearning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.livingplace.activitylearning.data.PositionData;

public class KDD {
	
	private String filename;
	
	private List<PositionData> positionList;
	
	private List<Pattern> patternList;
	
	private List<Pattern> bestPattern;
	
	private List<Pattern> discoveredPattern;
	
	private List<PatternCluster> clusterList;
	
	private SlidingWindow slidingWindow;
	
	public KDD()
	{
		this.bestPattern = new ArrayList<Pattern>();
		this.clusterList = new ArrayList<PatternCluster>();
		this.discoveredPattern = new ArrayList<Pattern>();
	}
	
	public KDD(String filename)
	{
		this();
		this.filename = filename;
		this.positionList = new ArrayList<PositionData>();
		
		parseFile();
		
	}
	public KDD(List<PositionData> data)
	{
		this();
		this.positionList = data;
	}
	
	public void dokdd()
	{
		boolean compressed = false;
//		for(PositionData d : positionList)
//			System.out.println(d);
		do
		{
			discoverPatterns();
			
			Collections.sort(patternList);
			
			Pattern bp = patternList.get(0);
			
//			System.out.println("Bestes Pattern: " + bp);
			
			bestPattern.add(bp);
			
			markEvents(bp);
			
			compressed = compressPattern();
			
//			compressed = false;
		} while(compressed);

//		for(Pattern p: discoveredPattern)
//			System.out.println(p);
		clusterPattern();
		
		compressCluster();
	}
	
	private void discoverPatterns()
	{
		int index = 0;
		boolean done = false;
		
		List<Pattern> childList;
		List<Pattern> localDiscoverdPattern = new ArrayList<Pattern>();
		List<Pattern> initalPattern = new ArrayList<Pattern>();
		patternList = new ArrayList<Pattern>();
		
		// Initiale Pattern finden
		for(PositionData d : positionList)
		{
			if(!d.getClass().equals(Copy.PREDEFINED))
			{
				Sequence seq = new Sequence(d, index, positionList);
				boolean containsSequence = false;
				for(Pattern p: initalPattern)
				{
					containsSequence = p.containsSequence(seq);
					if(containsSequence)
					{
						p.increasePatternCount(index);
						break;
					}
				}
				if(!containsSequence)
				{
					initalPattern.add(new Pattern(seq,index));
				}
				index++;
			}
		}
		for(Pattern p: initalPattern)
		{
			if(p.getPatternCount() > 1)
				patternList.add(p);
		}
//		System.out.println(patternList.size() + " initiale Pattern gefunden");
		
		//Initiale Pattern evaluieren
		for(Pattern p : patternList)
		{
			p.evaluate(positionList.size());
		}
		
		List<Pattern> parentList = patternList;
		while(!done)
		{
			childList = new ArrayList<Pattern>();
//			System.out.println("Parentpattern " + parentList.size());
			
			List<Pattern> extendedList = new ArrayList<Pattern>();
			for(int i=0; i < parentList.size(); i++)
			{
				Pattern parentPattern = parentList.get(i);
//				System.out.println(parentPattern);
				Pattern ep = new Pattern(parentPattern);
				boolean extended = ep.extend();
//				System.out.println(ep);
				if(extended)
				{
					extendedList.add(ep);
				}
				if(!localDiscoverdPattern.contains(parentPattern))
					localDiscoverdPattern.add(parentPattern);
			}
			for(Pattern p: extendedList)
			{
//				System.out.println("ex: " +p);
				for(int i = 0; i < (positionList.size() - p.getSequence().getSequence().size() + 1); i++)
				{
					List<PositionData> slist = new ArrayList<PositionData>();
					for(int j = 0; j < p.getSequence().getSequence().size();j++)
					{
						slist.add(positionList.get(i+j));
					}
					Sequence s = new Sequence(slist, i, positionList);
					if(p.containsSequence(s))
						p.increasePatternCount(i);
				}
			}
			for(int i=0; i< extendedList.size(); i++)
			{
				Pattern p = extendedList.get(i);
				p.evaluate(positionList.size());
//				System.out.printlng("Muster: " + i + " " + p);
				if(p.getPatternCount() > 1)
				{
					childList.add(p);
				}
			}
			
			if(childList.size() == 0)
			{
				done = true;
			}
			else
			{
				parentList = childList;
			}
		}
		patternList = localDiscoverdPattern;
//		System.out.println("Entdeckte Pattern: " + localDiscoverdPattern.size());
//		for (Pattern p : localDiscoverdPattern)
//		{
//			System.out.println(p);
//		}
		discoveredPattern.addAll(localDiscoverdPattern);
	}
	
	private int markEvents(Pattern pattern)
	{
		int duplicate = 0, mark = 0, numnewevents = positionList.size();
		boolean isNew = true;
		
		for(PositionData p: positionList)
		{
			if(!p.getClass().equals(Copy.PREDEFINED))
				p.setCopy(Copy.TRUE);
		}
		
		for(Integer integer: pattern.getInstances())
		{
			pattern.setUsed(true);
			isNew = true;
//			System.out.println(p);
			for(int i = 0; i < pattern.getSequence().getSequence().size(); i++)
			{
//				PositionData pos = p.getSequence().getSequence().get(i);
				PositionData event = positionList.get(integer + i);
//				System.out.println(event);
				
				if(!event.getCopy().equals(Copy.TRUE))
				{
					duplicate++;
					pattern.setUsed(false);
				}
				else
				{
					if(isNew)
					{
						event.setCopy(Copy.NEW);
						isNew = false;
					}
					else if (event.getCopy().equals(Copy.PREDEFINED))
					{
						
					}
					else
					{
						event.setCopy(Copy.FALSE);
						mark++;
						numnewevents--;
					}
				}
			}
		}
//		System.out.println("Duplicate: " + duplicate + " marked: " + mark);
//		for(PositionData p: positionList)
//		{
//			System.out.println(p);
//		}
		
		return numnewevents;
	}
	
	public boolean compressPattern()
	{
		List<PositionData> newList = new ArrayList<PositionData>();
		
		PositionData firstEvent = positionList.get(0);
		firstEvent.setCopy(Copy.TRUE);
		
		for(int i = 0; i < positionList.size(); i++)
		{
			Copy copy = positionList.get(i).getCopy();
			if(copy.equals(Copy.NEW))
			{
				newList.add(firstEvent);
			}
			else if(copy.equals(Copy.TRUE) || copy.equals(Copy.PREDEFINED))
			{
				newList.add(positionList.get(i));
			}
		}
		
		if(newList.size() < positionList.size())
		{
			positionList = newList;
//			System.out.println("Liste komprimiert: " + positionList.size() + " Events übrig.");
			return true;
		}
		return false;
	}

	public void clusterPattern()
	{
		for (Pattern p: discoveredPattern)
		{
			boolean contains = false;
			for(PatternCluster pc: clusterList)
			{
				contains = pc.containsPattern(p);
				if(contains)
					break;
				
				contains = pc.containsPatternSequence(p);
				if(contains)
					break;
				
				contains = pc.isSimilar(p);
				if(contains)
				{
					pc.addPattern(p);
					break;
				}
			}
			if(!contains)
			{
				PatternCluster cluster = new PatternCluster();
				cluster.addPattern(p);
				clusterList.add(cluster);
			}
		}
	}
	
	private void compressCluster()
	{
		boolean maxCompression = true;
		PatternCluster pc1 = null;
		PatternCluster pc2 = null;
		
		do
		{
			maxCompression = true;
			for(int i = 0; i < clusterList.size(); i++)
			{
				boolean bool = false;
				pc1 = clusterList.get(i);
				for(int j = 0; j < clusterList.size(); j++)
				{
					if (i != j) // Nicht ein PatternCluster mit sich selbst mergen
					{
						pc2 = clusterList.get(j);
						for(Pattern p: pc2.getPatternList())
						{
							bool = pc1.containsPatternSequence(p) || pc1.isSimilar(p);
							if(!bool)
								break;
						}
						if(!bool)
							break;
					}
					if(bool)
					{
						pc1.merge(pc2);
						clusterList.remove(j);
						maxCompression = false;
					}
				}
			}
		}
		while(!maxCompression);
	}
	
	private void parseFile()
	{
		try {
			FileReader reader = new FileReader(filename);
			BufferedReader br = new BufferedReader(reader);
			String line;
			try {
				while((line = br.readLine())!=null)
				{
					positionList.add(new PositionData(line));
//					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the slidingWindow
	 */
	public SlidingWindow getSlidingWindow() {
		return slidingWindow;
	}

	/**
	 * @param slidingWindow the slidingWindow to set
	 */
	public void setSlidingWindow(SlidingWindow slidingWindow) {
		this.slidingWindow = slidingWindow;
	}
	/**
	 * @return the positionList
	 */
	public List<PositionData> getPositionList() {
		return positionList;
	}
	/**
	 * @param positionList the positionList to set
	 */
	public void setPositionList(List<PositionData> positionList) {
		this.positionList = positionList;
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
	 * @return the clusterList
	 */
	public List<PatternCluster> getClusterList() {
		return clusterList;
	}

	/**
	 * @param clusterList the clusterList to set
	 */
	public void setClusterList(List<PatternCluster> clusterList) {
		this.clusterList = clusterList;
	}
}
