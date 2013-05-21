package org.livingplace.activitylearning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.livingplace.activitylearning.data.IData;
import org.livingplace.activitylearning.data.PositionData;

public class KDD {
	
	private String filename;
	
	private List<IData> dataList;
	
	@Deprecated
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
		this.dataList = new ArrayList<IData>();
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

		do
		{
			discoverPatterns();
			
			Collections.sort(patternList);
			
			Pattern bp = patternList.get(0);
			
//			System.out.println("Bestes Pattern: " + bp);
			
			bestPattern.add(bp);
			
			markEvents(bp);
			
			compressed = compressPattern();
			
//			compressed = true;
		} while(!compressed);

//		for(Pattern p: discoveredPattern)
//			System.out.println(p);
		clusterPattern();
		
		compressCluster();
		
		for(PatternCluster pc : clusterList)
			System.out.println(pc);
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
//		for(PositionData d : positionList)
		for(IData d: dataList)
		{
			if(!d.getClass().equals(Copy.PREDEFINED))
			{
//				Sequence seq = new Sequence(d, index, positionList);
				Sequence seq = new Sequence(d, index, dataList);
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
//			p.evaluate(positionList.size());
			p.evaluate(dataList.size());
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
//				for(int i = 0; i < (positionList.size() - p.getSequence().getSequence().size() + 1); i++)
				for(int i = 0; i < (dataList.size() - p.getSequence().getDataSequence().size() + 1); i++)
				{
//					List<PositionData> slist = new ArrayList<PositionData>();
					List<IData> slist = new ArrayList<IData>();
					for(int j = 0; j < p.getSequence().getDataSequence().size();j++)
					{
						slist.add(dataList.get(i+j));
					}
					Sequence s = new Sequence(slist, i, dataList);
					if(p.containsSequence(s))
						p.increasePatternCount(i);
				}
			}
			for(int i=0; i< extendedList.size(); i++)
			{
				Pattern p = extendedList.get(i);
				p.evaluate(dataList.size());
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
		int numnewevents = dataList.size();
		boolean isNew = true;
		
//		for(PositionData p: positionList)
		for(IData d: dataList)
		{
			if(!d.getCopy().equals(Copy.PREDEFINED))
				d.setCopy(Copy.TRUE);
		}
		
		for(Integer integer: pattern.getInstances())
		{
			pattern.setUsed(true);
			isNew = true;
//			System.out.println(p);
			for(int i = 0; i < pattern.getSequence().getDataSequence().size(); i++)
			{
//				PositionData pos = p.getSequence().getSequence().get(i);
//				PositionData event = positionList.get(integer + i);
				IData event = dataList.get(integer + i);
//				System.out.println(event);
				
				if(!event.getCopy().equals(Copy.TRUE))
				{
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
						numnewevents--;
					}
				}
			}
		}
		
		return numnewevents;
	}
	
	public boolean compressPattern()
	{
		List<IData> newList = new ArrayList<IData>();
		
		IData firstEvent = dataList.get(0);
		firstEvent.setCopy(Copy.TRUE);
		
		for(int i = 0; i < dataList.size(); i++)
		{
			Copy copy = dataList.get(i).getCopy();
			if(copy.equals(Copy.NEW))
			{
				newList.add(firstEvent);
			}
			else if(copy.equals(Copy.TRUE) || copy.equals(Copy.PREDEFINED))
			{
				newList.add(dataList.get(i));
			}
		}
		
		if(newList.size() < dataList.size())
		{
			dataList = newList;
			return false;
		}
		return true;
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
					dataList.add(new PositionData(line));
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
