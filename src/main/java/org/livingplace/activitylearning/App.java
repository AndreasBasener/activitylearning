package org.livingplace.activitylearning;

import org.livingplace.activitylearning.gui.GUI;


/**
 * Hello world!
 * 
 * @author Andreas Basener
 */
public class App {
	
	public static void main(String[] args) {
		
		GUI gui = new GUI();
		
		KDD kdd = new KDD("data\\csv_PatternTest003.csv");

		kdd.dokdd();
		
		gui.setPatternClusterList(kdd.getClusterList());
		
		gui.repaint();
//		System.out.println("Fertig");
	}
	
}
