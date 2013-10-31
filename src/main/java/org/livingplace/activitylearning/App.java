package org.livingplace.activitylearning;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.livingplace.activitylearning.activity.*;
import org.livingplace.activitylearning.activity.converter.*;
import org.livingplace.activitylearning.data.*;
import org.livingplace.activitylearning.data.IData;
import org.livingplace.activitylearning.gui.GUI;
import org.livingplace.activitylearning.pattern.Pattern;
import org.livingplace.activitylearning.pattern.PatternCluster;
import org.livingplace.activitylearning.pattern.Sequence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Mit dieder Klasse wird die Aktivitätetenentdeckung gesteuert.
 * 
 * @author Andreas Basener
 */
public class App {
	
	public static void main(String[] args) {
		
//		GUI gui = new GUI();
		
		GsonBuilder gbuilder = new GsonBuilder();
		gbuilder.registerTypeAdapter(Activity.class, new ActivityConverter());
		gbuilder.registerTypeAdapter(AlarmData.class, new AlarmConverter());
		gbuilder.registerTypeAdapter(BedData.class, new BedConverter());
		gbuilder.registerTypeAdapter(CouchData.class, new CouchConverter());
		gbuilder.registerTypeAdapter(DoorData.class, new DoorConverter());
		gbuilder.registerTypeAdapter(PositionData.class, new PositionConverter());
		gbuilder.registerTypeAdapter(PowerData.class, new PowerConverter());
		gbuilder.registerTypeAdapter(StorageData.class, new StorageConverter());
		gbuilder.registerTypeAdapter(WaterData.class, new WaterConverter());
		Gson gson = gbuilder.create();
		
		List<KDD> kddlist5pro = new ArrayList<KDD>();
		kddlist5pro.add(new KDD("data\\output_woche_005prozent_01.csv"));
		kddlist5pro.add(new KDD("data\\output_woche_005prozent_02.csv"));
		kddlist5pro.add(new KDD("data\\output_woche_005prozent_03.csv"));
		kddlist5pro.add(new KDD("data\\output_woche_005prozent_04.csv"));
		kddlist5pro.add(new KDD("data\\output_woche_005prozent_05.csv"));
		kddlist5pro.add(new KDD("data\\output_woche_005prozent_06.csv"));
		kddlist5pro.add(new KDD("data\\output_woche_005prozent_07.csv"));
		kddlist5pro.add(new KDD("data\\output_woche_005prozent_08.csv"));
		kddlist5pro.add(new KDD("data\\output_woche_005prozent_09.csv"));
		kddlist5pro.add(new KDD("data\\output_woche_005prozent_10.csv"));
		

		List<KDD> kddlist10pro = new ArrayList<KDD>();
		kddlist10pro.add(new KDD("data\\output_woche_010prozent_01.csv"));
		kddlist10pro.add(new KDD("data\\output_woche_010prozent_02.csv"));
		kddlist10pro.add(new KDD("data\\output_woche_010prozent_03.csv"));
		kddlist10pro.add(new KDD("data\\output_woche_010prozent_04.csv"));
		kddlist10pro.add(new KDD("data\\output_woche_010prozent_05.csv"));
		kddlist10pro.add(new KDD("data\\output_woche_010prozent_06.csv"));
		kddlist10pro.add(new KDD("data\\output_woche_010prozent_07.csv"));
		kddlist10pro.add(new KDD("data\\output_woche_010prozent_08.csv"));
		kddlist10pro.add(new KDD("data\\output_woche_010prozent_09.csv"));
		kddlist10pro.add(new KDD("data\\output_woche_010prozent_10.csv"));
//		
//
		List<KDD> kddlist20pro = new ArrayList<KDD>();
		kddlist20pro.add(new KDD("data\\output_woche_020prozent_01.csv"));
		kddlist20pro.add(new KDD("data\\output_woche_020prozent_02.csv"));
		kddlist20pro.add(new KDD("data\\output_woche_020prozent_03.csv"));
		kddlist20pro.add(new KDD("data\\output_woche_020prozent_04.csv"));
		kddlist20pro.add(new KDD("data\\output_woche_020prozent_05.csv"));
		kddlist20pro.add(new KDD("data\\output_woche_020prozent_06.csv"));
		kddlist20pro.add(new KDD("data\\output_woche_020prozent_07.csv"));
		kddlist20pro.add(new KDD("data\\output_woche_020prozent_08.csv"));
		kddlist20pro.add(new KDD("data\\output_woche_020prozent_09.csv"));
		kddlist20pro.add(new KDD("data\\output_woche_020prozent_10.csv"));
		
		List<KDD> kddlist0pro = new ArrayList<KDD>();
		KDD kdd = new KDD("data\\output_woche_000prozent_01.csv");
		kddlist0pro.add(kdd);
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
		
		
//		kdd.dokdd();

		for(KDD k: kddlist0pro)
		{
			k.dokdd();
		}
		for(KDD k: kddlist5pro)
		{
			k.dokdd();
		}
		for(KDD k: kddlist10pro)
		{
			k.dokdd();
		}
		for(KDD k: kddlist20pro)
		{
			k.dokdd();
		}

		writeResults("0Prozent_keinubi", kddlist0pro, map);
		writeResults("5Prozent_keinubi", kddlist5pro, map);
		writeResults("10Prozent_keinubi", kddlist10pro, map);
		writeResults("20Prozent_keinubi", kddlist20pro, map);
		
//		gui.setPatternClusterList(kdd.getClusterList());
//
//		List<Double> acu = new ArrayList<Double>();
//		
//		for(String s: map.keySet())
//		{
//			List<IData> l = map.get(s);
//			
//			PatternCluster bestpc = null;
//			double bestmatch = Double.MAX_VALUE;
//			if(l.size()>0)
//			{
//				for(PatternCluster pc: kdd.getClusterList())
//				{
//					double match = pc.bestMatch(l);
//					if(match < bestmatch)
//					{
//						bestmatch = match;
//						bestpc = pc;
//					}
//				}
//				System.out.println("-------------------------------------------------------------------");
//				Pattern p = new Pattern(new Sequence(l, 0, null), 0);
//				double acurracy = bestmatch / l.size();
//				acu.add(acurracy);
//				System.out.println(s + " best Cluster: " + bestpc.getClusterNumber() + " match: " + bestmatch + " size: " + l.size() + " acrruacy: " + acurracy*100 + "% " + bestpc.containsPatternSequence(p));
//	
//	//			System.out.println(bestpc);
//	//			for(IData d: l)
//	//			{
//	//				System.out.println(d);
//	//			}
//			}
//			else
//			{
//				System.out.println(s + " entfällt");
//			}
//		}
//		double sumavg = 0;
//		for(Double d: acu)
//			sumavg += d;
//		
//		sumavg /=acu.size();
//		System.out.println("Avg Acu: " + sumavg*100);
//		
//		gui.repaint();
//		gui.saveImage();
//		System.out.println("Fertig");
	}
	
	/**
	 * Mit dieser Metode werden die Abweichungen der vorgegebene Szenarien zu den entdeckten Aktivitäten
	 * in eine Textdatei im CSV gespeichert. 
	 * @param name Bezeichnung des Datesensets
	 * @param list Liste der unterchiedlichen Versuche
	 * @param map Map der vorgegebenen Szenarien
	 */
	private static void writeResults(String name, List<KDD> list, Map<String,List<IData>> map)
	{
		try {
			FileWriter writer = new FileWriter("data\\resultoutput" + name + ".csv");
		
//			writer.write(name + "\n");
			
			for(String s: map.keySet())
			{
				writer.write(s + ";");
	//			List<Double> acu = new ArrayList<Double>();
				for(KDD kdd: list)
				{
					List<IData> l = map.get(s);
					
					PatternCluster bestpc = null;
					double bestmatch = Double.MAX_VALUE;
					if(l.size()>0)
					{
						for(PatternCluster pc: kdd.getClusterList())
						{
							double match = pc.bestMatch(l);
							if(match < bestmatch)
							{
								bestmatch = match;
								bestpc = pc;
							}
						}
						Pattern p = new Pattern(new Sequence(l, 0, null), 0);
						double acurracy = bestmatch / l.size();
	//					acu.add(acurracy);
						if(bestpc.containsPatternSequence(p))
						{
							writer.write("0;");
						}
						else
						{
							writer.write(String.format("%.3f", acurracy) +";");
						}
//						System.out.println(s + " best Cluster: " + bestpc.getClusterNumber() + " match: " + bestmatch + " size: " + l.size() + " acrruacy: " + acurracy*100 + "% " + bestpc.containsPatternSequence(p));
			
			//			System.out.println(bestpc);
			//			for(IData d: l)
			//			{
			//				System.out.println(d);
			//			}
					}
					else
					{
						writer.write("entfällt");
						break;
					}
				}
				writer.write("\n");
				
			}

			writer.write("Cluster Anzahl;");
			for(KDD kdd: list)
			{
				writer.write(kdd.getClusterList().size() + ";");
			}
			
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
