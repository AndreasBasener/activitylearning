package org.livingplace.activitylearning.gui;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import org.livingplace.activitylearning.Pattern;
import org.livingplace.activitylearning.PatternCluster;
import org.livingplace.activitylearning.cluster.Cluster;
import org.livingplace.activitylearning.data.PositionData;
import org.livingplace.scriptsimulator.Point3D;
/**
 * 
 * @author Andreas Basener
 *
 */
public class XYPanel extends JPanel 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 203034290378916281L;

	private static final int offset = 50;
	private static final int lineStroke = 2;
	private static final int pointStroke = 4;
	private static final int centroidStroke = 6;

//	private int width;
//	private int height;

	private int xScale;
	private int yScale;

	private List<Point2D> point2DList;
	private List<Point3D> point3DList;
	
	private Random random;
	
	private List<Cluster> cluster;
	private List<PatternCluster> patternCluster;

	public XYPanel(int width, int height, int xScale, int yScale) 
	{
//		this.width = width;
//		this.height = height;
		this.xScale = xScale;
		this.yScale = yScale;

		this.point2DList = new ArrayList<Point2D>();
		this.point3DList = new ArrayList<Point3D>();
		this.cluster = new ArrayList<Cluster>();
		this.patternCluster = new ArrayList<PatternCluster>();
		
		this.random = new Random();
	}

	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		Dimension d = getSize();
		Insets i = getInsets();

		//Länge der Achsen berechnen
		int xScaleLength = d.width - i.left - i.right - offset * 2;
		int yScaleLength = d.height - i.bottom - i.top - offset * 2;
		
		//Länge der einzelnen Achsenabschnitte berechnen
		int xSteps = xScaleLength / xScale;
		int ySteps = yScaleLength / yScale;

		g2d.setStroke(new BasicStroke(lineStroke));
		
		// Ordinate, Abzisse und Achsenbeschriftung zeichnen
		g2d.drawLine(offset, offset, offset + xScaleLength, offset);
		g2d.drawLine(offset, offset, offset, offset + yScaleLength);
		
		g2d.drawString("X", offset + xScaleLength + 10, offset);
		g2d.drawString("Y", offset, offset + yScaleLength + 20);
		
		//Nullpunkt zeichnen
		g2d.drawString("0", offset - 15, offset - 7);

		//Abzisse beschriften
		for (int j = 1; j <= xScale; j++) 
		{
			//Achsensbschnitte und -beschriftung zeichnen
			g2d.drawLine(xSteps * j + offset, offset - 5, xSteps * j + offset, offset);
			g2d.drawString(j + "", offset + j * xSteps, offset - 7);
			
		}
		//Ordinate beschriften
		for (int j = 1; j <= yScale; j++) 
		{
			//Achsensbschnitte und -beschriftung zeichnen
			g2d.drawLine(offset - 5, ySteps * j + offset, offset, ySteps * j + offset);
			g2d.drawString(j + "", offset - 15, offset + j * ySteps);
			
		}

		g2d.setStroke(new BasicStroke(pointStroke));
		//Punkte einzeichnen
		for (Point2D p : point2DList) 
		{
			int x = (int) p.getX();
			int y = (int) p.getY();
			
			if(x > xScale)
				x = xScaleLength;
			else 
				x = x * xSteps;
			
			if(y > yScale)
				y = yScaleLength;
			else
				y = y * ySteps;
			
			g2d.setColor(Color.RED);
			g2d.drawLine(x + offset, y + offset, x + offset, y + offset);
			
		}
		for(Point3D p : point3DList)
		{
			double x = p.getX();
			double y = p.getY();
			
			if(x > xScale)
				x = xScaleLength;
			else 
				x = x * xSteps;
			
			if(y > yScale)
				y = yScaleLength;
			else
				y = y * ySteps;
			

			g2d.setColor(Color.GREEN);
			g2d.drawLine((int)x + offset,(int) y + offset,(int) x + offset,(int) y + offset);
		}
		int cCount = 0;
		for(Cluster c : cluster)
		{
//			Color color = new Color(random.nextInt());
			List<Point3D> l = c.getClusterPoints();
			Point3D centroid = c.getCentroid();

			g2d.setStroke(new BasicStroke(centroidStroke));
			
			switch (cCount) {
			case 0:
				g2d.setColor(Color.RED);
				break;
			case 1:
				g2d.setColor(Color.BLUE);
				break;
			case 2:
				g2d.setColor(Color.GREEN);
				break;
			case 3:
				g2d.setColor(Color.YELLOW);
				break;
			case 4:
				g2d.setColor(Color.GRAY);
				break;
			case 5:
				g2d.setColor(Color.ORANGE);
				break;
			default:
				g2d.setColor(new Color(random.nextInt()));
				break;
			}
//			g2d.setColor(color);
			
			g2d.drawLine((int) (centroid.getX() * xSteps) + offset, (int) (centroid.getY() * ySteps) + offset, 
					(int) (centroid.getX() * xSteps) + offset, (int) (centroid.getY() * ySteps) + offset);
			
			for(Point3D p : l)
			{
				g2d.setStroke(new BasicStroke(pointStroke));
//				g2d.setColor(color);
				g2d.drawLine((int) (p.getX() * xSteps) + offset, (int) (p.getY() * ySteps) + offset, 
						(int) (p.getX() * xSteps) + offset, (int) (p.getY() * ySteps) + offset);
			}
			
			cCount++;
		}
		for(PatternCluster pc: patternCluster)
		{
			g2d.setColor(new Color(random.nextInt()));
			g2d.setStroke(new BasicStroke(pointStroke));
			
			for(Pattern p: pc.getPatternList())
			{
				List<PositionData> plist = p.getSequence().getSequence();
				for(PositionData pd: plist)
				{
					g2d.drawLine((int) (pd.getX() * xSteps) + offset, (int) (pd.getY() * ySteps) + offset, 
							(int) (pd.getX() * xSteps) + offset, (int) (pd.getY() * ySteps) + offset);
				}
			}
		}
		setBackground(Color.WHITE);
	}

	public void addPoint(Point2D point) 
	{
		point2DList.add(point);
	}
	public void addPoint(Point3D point)
	{
		point3DList.add(point);
	}

	public List<Cluster> getCluster() {
		return cluster;
	}

	public void setCluster(List<Cluster> cluster) {
		this.cluster = cluster;
	}
	
	public void setPatternCluster(List<PatternCluster> patternCluster)
	{
		this.patternCluster = patternCluster;
	}

}
