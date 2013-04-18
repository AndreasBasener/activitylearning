package org.livingplace.activitylearning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.livingplace.activitylearning.cluster.Cluster;
import org.livingplace.activitylearning.cluster.Kmeans;
import org.livingplace.activitylearning.data.IData;
import org.livingplace.activitylearning.data.UbisenseData;
import org.livingplace.activitylearning.event.EventList;
import org.livingplace.activitylearning.gui.XYFrame;
import org.livingplace.activitylearning.receive.AbstractDataReceiver;
import org.livingplace.activitylearning.receive.UbisenseReceiver;
import org.livingplace.activitylearning.sensor.Sensor;
import org.livingplace.activitylearning.sensor.UbisenseMockupSensor;
import org.livingplace.scriptsimulator.Helper;
import org.livingplace.scriptsimulator.Point3D;
import org.livingplace.scriptsimulator.script.entry.UbisenseMockupData;
import org.livingplace.scriptsimulator.script.json.Point3DJsonConverter;
import org.livingplace.scriptsimulator.script.json.UbisenseMockupDataJsonConverter;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Hello world!
 * 
 * @author Andreas Basener
 */
public class App {

//	private ActiveMQConnectionFactory connectionFactory;
//	private TopicConnection connection;
//	private boolean transacted = false;
//
//	private TopicSubscriber subscriber;
	
	
	private Gson gson;
	private GsonBuilder builder;
	
	private Random random;
	
	private static List<Sensor> sensorList;
	
	private List<AbstractDataReceiver> receiverList;
	
	private List<IData> dataList;
	
	private EventList eventList;
	
	private XYFrame graph;
	
	public static void main(String[] args) {
//		App app = new App();
//		app.createReceiver();
//		app.startReceiver();
		
		KDD kdd = new KDD("data\\csv_test001.csv");
//		System.out.println("Fertig");
	}
	
	public App()
	{
		receiverList = new ArrayList<AbstractDataReceiver>();

		this.builder = new GsonBuilder();
		builder.registerTypeAdapter(UbisenseMockupData.class, new UbisenseMockupDataJsonConverter());
		builder.registerTypeAdapter(Point3D.class, new Point3DJsonConverter());
		this.gson = builder.create();
		
		eventList = new EventList();
		dataList = new ArrayList<IData>();
		
		int xScale = 12, yScale = 17;
		random = new Random();
		graph = new XYFrame(500,500,xScale,yScale);
		graph.setVisible(true);
//		
//		List<Point3D> list = new ArrayList<Point3D>();
//		for(int i = 0; i < 100; i++)
//		{
//			list.add(new Point3D(random.nextDouble() * xScale, random.nextDouble() * yScale, 0));
//		}
		
//		list.add(new Point3D(1, 1, 0));
//		list.add(new Point3D(2, 1, 0));
//		list.add(new Point3D(1, 2, 0));
//		list.add(new Point3D(2, 2, 0));
//
//		list.add(new Point3D(8, 1, 0));
//		list.add(new Point3D(8, 2, 0));
//		list.add(new Point3D(9, 1, 0));
//		list.add(new Point3D(9, 2, 0));
//
//		list.add(new Point3D(1, 8, 0));
//		list.add(new Point3D(2, 8, 0));
//		list.add(new Point3D(1, 9, 0));
//		list.add(new Point3D(2, 9, 0));
//
//		list.add(new Point3D(8, 8, 0));
//		list.add(new Point3D(9, 8, 0));
//		list.add(new Point3D(8, 9, 0));
//		list.add(new Point3D(9, 9, 0));
//		
//		list.add(new Point3D(5, 5, 0));
//		Kmeans kmeans = new Kmeans(4, 50, list);
//		kmeans.cluster();
//		List<Cluster> clusterlist= kmeans.getCluster();
//		graph.addPoints(list);
//		graph.setCluster(clusterlist);
		
//		sensorList = new ArrayList<Sensor>();
//		
	}
	
	public void createReceiver()
	{
		String activeIP = Helper.URL;
		String mongoIP = Helper.MONGO_DB_IP;
		receiverList.add(new UbisenseReceiver(activeIP, mongoIP, gson));
		
		for(AbstractDataReceiver r : receiverList)
		{
			r.init();
		}
	}
	
	public void startReceiver()
	{
		for(final AbstractDataReceiver r : receiverList)
		{
			Thread t = new Thread(new Runnable() {
				public void run() {
					while(true)
					{
						IData data = r.receive();
						synchronized (dataList) {
							dataList.add(data);
							UbisenseData d = (UbisenseData) data;
							graph.addPoint(d.getPoint());
						}
					}
				}
			});
			t.start();
		}
	}
	
	public void createSensors()
	{
		Sensor sensor = new UbisenseMockupSensor("test",gson);
		sensor.connectToTopic(Helper.UBISENSE_ENTRY_TOPIC_NAME);
		
		sensorList.add(sensor);
	}
	
}
