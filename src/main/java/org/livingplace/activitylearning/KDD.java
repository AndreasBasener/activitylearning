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
	
	private SlidingWindow slidingWindow;
	
	public KDD(String filename)
	{
		this.filename = filename;
		this.positionList = new ArrayList<PositionData>();
		
		parseFile();
		
		this.slidingWindow = new SlidingWindow(3, positionList);
		
	}

	public void parseFile()
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
}
