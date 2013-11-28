package org.livingplace.activitylearning;

public class Helper {

	public static final double MIN_SIMILAR_CLUSTER = 2;
	public static final double MIN_DISTANCE_COMPRESS = 2;
	public static final double MAX_DISTANCE = 2;
	public static final long MAX_DIFF_TIME = 1000;
	public static final double MEAN_OVERLAP_RATIO = 0.3;
	
	public static final double X = 12; // [m]
	public static final double Y = 17; // [m]
	
	public static final double MAX_DIAGONAL = Math.sqrt(X*X + Y*Y); //Diaginale [m]
	
	public static final double MAX_HUMAN_SPEED = 12.5; //[m/s]
	
	public static final String ACTIVEMQ_ADDRESS = "tcp://127.0.0.1:61616"; //Adresse vom ActiveMQ
	public static final String ACTIVEMQ_TOPICNAME = "activitiesTopic"; // Topicname der Aktivitäten
	
}
