package org.livingplace.activitylearning.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import org.livingplace.activitylearning.pattern.Cluster;
import org.livingplace.scriptsimulator.Point3D;

public class GUI {

	private XYFrame graph;
	
	int x = 550, y = 700, xScale = 12, yScale = 17;
	
	private List<Cluster> patternClusterList;
	
	public GUI()
	{
		this.patternClusterList = new ArrayList<Cluster>();
		
		//int xScale = 12, yScale = 17;
		graph = new XYFrame(x,y,xScale,yScale);
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
	
	public void addPatternCluster(Cluster cluster)
	{
		this.patternClusterList.add(cluster);
	}

	/**
	 * @return the patternClusterList
	 */
	public List<Cluster> getPatternClusterList() {
		return patternClusterList;
	}

	/**
	 * @param patternClusterList the patternClusterList to set
	 */
	public void setPatternClusterList(List<Cluster> patternClusterList) {
		this.patternClusterList = patternClusterList;
		graph.setPatternClusterList(patternClusterList);
	}
	
	public void saveImage()
	{
		BufferedImage img = new BufferedImage(graph.getPanel().getWidth(), graph.getPanel().getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = img.createGraphics();
		graph.getPanel().paintAll(g2d);
		g2d.dispose();
		
		try {
//			Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName("jpg");
//			ImageWriter imageWriter = iterator.next();
//			ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
//			imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//			imageWriteParam.setCompressionQuality(1);
//			ImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(outputstream);
//			imageWriter.setOutput(imageOutputStream);
//			IIOImage iioimage = new IIOImage(img, null, null);
//			imageWriter.write(null, iioimage, imageWriteParam);
//			imageOutputStream.flush();
			ImageIO.write(img, "png", new File("activityresult.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
