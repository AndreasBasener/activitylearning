package org.livingplace.activitylearning;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.livingplace.activitylearning.activity.*;
import org.livingplace.activitylearning.activity.converter.*;
import org.livingplace.activitylearning.event.*;
import org.livingplace.activitylearning.gui.GUI;
import org.livingplace.activitylearning.pattern.Pattern;
import org.livingplace.activitylearning.pattern.Cluster;
import org.livingplace.activitylearning.pattern.Sequence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Mit dieder Klasse wird die Aktivitätetenentdeckung gesteuert.
 * 
 * @author Andreas Basener
 */
public class App {
	
	private static Gson gson;
	
	public static void main(String[] args) {
		
		//GUI zum Anzeigen der Aktivitäten auf dem Livingplace Grundriss
		GUI gui = new GUI();
		
		//Zum serialieren der Aktivitäten als JSON Strings
		GsonBuilder gbuilder = new GsonBuilder();
		gbuilder.registerTypeAdapter(Activity.class, new ActivityConverter());
		gbuilder.registerTypeAdapter(AlarmEvent.class, new AlarmConverter());
		gbuilder.registerTypeAdapter(BedEvent.class, new BedConverter());
		gbuilder.registerTypeAdapter(CouchEvent.class, new CouchConverter());
		gbuilder.registerTypeAdapter(DoorEvent.class, new DoorConverter());
		gbuilder.registerTypeAdapter(PositionEvent.class, new PositionConverter());
		gbuilder.registerTypeAdapter(PowerEvent.class, new PowerConverter());
		gbuilder.registerTypeAdapter(StorageEvent.class, new StorageConverter());
		gbuilder.registerTypeAdapter(WaterEvent.class, new WaterConverter());
		gbuilder.setPrettyPrinting();
		gson = gbuilder.create();
		
		//Datensets mit Fehlerrate 5%
		List<KDD> kddlist5pro = new ArrayList<KDD>();
		kddlist5pro.add(new KDD("data\\output_woche_005prozent_01.csv"));
//		kddlist5pro.add(new KDD("data\\output_woche_005prozent_02.csv"));
//		kddlist5pro.add(new KDD("data\\output_woche_005prozent_03.csv"));
//		kddlist5pro.add(new KDD("data\\output_woche_005prozent_04.csv"));
//		kddlist5pro.add(new KDD("data\\output_woche_005prozent_05.csv"));
//		kddlist5pro.add(new KDD("data\\output_woche_005prozent_06.csv"));
//		kddlist5pro.add(new KDD("data\\output_woche_005prozent_07.csv"));
//		kddlist5pro.add(new KDD("data\\output_woche_005prozent_08.csv"));
//		kddlist5pro.add(new KDD("data\\output_woche_005prozent_09.csv"));
//		kddlist5pro.add(new KDD("data\\output_woche_005prozent_10.csv"));
//		
//		//Datensets mit Fehlerrate 10%
//		List<KDD> kddlist10pro = new ArrayList<KDD>();
//		kddlist10pro.add(new KDD("data\\output_woche_010prozent_01.csv"));
//		kddlist10pro.add(new KDD("data\\output_woche_010prozent_02.csv"));
//		kddlist10pro.add(new KDD("data\\output_woche_010prozent_03.csv"));
//		kddlist10pro.add(new KDD("data\\output_woche_010prozent_04.csv"));
//		kddlist10pro.add(new KDD("data\\output_woche_010prozent_05.csv"));
//		kddlist10pro.add(new KDD("data\\output_woche_010prozent_06.csv"));
//		kddlist10pro.add(new KDD("data\\output_woche_010prozent_07.csv"));
//		kddlist10pro.add(new KDD("data\\output_woche_010prozent_08.csv"));
//		kddlist10pro.add(new KDD("data\\output_woche_010prozent_09.csv"));
//		kddlist10pro.add(new KDD("data\\output_woche_010prozent_10.csv"));
		
//		//Datensets mit Fehlerrate 20%
//		List<KDD> kddlist20pro = new ArrayList<KDD>();
//		kddlist20pro.add(new KDD("data\\output_woche_020prozent_01.csv"));
//		kddlist20pro.add(new KDD("data\\output_woche_020prozent_02.csv"));
//		kddlist20pro.add(new KDD("data\\output_woche_020prozent_03.csv"));
//		kddlist20pro.add(new KDD("data\\output_woche_020prozent_04.csv"));
//		kddlist20pro.add(new KDD("data\\output_woche_020prozent_05.csv"));
//		kddlist20pro.add(new KDD("data\\output_woche_020prozent_06.csv"));
//		kddlist20pro.add(new KDD("data\\output_woche_020prozent_07.csv"));
//		kddlist20pro.add(new KDD("data\\output_woche_020prozent_08.csv"));
//		kddlist20pro.add(new KDD("data\\output_woche_020prozent_09.csv"));
//		kddlist20pro.add(new KDD("data\\output_woche_020prozent_10.csv"));
		
		//Datenset mit Fehlerrate 0%
		List<KDD> kddlist0pro = new ArrayList<KDD>();
		KDD kdd = new KDD("data\\output_woche_000prozent_01.csv");
		kddlist0pro.add(kdd);
//		KDD kdd = new KDD("C:\\workspacejuno\\scriptsimulator\\output.csv");
		
		//Map mit den gesuchten Aktivitätssequenzen
		Map<String,List<IEvent>> map = new HashMap<String, List<IEvent>>();
		
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
		
		//Aktivitätenentdeckung durchführen
//		kdd.dokdd();

//		for(KDD k: kddlist0pro)
//		{
//			k.dokdd();
//		}
		for(KDD k: kddlist5pro)
		{
			k.dokdd();
		}
//		for(KDD k: kddlist10pro)
//		{
//			k.dokdd();
//		}
//		for(KDD k: kddlist20pro)
//		{
//			k.dokdd();
//		}

//		for(KDD k: kddlist5pro)
//		{
////			k.checkEvents();
//			k.dokdd();
////			writeActivities("data\\activities\\activity_" + index++ + ".act", k.getActivities());
////			sendActivities(Helper.ACTIVEMQ_TOPICNAME, Helper.ACTIVEMQ_ADDRESS, k.getActivities());
//		}

		//Ergebnisse in Datei als CSV speichern
//		writeResults("0Prozent_keinubi", kddlist0pro, map);
		writeResults("5Prozent_alle", kddlist5pro, map);
//		writeResults("10Prozent_keinubi", kddlist10pro, map);
//		writeResults("20Prozent_keinubi", kddlist20pro, map);
		
		//Aktivitäten anzeigen
		gui.setPatternClusterList(kddlist5pro.get(0).getClusterList());
		gui.repaint();
		gui.saveImage();
		
		System.out.println("Fertig");
	}
	
	/**
	 * Mit dieser Metode werden die Abweichungen der vorgegebene Szenarien zu den entdeckten Aktivitäten
	 * in eine Textdatei im CSV gespeichert. 
	 * @param name Bezeichnung des Datesensets
	 * @param list Liste der unterchiedlichen Versuche
	 * @param map Map der vorgegebenen Szenarien
	 */
	private static List<Cluster> writeResults(String name, List<KDD> list, Map<String,List<IEvent>> map)
	{
		List<Cluster> bestCluster = new ArrayList<Cluster>();
		try {
			FileWriter writer = new FileWriter("data\\resultoutput" + name + ".csv");
		
//			writer.write(name + "\n");
			
			for(String s: map.keySet())
			{
				writer.write(s + ";");
	//			List<Double> acu = new ArrayList<Double>();
				for(KDD kdd: list)
				{
					List<IEvent> l = map.get(s);
					
					Cluster bestpc = null;
					double bestmatch = Double.MAX_VALUE;
					if(l.size()>0)
					{
						for(Cluster pc: kdd.getClusterList())
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
//						System.out.println(bestpc);
//						System.out.println(p);
						if(bestpc.containsPatternSequence(p))
						{
							writer.write("0;");
						}
						else
						{
							writer.write(String.format("%.3f", acurracy) +";");
						}
						
						bestCluster.add(bestpc);
						
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
			e.printStackTrace();
		}
		return bestCluster;
	}
	
	private static void writeActivities(String filename, List<Activity> activities)
	{
		if(gson == null)
		{
			System.out.println("gson ist null");
			return;
		}
		try
		{
			FileWriter writer = new FileWriter(filename);
			
			String output = "";
			
			output = gson.toJson(activities);
			
			writer.write(output);
			
			writer.flush();
			writer.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static void sendActivities(String topicName, String address, List<Activity> activities)
	{
		TopicConnection topicConnection;
	    TopicSession topicSession;
	    Topic topic;
	    TopicPublisher publisher;
	    ActiveMQConnectionFactory connectionFactory;
		
	    connectionFactory = new ActiveMQConnectionFactory(address);

        try {
            topicConnection = connectionFactory.createTopicConnection();
            topicConnection.start();

            topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            topic = topicSession.createTopic(topicName);

            publisher = topicSession.createPublisher(topic);

            
            for(Activity a: activities)
            {
            	TextMessage t = topicSession.createTextMessage(gson.toJson(a));
                publisher.send(topic, t);
            }
            
            publisher.close();
            topicSession.close();
            topicConnection.close();

        } catch (JMSException ex) {

            System.err.println("FATAL: \t|We can't connect to the ActiveMQ");
            System.err.println("FATAL: \t|Printing Stack:\n");
            ex.getStackTrace();
        }
	    
	}
	
}
