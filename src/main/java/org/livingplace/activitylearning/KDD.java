package org.livingplace.activitylearning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.livingplace.activitylearning.data.PositionData;

public class KDD {
	
	private String filename;
	
	private List<PositionData> positionList;
	
	private List<Pattern> patternList;
	
	private SlidingWindow slidingWindow;
	
	public KDD(String filename)
	{
		this.filename = filename;
		this.positionList = new ArrayList<PositionData>();
		this.patternList = new ArrayList<Pattern>();
		
		parseFile();
		
//		this.slidingWindow = new SlidingWindow(3, positionList);
		
		discoverPatterns();
		
	}
	public KDD(List<PositionData> data)
	{
		this.positionList = data;
		this.patternList = new ArrayList<Pattern>();
		
		discoverPatterns();
	}
	
	private void discoverPatterns()
	{
		int index = 0;
		boolean done = false;
		
		List<Pattern> childList;
		List<Pattern> discoverdPattern = new ArrayList<Pattern>();
		List<Pattern> initalPattern = new ArrayList<Pattern>();
		
		
		// Initiale Pattern finden
		for(PositionData d : positionList)
		{
			Sequence seq = new Sequence(d, index, positionList);
			boolean containsSequence = false;
			for(Pattern p: initalPattern)
			{
				containsSequence = p.containsSequence(seq);
				if(containsSequence)
				{
					p.increasePatternCount();
					break;
				}
			}
			if(!containsSequence)
			{
				initalPattern.add(new Pattern(seq));
			}
			index++;
		}
		for(Pattern p: initalPattern)
		{
			if(p.getPatternCount() > 1)
				patternList.add(p);
		}
		System.out.println(patternList.size() + " initiale Pattern gefunden");
		
		//Initiale Pattern evaluieren
		for(Pattern p : patternList)
		{
			p.evaluate(positionList.size());
		}
		
//		Pattern pat = patternList.get(0);
//		System.out.println(pat);
//		boolean ext = pat.extend();
//		pat.evaluate(positionList.size());
//		System.out.println(pat);
//		System.out.println(ext);
		
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
				if(extended)
				{
					extendedList.add(ep);
				}
				if(!discoverdPattern.contains(parentPattern))
					discoverdPattern.add(parentPattern);
			}
			for(Pattern p: extendedList)
			{
				for(int i = 0; i < (positionList.size() - p.getSequence().getSequence().size() + 1); i++)
				{
					List<PositionData> slist = new ArrayList<PositionData>();
					for(int j = 0; j < p.getSequence().getSequence().size();j++)
					{
						slist.add(positionList.get(i+j));
					}
					Sequence s = new Sequence(slist, i, positionList);
					if(p.containsSequence(s))
						p.increasePatternCount();
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
		System.out.println("Entdeckte Pattern: " + discoverdPattern.size());
		for (Pattern p : discoverdPattern)
		{
			System.out.println(p);
		}
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
}
