package org.livingplace.activitylearning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.livingplace.activitylearning.data.*;
import org.livingplace.activitylearning.pattern.Pattern;
import org.livingplace.activitylearning.pattern.PatternCluster;
import org.livingplace.activitylearning.pattern.Sequence;
import org.livingplace.activitylearning.pattern.Pattern.OrderType;

public class KDD {
	
//	private String filename;
	
//	private List<Point3D> dataPoints;
	
	/**
	 * Liste der Sensordaten für die Aktivitätenerkennung.
	 * evtl. noch in eventList umbenennen
	 */
	//TODO: umbenennen
	private List<IData> dataList;
	
	private List<Pattern> patternList;
	
	private List<Pattern> bestPattern;
	
	private List<Pattern> discoveredPattern;
	
	private List<PatternCluster> clusterList;
	
	public KDD()
	{
		this.bestPattern = new ArrayList<Pattern>();
		this.clusterList = new ArrayList<PatternCluster>();
		this.discoveredPattern = new ArrayList<Pattern>();
		this.dataList = new ArrayList<IData>();
//		this.dataPoints = new ArrayList<Point3D>();
	}
	
	public KDD(String filename)
	{
		this();
//		this.filename = filename;
		
//		parseFile();
		dataList = readFile(filename);
		System.out.println(dataList.size() + " Events eingelesen");
	}
	
	public void dokdd()
	{
		boolean compressed = false;
		int numCycles = 0;
		do
		{
			discoverPatterns();

			Pattern.setOrdertype(OrderType.VALUE);
			Collections.sort(patternList);
			Pattern bp = patternList.get(0);
			
//			System.out.println("Bestes Pattern: " + bp);
			if(!bestPattern.contains(bp))
			{
				bestPattern.add(bp);
			}
			markEvents(bp);
			
			compressed = compressPattern(bp);
			
			numCycles++;
//			compressed = true;
		} while(!compressed && numCycles > 1);
		
		System.out.println(discoveredPattern.size() + " Pattern entdeckt");
		System.out.println(bestPattern.size() + " beste Pattern entdeckt");

//		for(Pattern p: discoveredPattern)
//			System.out.println(p);
		
//		System.out.println(numCycles);
//		removeDuplicates();
		clusterPattern();
		
		System.out.println(clusterList.size() + " Cluster erzeugt");
		
//		for(PatternCluster pc : clusterList)
//			if(pc.getPatternList().size() > 0)
//				System.out.println(pc);
		
//		compressCluster();
		

//		for(PatternCluster pc : clusterList)
//			if(pc.getPatternList().size() > 0)
//				System.out.println(pc);
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
		for(IData d: dataList)
		{
			if(!d.getClass().equals(Copy.PREDEFINED))
			{
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
//				else
//					System.out.println("gibt es schon");
			}
			for(Pattern p: extendedList)
			{
//				System.out.println("ex: " +p);
				for(int i = 0; i < (dataList.size() - p.getSequence().getDataSequence().size() + 1); i++)
				{
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
				p.evaluate(dataList.size()); //Evtl evaluate mit in die if-Abfrage
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
		for(Pattern p: localDiscoverdPattern)
		{
			if(!discoveredPattern.contains(p))
				discoveredPattern.add(p);
		}
//		discoveredPattern.addAll(localDiscoverdPattern);
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
	
	public boolean compressPattern(Pattern pattern)
	{
		List<IData> newList = new ArrayList<IData>();
		
		IData firstEvent = pattern.getSequence().getDataSequence().get(0);
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
//		System.out.println("alt: " + dataList.size() + "neu: " +newList.size());
		if(newList.size() < dataList.size())
		{
			dataList = newList;
			return false;
		}
		return true;
	}

	public void clusterPattern()
	{
		
		List<Pattern> workList = new ArrayList<Pattern>();
		
		for(Pattern p1: discoveredPattern)
		{
			if(p1.getSequence().getDataSequence().size() > 2 /*&& p1.numberOfTypes() > 1*/)
				workList.add(p1);
		}
		Pattern.setOrdertype(OrderType.SIZE);
		Collections.sort(workList);
		System.out.println(workList.size() + " Pattern zum clustern übrig");
		for (Pattern p: workList)
		{
			boolean contains = false;
			boolean createnew = true;
			double[] distanceToPatternCluster = new double[clusterList.size()];
			int index = 0;
			for(PatternCluster pc: clusterList)
			{
				contains = pc.containsPattern(p);
				if(contains)
					break;
				
				contains = pc.containsPatternSequence(p);
				if(contains)
				{
					pc.addPattern(p);
					break;
				}
				
				double sim = pc.distanceToCentroid(p);
				distanceToPatternCluster[index] = sim;
				index++;
			}
			if(contains)
				continue;
			
			int mindistindex = Integer.MAX_VALUE;
			double mindist = Double.MAX_VALUE;
			index = 0;
			for(double d: distanceToPatternCluster)
			{
				if(d < mindist)
				{
					mindist = d;
					mindistindex = index;
				}
				index++;
			}
			
			if(mindist <= Helper.MIN_SIMILAR_CLUSTER && index < Integer.MAX_VALUE)
			{
				clusterList.get(mindistindex).addPattern(p);
				createnew = false;
			}

			if(createnew)
			{
				PatternCluster cluster = new PatternCluster(p);
//				cluster.addPattern(p);
				clusterList.add(cluster);
			}
		}
	}
	
	private void compressCluster()
	{
		boolean maxCompression = true;
		PatternCluster pc1 = null;
		PatternCluster pc2 = null;
		
		List<PatternCluster> clist = new ArrayList<PatternCluster>();
		
		System.out.println("Starte Clusterkomprimierung. " + clusterList.size() + " werden komprimiert");
		
		for(PatternCluster cluster1: clusterList)
		{
			if(clist.size() == 0)
			{
				clist.add(cluster1);
			}
			else
			{
				boolean merge = false;
				for(PatternCluster cluster2: clist)
				{
					if(cluster1 != cluster2) //nicht versuchen ein Cluster mit sich selbst zu mergen
					{
						merge = cluster1.isSimilar(cluster2);
						if(merge)
						{
							cluster2.merge(cluster1);
							break;
						}
					}
				}
				if(!merge)
				{
					clist.add(cluster1);
				}
			}
		}
		clusterList = clist;
//		do
//		{
//			maxCompression = true;
//			for(int i = 0; i < clusterList.size(); i++)
//			{
//				boolean bool = false;
//				pc1 = clusterList.get(i);
//				for(int j = 0; j < clusterList.size(); j++)
//				{
//					if (i != j) // Nicht ein PatternCluster mit sich selbst mergen
//					{
//						pc2 = clusterList.get(j);
//						for(Pattern p: pc2.getPatternList())
//						{
//							bool = pc1.containsPatternSequence(p) || pc1.isSimilar(p);
////							bool = pc1.containsPatternSequence(p) || pc1.getCentroid().distanceTo(p) <= Helper.MAX_DISTANCE;
//							if(!bool)
//								break;
//						}
//						if(!bool)
//							break;
//					}
//					if(bool)
//					{
//						pc1.merge(pc2);
//						clusterList.remove(j);
//						maxCompression = false;
//					}
//				}
//			}
//		}
//		while(!maxCompression);
		System.out.println("Cluster fertig komprimiert. " + clist.size() + " Cluster übrig");
	}
	
	public List<IData> readFile(String filename)
	{
		if(filename == null || filename.equals(""))
			return null;
		
		List<IData> sequence = new ArrayList<IData>();
		
		try {
			FileReader reader = new FileReader(filename);
			BufferedReader br = new BufferedReader(reader);
			String line;
			try {
				while((line = br.readLine())!=null)
				{
					String[] strarr = line.split(";", 2);
					if(strarr.length == 2)
					{
						String str = strarr[0];
						String data = strarr[1];
						if(str.equals("Alarm"))
							sequence.add(new AlarmData(data));
						else if(str.equals("Bed"))
							sequence.add(new BedData(data));
						else if(str.equals("Blinds"))
							sequence.add(new BlindsData(data));
						else if(str.equals("Couch"))
							sequence.add(new CouchData(data));
						else if(str.equals("DoorBell"))
							sequence.add(new DoorBellData(data));
						else if(str.equals("Door"))
							sequence.add(new DoorData(data));
						else if(str.equals("Ubisense"))
						{
							PositionData pd = new PositionData(data);
							sequence.add(pd);
//							addDataPoint(new Point3D(pd.getX(), pd.getY(), 0));
						}
						else if(str.equals("Power"))
							sequence.add(new PowerData(data));
						else if(str.equals("Storage"))
							sequence.add(new StorageData(data));
						else if(str.equals("Water"))
							sequence.add(new WaterData(data));
						else if(str.equals("Window"))
							sequence.add(new WindowData(data));
					}
//					dataList.add(new PositionData(line));
//					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
//		System.out.println(filename);
//		for(IData d: sequence)
//		{
//			System.out.println(d);
//		}
		
		return sequence;
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

	/**
	 * @return the dataPoints
	 */
//	public List<Point3D> getDataPoints() {
//		return dataPoints;
//	}

	/**
	 * @param dataPoints the dataPoints to set
	 */
//	public void setDataPoints(List<Point3D> dataPoints) {
//		this.dataPoints = dataPoints;
//	}
	
//	public void addDataPoint(Point3D point)
//	{
//		this.dataPoints.add(point);
//	}
}
