package org.livingplace.activitylearning.pattern;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class PatternInfo {

	private int numLabels;
	private double meantime;
	private List<Integer> labels;
	
	private PatternInfo()
	{
		this.labels = new ArrayList<Integer>();
	}

	public void addLabel(int label)
	{
		this.labels.add(label);
	}
	
	/**
	 * @return the numLabels
	 */
	public int getNumLabels() {
		return numLabels;
	}

	/**
	 * @param numLabels the numLabels to set
	 */
	public void setNumLabels(int numLabels) {
		this.numLabels = numLabels;
	}

	/**
	 * @return the meantime
	 */
	public double getMeantime() {
		return meantime;
	}

	/**
	 * @param meantime the meantime to set
	 */
	public void setMeantime(double meantime) {
		this.meantime = meantime;
	}

	/**
	 * @return the labels
	 */
	public List<Integer> getLabels() {
		return labels;
	}

	/**
	 * @param labels the labels to set
	 */
	public void setLabels(List<Integer> labels) {
		this.labels = labels;
	}
	
}
