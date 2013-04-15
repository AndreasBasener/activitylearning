package org.livingplace.activitylearning.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.livingplace.scriptsimulator.Point3D;


/**
 * Kmeans Implementation für Point3D Punkte
 * 
 * @see <a href="http://java-ml.svn.sourceforge.net/viewvc/java-ml/trunk/src/net/sf/javaml/clustering/KMeans.java?revision=1291&view=markup">Java-ML: Kmeans</a>
 * @author Andreas Basener
 *
 */
public class Kmeans {

	int dimension = 3; //Dimension der Daten. Bei 3D Daten genau 3 :-)

	private int clusterCount;
	private int maxIterations;
	private List<Point3D> data;
	private Point3D[] centroids;
	private List<Cluster> cluster;
	
	private Random random;
	
//	private double maxX = 0, minX = 0, maxY = 0, minY = 0, maxZ = 0, minZ = 0;
	private double min[];
	private double max[];
	private double maxDiff[];
	
	public Kmeans(int clusterCount, int maxIterations, List<Point3D> data)
	{
		this.clusterCount = clusterCount;
		this.maxIterations = maxIterations;
		this.data = data;
		centroids = new Point3D[clusterCount];
		cluster = new ArrayList<Cluster>();
		min = new double[dimension];
		max = new double[dimension];
		maxDiff = new double[dimension];
		for (int i = 0; i < clusterCount; i++)
		{
			cluster.add(new Cluster());
		}
		this.random = new Random();
	}
	
	public void cluster()
	{
		for(Point3D p : data)
		{
			double x = p.getX();
			double y = p.getY();
			double z = p.getZ();
			
			if(x > max[0])
				max[0] = x;
			if(x < min[0])
				min[0] = x;
			
			if(y > max[1])
				max[1] = y;
			if(y < min[1])
				min[1] = y;
			
			if(z > max[2])
				max[2] = z;
			if(z < max[2])
				min[2] = z;
		}
		for(int i = 0; i < centroids.length; i++)
		{
			maxDiff[0] = Math.abs(max[0] - min[0]);
			double randX = min[0] + random.nextDouble() * maxDiff[0];
			
			maxDiff[1] = Math.abs(max[1] - min[1]);
			double randY = min[1] + random.nextDouble() * maxDiff[1];
			
			maxDiff[2] = Math.abs(max[2] - min[2]);
			double randZ = min[2] + random.nextDouble() * maxDiff[2];
			
			centroids[i] = new Point3D(randX, randY, randZ);
//			cluster.get(i).setCentroid(centroids[i]);
		}
		boolean centroidsChanged = true;
        boolean randomCentroids = true;
        int iteration = 0;
		while(randomCentroids || (iteration < maxIterations && centroidsChanged))
		{
			iteration++;
			int assignements[] = new int[data.size()];
			for(int i = 0; i < data.size(); i++)
			{
				int nearestCentroid = 0;
				double shortestDistance = centroids[0].distance(data.get(i));
				
				for(int j = 1; j < centroids.length; j++)
				{
					double currentDistance = centroids[j].distance(data.get(i));
					if(currentDistance < shortestDistance)
					{
						nearestCentroid = j;
						shortestDistance = currentDistance;
					}
				}
				assignements[i] = nearestCentroid;
//				cluster.get(nearestCentroid).addPoint(data.get(i));
			}

			//Clusterzenter neu berechnen
			double[][] sumPosition = new double[this.clusterCount][dimension];
			int[] countPosition = new int[this.clusterCount];
			for(int i = 0; i < data.size(); i++)
			{
				Point3D p = data.get(i);
				for(int j = 0; j < dimension; j++)
				{
					sumPosition[assignements[i]][j] += p.getAsArray()[j];
				}
				countPosition[assignements[i]]++;
			}
			
			centroidsChanged = false;
			randomCentroids = false;
			
			for(int i = 0; i < clusterCount; i++)
			{
				//Hat ein Centroid überhaupt Punkte in der Nähe?
				if(countPosition[i] > 0)
				{
					double[] mean = new double[dimension]; //Durchschnitt der einzelnen Koordinaten
					for( int j = 0; j < dimension; j++)
					{
						mean[j] = (float) sumPosition[i][j] / countPosition[i];
					}
					Point3D newCentroid = new Point3D(mean[0], mean[1], mean[2]);
					
					if(centroids[i].distance(newCentroid) > 0.0001)
					{
						centroidsChanged = true;
						centroids[i] = newCentroid;
					}
				}
				else //Centroid zufällig neu versetzen
				{
					double[] val = new double[dimension];
					for(int j = 0; j < dimension; j++)
					{
						
						val[i] = min[j] + random.nextDouble() * maxDiff[j];
					}
					randomCentroids = true;
					centroids[i] = new Point3D(val[0], val[1], val[2]);
				}
			}
			
		}
		System.out.println("Iterationen: " + iteration);
		//berechnete Cluster und Centroids zuordnen
		for(int i = 0; i < clusterCount; i++)
		{
			cluster.get(i).setCentroid(centroids[i]);
		}
		for(int i = 0; i < data.size(); i++)
		{
//			cluster.get(assignements[i]).addPoint(data.get(i));
			int nearestCentroid = 0;
			double shortestDistance = centroids[0].distance(data.get(i));
			
			for(int j = 1; j < centroids.length; j++)
			{
				double currentDistance = centroids[j].distance(data.get(i));
				if(currentDistance < shortestDistance)
				{
					nearestCentroid = j;
					shortestDistance = currentDistance;
				}
			}
			cluster.get(nearestCentroid).addPoint(data.get(i));
		}
	}


	public List<Point3D> getData() {
		return data;
	}

	public void setData(List<Point3D> data) {
		this.data = data;
	}

	public Point3D[] getCentroids() {
		return centroids;
	}

	public void setCentroids(Point3D[] centroids) {
		this.centroids = centroids;
	}

	public List<Cluster> getCluster() {
		return cluster;
	}

	public void setCluster(List<Cluster> cluster) {
		this.cluster = cluster;
	}

	
}
