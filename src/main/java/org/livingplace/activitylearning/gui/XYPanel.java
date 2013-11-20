package org.livingplace.activitylearning.gui;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import org.livingplace.activitylearning.event.IEvent;
import org.livingplace.activitylearning.event.PositionEvent;
import org.livingplace.activitylearning.pattern.Pattern;
import org.livingplace.activitylearning.pattern.Cluster;
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
//	private static final int centroidStroke = 6;

//	private int width;
//	private int height;

	private int xScale;
	private int yScale;

	private List<Point2D> point2DList;
	private List<Point3D> point3DList;
	
	private Random random;

	private List<Cluster> cluster;
	
	private Image floorplan;
	
	private Image[] shapes;
	private int shapescount = 12;

	public XYPanel(int width, int height, int xScale, int yScale) 
	{
//		this.width = width;
//		this.height = height;
		this.xScale = xScale;
		this.yScale = yScale;

		this.point2DList = new ArrayList<Point2D>();
		this.point3DList = new ArrayList<Point3D>();
		this.cluster = new ArrayList<Cluster>();
		
		this.random = new Random();
		
		this.floorplan = Toolkit.getDefaultToolkit().getImage("data\\LPGrundriss_fs.png");
		
		shapes = new Image[shapescount];
		for(int i = 0; i < shapescount; i++)
		{
			shapes[i] = Toolkit.getDefaultToolkit().getImage("data\\shapes\\shape"+ (i+1) + ".png");
		}
	}

	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		int cCount = 0;

		Graphics2D g2d = (Graphics2D) g;

		Dimension d = getSize();
		Insets i = getInsets();

//		int ix=10,yp= 10;
//		for(Image image: shapes)
//		{
//			g2d.drawImage(image, ix +=10, yp, null);
//		}
		
		
		//Länge der Achsen berechnen
		int xScaleLength = d.width - i.left - i.right - offset * 2;
		int yScaleLength = d.height - i.bottom - i.top - offset * 2;
		
		//Länge der einzelnen Achsenabschnitte berechnen
		int xSteps = xScaleLength / xScale;
		int ySteps = yScaleLength / yScale;

		g2d.drawImage(floorplan, 
						offset, offset, d.width - offset, d.height - offset, 
						0, 0, floorplan.getWidth(this), floorplan.getHeight(this), 
						this);
		
		g2d.setStroke(new BasicStroke(lineStroke));
		
		// Ordinate, Abzisse und Achsenbeschriftung zeichnen
		g2d.drawLine(offset, offset + yScaleLength, offset + xScaleLength, offset + yScaleLength);
		g2d.drawLine(offset, offset, offset, offset + yScaleLength);
		
		g2d.drawString("X [m]", offset + xScaleLength + 10, offset + yScaleLength + 7);
		g2d.drawString("Y [m]", offset, offset);
		
		//Nullpunkt zeichnen
		g2d.drawString("0", offset - 7, offset + yScaleLength + 10);

		//Abzisse beschriften
		for (int j = 1; j <= xScale; j++) 
		{
			//Achsensbschnitte und -beschriftung zeichnen
			g2d.drawLine(xSteps * j + offset, offset + 5 + yScaleLength, xSteps * j + offset, offset + yScaleLength);
			g2d.drawString(j + "", offset + j * xSteps - 7, offset + yScaleLength + 20);
			
		}
		//Ordinate beschriften
		for (int j = 1; j <= yScale; j++) 
		{
			//Achsensbschnitte und -beschriftung zeichnen
			int y = d.height - offset - (ySteps * j);
			g2d.drawLine(offset - 5, y, offset, y);
			g2d.drawString(j + "", offset - 20, y);
//			g2d.drawLine(offset - 5, ySteps * j + offset, offset, ySteps * j + offset);
//			g2d.drawString(j + "", offset - 20, offset + (yScale - j + 1) * ySteps);
			
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
			double y2 = d.getHeight() - offset - y;
			g2d.drawLine((int)x + offset, (int) y2, (int) x + offset, (int) y2);
		}
		
		for(Cluster pc: cluster)
		{
			if (pc.getPatternList().size() < 1)
				continue;
//			g2d.setColor(new Color(random.nextInt()));
			g2d.setStroke(new BasicStroke(pointStroke));
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
			case 6:
				g2d.setColor(Color.BLACK);
				break;
			case 7:
				g2d.setColor(Color.CYAN);
				break;
			case 8:
				g2d.setColor(Color.LIGHT_GRAY);
				break;
			default:
				g2d.setColor(new Color(random.nextInt()));
				break;
			}
			
//			Image img;
//			if(cCount < shapescount)
//				img = shapes[cCount];
//			else
//				img = shapes[shapescount-1];
//			for(Pattern p: pc.getPatternList())
//			{
//				List<IData> plist = p.getSequence().getDataSequence();
//				for(IData data: plist)
//				{
//					if (data instanceof PositionData)
//					{
//						PositionData pos = (PositionData) data;
//						int y = (int)(d.getHeight() - pos.getY() * ySteps) - offset;
//						int x = (int)(pos.getX() * xSteps) + offset;
//						g2d.drawImage(img, x, y, null);
//					}
//				}
//			}
			
			for(Pattern p: pc.getPatternList())
			{
				List<IEvent> plist = p.getSequence().getDataSequence();
				for(IEvent event: plist)
				{
					if (event instanceof PositionEvent)
					{
						PositionEvent pos = (PositionEvent) event;
						double y = d.getHeight() - pos.getY() * ySteps - offset;
						g2d.drawLine((int) (pos.getX() * xSteps) + offset, (int) y, 
								(int) (pos.getX() * xSteps) + offset, (int) y);
					}
				}
			}
			cCount++;
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
	
	public void setPatternCluster(List<Cluster> cluster)
	{
		this.cluster = cluster;
	}

}
