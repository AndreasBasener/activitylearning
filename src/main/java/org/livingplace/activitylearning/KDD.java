package org.livingplace.activitylearning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.livingplace.activitylearning.activity.Activity;
import org.livingplace.activitylearning.event.*;
import org.livingplace.activitylearning.pattern.Pattern;
import org.livingplace.activitylearning.pattern.Cluster;
import org.livingplace.activitylearning.pattern.Sequence;
import org.livingplace.activitylearning.pattern.Pattern.OrderType;

/**
 * Mit dieser Klasse wird die Aktivitätenentdeckung durchgeführt.
 * @author Andreas Basener
 *
 */
public class KDD {
	
//	private String filename;
	
//	private List<Point3D> dataPoints;
	
	/**
	 * Liste der Sensordaten für die Aktivitätenerkennung.
	 */
	private List<IEvent> eventList;
	
	/**
	 * Liste der Pattern
	 */
	private List<Pattern> patternList;
	
	/**
	 * Liste der besten Pattern
	 */
	private List<Pattern> bestPattern;
	
	/**
	 * Liste aller entdeckten Pattern
	 */
	private List<Pattern> discoveredPattern;
	
	/**
	 * Liste der generierten Cluster
	 */
	private List<Cluster> clusterList;
	
	/**
	 * Liste der endgültigen activities;
	 */
	private List<Activity> activities;
	
	/**
	 * Erzeugt eine neue KDD Instanz mit leeren Listen
	 */
	public KDD()
	{
		this.bestPattern = new ArrayList<Pattern>();
		this.clusterList = new ArrayList<Cluster>();
		this.discoveredPattern = new ArrayList<Pattern>();
		this.eventList = new ArrayList<IEvent>();
//		this.dataPoints = new ArrayList<Point3D>();
	}
	
	/**
	 * Erstellt eine neue KDD Instanz und füllt die Eventliste mit den Sensordaten, die in der
	 * Datai unter filename enthalten sind. 
	 * @param filename Dateiname der Ausgangsdatei
	 */
	public KDD(String filename)
	{
		this();
//		this.filename = filename;
		
//		parseFile();
		eventList = readFile(filename);
		System.out.println(eventList.size() + " Events eingelesen");
	}
	
	/**
	 * Mit dieser Methode wird die Aktivitätenentdeckung ausgeführt.
	 */
	public void dokdd()
	{
		boolean compressed = false;
		int numCycles = 0;
		do //Pattern entdecken
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
		//Pattern clustern
		clusterPattern();
		
		System.out.println(clusterList.size() + " Cluster erzeugt");
		
//		for(PatternCluster pc : clusterList)
//			if(pc.getPatternList().size() > 0)
//				System.out.println(pc);
		
//		compressCluster();
		

//		for(PatternCluster pc : clusterList)
//			if(pc.getPatternList().size() > 0)
//				System.out.println(pc);
		
		generateActivities();
		System.out.println("Aktivitäten erzeugt: " + activities.size());
	}
	
	/**
	 * Mit dieser Methode werden die Pattern für die Clusterbildung entdeckt.
	 */
	private void discoverPatterns()
	{
		int index = 0;
		boolean done = false;

		//Liste der in der jeweiligen Iteration entdeckten Pattern
		List<Pattern> childList;
		//Liste der innerhalb dieser Methode entdeckten Pattern
		List<Pattern> localDiscoverdPattern = new ArrayList<Pattern>();
		//Liste der intialen Pattern mit länge 1
		List<Pattern> initalPattern = new ArrayList<Pattern>();
		patternList = new ArrayList<Pattern>();
		
		// Initiale Pattern finden
		for(IEvent d: eventList)
		{
			if(!d.getClass().equals(Copy.PREDEFINED)) //Ist das Event bereits vordefiniert worden?
			{
				Sequence seq = new Sequence(d, index, eventList); //Sequenz mit Länge 1 erstellen
				boolean containsSequence = false;
				for(Pattern p: initalPattern)//Ist die Sequenz bereits enteckt worden?
				{
					containsSequence = p.containsSequence(seq);
					if(containsSequence)
					{
						//Bei sich wiederholenden Events den Counter erhöhen
						p.increasePatternCount(index);
						break;
					}
				}
				if(!containsSequence)
				{
					//Neue Sequenz in Liste speichern
					initalPattern.add(new Pattern(seq,index));
				}
				index++;
			}
		}
		//Nur Pattern mit einer Minddeshäufigkeit von 1 weiter verwenden
		for(Pattern p: initalPattern)
		{
			if(p.getPatternCount() > 1)
				patternList.add(p);
		}
//		System.out.println(patternList.size() + " initiale Pattern gefunden");
		
		//Initiale Pattern evaluieren
		for(Pattern p : patternList)
		{
			p.evaluate(eventList.size());
		}
		
		List<Pattern> parentList = patternList;
		//Inklemetell die initialen Pattern erweitern und die so entdeckten Muster speichern.
		//Solange wiederholen, bis die Pattern nicht mehr erweitert werden können. 
		while(!done)
		{
			//Liste der erweiterten Pattern
			List<Pattern> extendedList = new ArrayList<Pattern>();
			childList = new ArrayList<Pattern>();
			
			//Pattern aus der parentList erweitern und in der Liste extendedList speichern
			for(int i=0; i < parentList.size(); i++)
			{
				Pattern parentPattern = parentList.get(i);
				Pattern ep = new Pattern(parentPattern);
				boolean extended = ep.extend();
				if(extended)
				{
					extendedList.add(ep);
				}
				//nur neue Pattern hinzufügen
				if(!localDiscoverdPattern.contains(parentPattern))
					localDiscoverdPattern.add(parentPattern);
			}
			//für die erweiterten Pattern herausfinden, wie oft sie in der Eventliste vorkommen
			for(Pattern p: extendedList)
			{
				for(int i = 0; i < (eventList.size() - p.getSequence().getDataSequence().size() + 1); i++)
				{
					List<IEvent> slist = new ArrayList<IEvent>();
					for(int j = 0; j < p.getSequence().getDataSequence().size();j++)
					{
						slist.add(eventList.get(i+j));
					}
					Sequence s = new Sequence(slist, i, eventList);
					if(p.containsSequence(s))
						p.increasePatternCount(i);
				}
			}
			//erwiterte Pattern, die sich mindestens 2 Mal wiederholen, evaluieren und in die
			//childList speichern
			for(int i=0; i< extendedList.size(); i++)
			{
				Pattern p = extendedList.get(i);
				p.evaluate(eventList.size()); //Evtl evaluate mit in die if-Abfrage
				if(p.getPatternCount() > 1)
				{
					childList.add(p);
				}
			}
			//kommt kein erweitertes Pattern häufig genug vor, dann abbrechen
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

		for(Pattern p: localDiscoverdPattern)
		{
			if(!discoveredPattern.contains(p))
				discoveredPattern.add(p);
		}
	}
	
	/**
	 * Pattern in der Eventliste markieren
	 * @param pattern das zu markierende Pattern
	 * @return Anzahl der Events, die nach der markierung übrigbleiben
	 */
	private int markEvents(Pattern pattern)
	{
		int numnewevents = eventList.size();
		boolean isNew = true;
		
//		for(PositionData p: positionList)
		for(IEvent d: eventList)
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
				IEvent event = eventList.get(integer + i);
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
	
	/**
	 * Pattern in der Eventliste komprimieren.
	 * @param pattern das zu komprimierende Pattern
	 * @return wurde komprimiert oder nicht
	 */
	public boolean compressPattern(Pattern pattern)
	{
		List<IEvent> newList = new ArrayList<IEvent>();
		
		IEvent firstEvent = pattern.getSequence().getDataSequence().get(0);
		firstEvent.setCopy(Copy.TRUE);
		
		for(int i = 0; i < eventList.size(); i++)
		{
			Copy copy = eventList.get(i).getCopy();
			if(copy.equals(Copy.NEW))
			{
				newList.add(firstEvent);
			}
			else if(copy.equals(Copy.TRUE) || copy.equals(Copy.PREDEFINED))
			{
				newList.add(eventList.get(i));
			}
		}
//		System.out.println("alt: " + dataList.size() + "neu: " +newList.size());
		if(newList.size() < eventList.size())
		{
			eventList = newList;
			return false;
		}
		return true;
	}

	/**
	 * Clusterbildung der entdeckten Pattern
	 */
	public void clusterPattern()
	{
		//Hilfsliste für die entdeckten Pattern zur Clusterbildung
		List<Pattern> workList = new ArrayList<Pattern>();
		
		//Nur Pattern für die Clusterbildung verwenden, die mindestens die Länge 3 Mal haben
		for(Pattern p1: discoveredPattern)
		{
			if(p1.getSequence().getDataSequence().size() > 2 /*&& p1.numberOfTypes() > 1*/)
				workList.add(p1);
		}
		
//		Pattern.setOrdertype(OrderType.SIZE);
//		Collections.sort(workList);
		
		System.out.println(workList.size() + " Pattern zum clustern übrig");
		//Clusterbildung
		for (Pattern p: workList)
		{
			boolean contains = false;
			boolean createnew = true;
			//Distanzen des aktuellen Pattern zu allen Clusterzentren
			double[] distanceToPatternCluster = new double[clusterList.size()];
			int index = 0;
			for(Cluster pc: clusterList)
			{
				//Ist das Pattern im Cluster bereits enthalten?
				contains = pc.containsPattern(p);
				if(contains)
					break;
				
				//Ist die Patternsequenz in einem der Pattern des Cluster komplett enthalten?
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
			//Ist das Pattern bereits enthalten, mit dem nächsten Pattern fortfahren.
			if(contains)
				continue;
			
			int mindistindex = Integer.MAX_VALUE;
			double mindist = Double.MAX_VALUE;
			index = 0;
			//Die kleinste Distanz zu den Clusterzentren und das dazugehörige Cluster herausfinden.
			for(double d: distanceToPatternCluster)
			{
				if(d < mindist)
				{
					mindist = d;
					mindistindex = index;
				}
				index++;
			}
			
			//Ist die Distanz zu dem Cluster klein genug?
			if(mindist <= Helper.MIN_SIMILAR_CLUSTER && index < Integer.MAX_VALUE)
			{
				clusterList.get(mindistindex).addPattern(p);
				createnew = false;
			}
			//Konnte das Pattern keinem Cluster zugeordnet werden, wird ein neues Clustermit dem 
			//Pattern erzeugt.
			if(createnew)
			{
				Cluster cluster = new Cluster(p);
				clusterList.add(cluster);
			}
		}
	}
	
	/**
	 * Mit dieser Methode können die Cluster weiter zusammengefasst werden. 
	 */
//	private void compressCluster()
//	{
//		boolean maxCompression = true;
//		PatternCluster pc1 = null;
//		PatternCluster pc2 = null;
//		
//		List<PatternCluster> clist = new ArrayList<PatternCluster>();
//		
//		System.out.println("Starte Clusterkomprimierung. " + clusterList.size() + " werden komprimiert");
//		
//		for(PatternCluster cluster1: clusterList)
//		{
//			if(clist.size() == 0)
//			{
//				clist.add(cluster1);
//			}
//			else
//			{
//				boolean merge = false;
//				for(PatternCluster cluster2: clist)
//				{
//					if(cluster1 != cluster2) //nicht versuchen ein Cluster mit sich selbst zu mergen
//					{
//						merge = cluster1.isSimilar(cluster2);
//						if(merge)
//						{
//							cluster2.merge(cluster1);
//							break;
//						}
//					}
//				}
//				if(!merge)
//				{
//					clist.add(cluster1);
//				}
//			}
//		}
//		clusterList = clist;
////		do
////		{
////			maxCompression = true;
////			for(int i = 0; i < clusterList.size(); i++)
////			{
////				boolean bool = false;
////				pc1 = clusterList.get(i);
////				for(int j = 0; j < clusterList.size(); j++)
////				{
////					if (i != j) // Nicht ein PatternCluster mit sich selbst mergen
////					{
////						pc2 = clusterList.get(j);
////						for(Pattern p: pc2.getPatternList())
////						{
////							bool = pc1.containsPatternSequence(p) || pc1.isSimilar(p);
//////							bool = pc1.containsPatternSequence(p) || pc1.getCentroid().distanceTo(p) <= Helper.MAX_DISTANCE;
////							if(!bool)
////								break;
////						}
////						if(!bool)
////							break;
////					}
////					if(bool)
////					{
////						pc1.merge(pc2);
////						clusterList.remove(j);
////						maxCompression = false;
////					}
////				}
////			}
////		}
////		while(!maxCompression);
//		System.out.println("Cluster fertig komprimiert. " + clist.size() + " Cluster übrig");
//	}
	
	/**
	 * Mit dieser Methode wird die Datei aus filename ausgelesen. Die darin enthaltenen Sensordaten werden
	 * in eine Eventliste gespeichert, die anschließend zurückgegben wird.
	 * @param filename dir zu parsende Datei
	 * @return Liste der erstellten Events
	 */
	public List<IEvent> readFile(String filename)
	{
		if(filename == null || filename.equals(""))
			return null;
		//Zielliste der Events
		List<IEvent> sequence = new ArrayList<IEvent>();
		
		try {
			FileReader reader = new FileReader(filename);
			BufferedReader br = new BufferedReader(reader);
			String line;
			try {//Zeie für Zeile auslesen 
				while((line = br.readLine())!=null)
				{
					//Die Sensordaten sind mmit einem Semikolon getrennt
					String[] strarr = line.split(";", 2);
					if(strarr.length == 2)
					{
						String str = strarr[0];
						String data = strarr[1];
						if(str.equals("Alarm"))
							sequence.add(new AlarmEvent(data));
						else if(str.equals("Bed"))
							sequence.add(new BedEvent(data));
						else if(str.equals("Blinds"))
							sequence.add(new BlindsEvent(data));
						else if(str.equals("Couch"))
							sequence.add(new CouchEvent(data));
						else if(str.equals("DoorBell"))
							sequence.add(new DoorBellEvent(data));
						else if(str.equals("Door"))
							sequence.add(new DoorEvent(data));
//						if(str.equals("Ubisense"))
//						{
//							PositionData pd = new PositionData(data);
//							sequence.add(pd);
////							addDataPoint(new Point3D(pd.getX(), pd.getY(), 0));
//						}
						else if(str.equals("Power"))
							sequence.add(new PowerEvent(data));
						else if(str.equals("Storage"))
							sequence.add(new StorageEvent(data));
						else if(str.equals("Water"))
							sequence.add(new WaterEvent(data));
						else if(str.equals("Window"))
							sequence.add(new WindowEvent(data));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return sequence;
	}
	
	/**
	 * Erzeugt aus den Clustern Aktivitäten.
	 */
	private void generateActivities()
	{
		this.activities = new ArrayList<Activity>();
		
		for(Cluster pc: clusterList)
		{
			activities.add(new Activity(pc));
		}
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
	public List<Cluster> getClusterList() {
		return clusterList;
	}

	/**
	 * @param clusterList the clusterList to set
	 */
	public void setClusterList(List<Cluster> clusterList) {
		this.clusterList = clusterList;
	}

	/**
	 * @return the activities
	 */
	public List<Activity> getActivities() {
		return activities;
	}

	/**
	 * @param activities the activities to set
	 */
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
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
