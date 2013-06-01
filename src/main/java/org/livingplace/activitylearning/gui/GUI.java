package org.livingplace.activitylearning.gui;

import java.util.ArrayList;
import java.util.List;

import org.livingplace.activitylearning.pattern.PatternCluster;
import org.livingplace.scriptsimulator.Point3D;

public class GUI {

	private XYFrame graph;
	
	private List<PatternCluster> patternClusterList;
	
	public GUI()
	{
		this.patternClusterList = new ArrayList<PatternCluster>();
		
		int xScale = 12, yScale = 17;
		graph = new XYFrame(500,500,xScale,yScale);
		graph.setVisible(true);
	}
	
	public void repaint()
	{
		graph.repaint();
	}
	
	public void drawPoint3D(Point3D point)
	{
		graph.addPoint(point);
	}
	public void setPoint3D(List<Point3D> list)
	{
		graph.addPoints(list);
	}
	
	public void addPatternCluster(PatternCluster patternCluster)
	{
		this.patternClusterList.add(patternCluster);
	}

	/**
	 * @return the patternClusterList
	 */
	public List<PatternCluster> getPatternClusterList() {
		return patternClusterList;
	}

	/**
	 * @param patternClusterList the patternClusterList to set
	 */
	public void setPatternClusterList(List<PatternCluster> patternClusterList) {
		this.patternClusterList = patternClusterList;
		graph.setPatternClusterList(patternClusterList);
	}
}
