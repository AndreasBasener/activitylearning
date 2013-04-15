package org.livingplace.activitylearning.gui;


import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JFrame;

import org.livingplace.activitylearning.cluster.Cluster;
import org.livingplace.scriptsimulator.Point3D;

public class XYFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8377287475390841404L;

	private int width;
	private int height;
	
	private int xScale;
	private int yScale;
	
	private XYPanel panel;
	
	public XYFrame()
	{
		this(500,500,10,10);
	}
	public XYFrame(int width, int height, int xScale, int yScale)
	{
		this.width = width;
		this.height = height;
		this.xScale = xScale;
		this.yScale = yScale;
		
		panel = new XYPanel(width, height, xScale, yScale);
		add(panel);
		
		setTitle("XY Graph");
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void addPoint(Point2D point)
	{
		
		panel.addPoint(point);
		this.repaint();
	}
	public void addPoint(Point3D point)
	{
		panel.addPoint(point);
		this.repaint();
	}
	public void addPoints(List<Point3D> list)
	{
		for(Point3D p : list)
		{
			panel.addPoint(p);
		}
		this.repaint();
	}
	public void setCluster(List<Cluster> cluster)
	{
		panel.setCluster(cluster);
	}
	
}
