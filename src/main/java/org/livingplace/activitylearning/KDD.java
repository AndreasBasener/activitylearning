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
	
	private void discoverPatterns()
	{
		int index = 0;
		boolean done = false;
		
		List<Pattern> childList;
		List<Pattern> discoverdPattern = new ArrayList<Pattern>();
		
		// Initiale Pattern finden
		for(PositionData d : positionList)
		{
			Sequence seq = new Sequence(d, index, positionList);
			boolean containsSequence = false;
			for(Pattern p: patternList)
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
				patternList.add(new Pattern(seq));
			}
			index++;
		}
		System.out.println(patternList.size() + " initiale Pattern gefunden");
		
		//Initiale Pattern evaluieren
		for(Pattern p : patternList)
		{
			p.evaluate(positionList.size());
		}
		
		List<Pattern> parentList = patternList;
		while(!done)
		{
			childList = new ArrayList<Pattern>();
			for (Pattern p : parentList)
			{
				List<Pattern> extendedList = new ArrayList<Pattern>(parentList);
//				for(Pattern ep : extendedList)
				for(int i = 0; i < extendedList.size(); i++)
				{
					Pattern ep = extendedList.get(i);
					ep.extend();
					ep.evaluate(positionList.size());
					
					if ((ep.getSequenceList().size() == 1) || (ep.getValue() < p.getValue()))
					{
						extendedList.remove(p);
					}
					else
					{
						if(!childList.contains(ep))
						{
							childList.add(ep);
						}
					}
				}
				discoverdPattern.add(p);
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
