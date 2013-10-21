package org.livingplace.activitylearning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.livingplace.activitylearning.data.IData;
import org.livingplace.activitylearning.gui.GUI;
import org.livingplace.activitylearning.pattern.Pattern;
import org.livingplace.activitylearning.pattern.PatternCluster;
import org.livingplace.activitylearning.pattern.Sequence;


/**
 * Hello world!
 * 
 * @author Andreas Basener
 */
public class App {
	
	public static void main(String[] args) {
		
		GUI gui = new GUI();
		
		KDD kdd = new KDD("data\\output_woche_000prozent_01.csv");
//		KDD kdd = new KDD("C:\\workspacejuno\\scriptsimulator\\output.csv");
		
		Map<String,List<IData>> map = new HashMap<String, List<IData>>();
		
		map.put("abwaschen",kdd.readFile("data\\activities\\abwaschen.csv"));
		map.put("ankleiden",kdd.readFile("data\\activities\\ankleiden.csv"));
		map.put("arbeiten",kdd.readFile("data\\activities\\arbeiten.csv"));
		map.put("duschen",kdd.readFile("data\\activities\\duschen.csv"));
		map.put("essen",kdd.readFile("data\\activities\\essen.csv"));
		map.put("fernsehen",kdd.readFile("data\\activities\\fernsehen.csv"));
		map.put("hinsetzen",kdd.readFile("data\\activities\\hinsetzen.csv"));
		map.put("kaffekochen",kdd.readFile("data\\activities\\kaffekochen.csv"));
		map.put("schlafenabends",kdd.readFile("data\\activities\\schlafenabends.csv"));
		map.put("schlafenmorgends",kdd.readFile("data\\activities\\schlafenmorgends.csv"));
		map.put("toilette",kdd.readFile("data\\activities\\toilette.csv"));
		map.put("touchtisch",kdd.readFile("data\\activities\\touchtisch.csv"));
		
		
		kdd.dokdd();
		
		gui.setPatternClusterList(kdd.getClusterList());

		List<Double> acu = new ArrayList<Double>();
		
		for(String s: map.keySet())
		{
			List<IData> l = map.get(s);
			
			PatternCluster bestpc = null;
			double bestmatch = Double.MAX_VALUE;
			
			for(PatternCluster pc: kdd.getClusterList())
			{
				double match = pc.bestMatch(l);
				if(match < bestmatch)
				{
					bestmatch = match;
					bestpc = pc;
				}
			}
			System.out.println("-------------------------------------------------------------------");
			Pattern p = new Pattern(new Sequence(l, 0, null), 0);
			double acurracy = bestmatch / l.size();
			acu.add(acurracy);
			System.out.println(s + " best Cluster: " + bestpc.getClusterNumber() + " match: " + bestmatch + " size: " + l.size() + " acrruacy: " + acurracy*100 + "% " + bestpc.containsPatternSequence(p));

//			System.out.println(bestpc);
//			for(IData d: l)
//			{
//				System.out.println(d);
//			}
		}
		double sumavg = 0;
		for(Double d: acu)
			sumavg += d;
		
		sumavg /=acu.size();
		System.out.println("Avg Acu: " + sumavg*100);
		
		gui.repaint();
		gui.saveImage();
//		System.out.println("Fertig");
	}
	
}
