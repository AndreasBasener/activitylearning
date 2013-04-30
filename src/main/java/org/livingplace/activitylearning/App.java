package org.livingplace.activitylearning;

import java.util.ArrayList;
import java.util.List;
import org.livingplace.activitylearning.cluster.Cluster;
import org.livingplace.activitylearning.data.PositionData;
import org.livingplace.activitylearning.gui.GUI;
import org.livingplace.scriptsimulator.Point3D;


/**
 * Hello world!
 * 
 * @author Andreas Basener
 */
public class App {
	
	public static void main(String[] args) {
		
//		GUI gui = new GUI();
		
		List<PositionData> data = new ArrayList<PositionData>();
		data.add(new PositionData(0,0,0));
		data.add(new PositionData(1,1,1));
		data.add(new PositionData(2,2,2));
		data.add(new PositionData(0,0,0));
		data.add(new PositionData(1,1,1));
		data.add(new PositionData(3,3,3));
		data.add(new PositionData(0,0,0));
		data.add(new PositionData(1,1,1));
		data.add(new PositionData(4,4,4));
		data.add(new PositionData(0,0,0));
		data.add(new PositionData(1,1,1));
		data.add(new PositionData(5,5,5));
		data.add(new PositionData(0,0,0));
		data.add(new PositionData(1,1,1));
		data.add(new PositionData(2,2,2));
		
		
		KDD kdd = new KDD("data\\csv_PatternTest003.csv");
//		KDD kdd = new KDD(data);
		kdd.dokdd();
		
//		for(PositionData p: kdd.getPositionList())
//		{
//			gui.drawPoint3D(p.toPoint3D());
//		}
//		Cluster cluster = new Cluster();
//		cluster.setCentroid(new Point3D(0, 0, 0));
//		for(Pattern p: kdd.getPatternList())
//		{
//			for(PositionData pos: p.getSequence().getSequence())
//			{
//				cluster.addPoint(pos.toPoint3D());
//			}
//		}
//		gui.addCluster(cluster);
		
//		System.out.println("Fertig");
	}
	
}
