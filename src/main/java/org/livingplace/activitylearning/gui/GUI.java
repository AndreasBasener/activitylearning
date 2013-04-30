package org.livingplace.activitylearning.gui;

import java.util.ArrayList;
import java.util.List;

import org.livingplace.activitylearning.cluster.Cluster;
import org.livingplace.scriptsimulator.Point3D;

public class GUI {

	private XYFrame graph;
	
	private List<Cluster> clusterlist;
	
	public GUI()
	{
		this.clusterlist = new ArrayList<Cluster>();
		
		int xScale = 12, yScale = 17;
		graph = new XYFrame(500,500,xScale,yScale);
		graph.setVisible(true);
	}
	
	public void drawPoint3D(Point3D point)
	{
		graph.addPoint(point);
	}
	
	public void addCluster(Cluster cluster)
	{
		clusterlist.add(cluster);
		graph.setCluster(clusterlist);
	}
}
