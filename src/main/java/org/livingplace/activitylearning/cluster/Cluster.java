package org.livingplace.activitylearning.cluster;



import java.util.ArrayList;
import java.util.List;

import org.livingplace.scriptsimulator.Point3D;

public class Cluster {

	private List<Point3D> clusterpoints;
	
	private Point3D centroid;
	
	public Cluster()
	{
		this.clusterpoints = new ArrayList<Point3D>();
		
	}
	
	public void addPoint(Point3D point)
	{
		this.clusterpoints.add(point);
	}

	public List<Point3D> getClusterPoints() {
		return clusterpoints;
	}

	public void setClusterPoints(List<Point3D> list) {
		this.clusterpoints = list;
	}

	public Point3D getCentroid() {
		return centroid;
	}

	public void setCentroid(Point3D centroid) {
		this.centroid = centroid;
	}
}
