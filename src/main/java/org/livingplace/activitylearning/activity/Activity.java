package org.livingplace.activitylearning.activity;

import org.livingplace.activitylearning.pattern.Cluster;

/**
 * 	Wrapperklasse für PatternCluster. Acrivity hat einen Json-Converter names Activityconverter.
 *  Dort passiert die ganze Magie, um die Aktivität als Json-Objekt weiter geben zu können.
 *  
 * @author Andreas Basener
 *
 */
public class Activity {
	
	private Cluster pc;

	public Activity(Cluster pc)
	{
		this.pc = pc;
	}
	
	/**
	 * @return the pc
	 */
	public Cluster getPc() {
		return pc;
	}

	/**
	 * @param pc the pc to set
	 */
	public void setPc(Cluster pc) {
		this.pc = pc;
	}

}
