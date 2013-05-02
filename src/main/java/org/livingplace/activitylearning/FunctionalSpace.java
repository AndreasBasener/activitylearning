package org.livingplace.activitylearning;

public class FunctionalSpace {

	private FunctionalSpaceLabel label;
	
	private double x,y;
	
	public FunctionalSpace(double x, double y)
	{
		this.x = x;
		this.y = y;
		label = FunctionalSpaceLabel.UNDEFINED;
		computeLabel();
	}
	
	private void computeLabel()
	{
		if((x >= 0 && x <= 4.13) && (y >= 0 && y <= 4.74)) // diningroom
		{
			label = FunctionalSpaceLabel.DINING_ROOM;
		}
		else if((x >= 0 && x <= 4.13) && (y > 4.74 && y <= 10.63)) // kitchen
		{
			label = FunctionalSpaceLabel.KITCHEN;
		}
		else if((x >= 0 && x <= 5.68) && (y > 10.63 && y <= 16.57)) // lounge
		{
			label = FunctionalSpaceLabel.LOUNGE;
		}
		else if((x > 4.13 && x <= 7.35) && (y >= 0 && y <= 10.63)) // hallway
		{
			label = FunctionalSpaceLabel.HALLWAY;
		}
		else if((x > 7.35 && x <= 11.66) && (y >= 0 && y <= 6.55)) // bedroom
		{
			label = FunctionalSpaceLabel.BEDROOM;
		}
		else if((x > 7.35 && x <= 11.66) && (y > 6.55 && y <= 8.9)) // bathroom
		{
			label = FunctionalSpaceLabel.BATHROOM;
		}
	}
	
	public String toString()
	{
		return "X: " + x + " Y: " + y + " space: " + label;
	}
	
	public boolean equals(Object o)
	{
		if(o==this)
			return true;
		if(! (o instanceof FunctionalSpace))
			return false;
		
		FunctionalSpace f = (FunctionalSpace) o;
		
		return this.label.equals(f.label);
	}

	/**
	 * @return the label
	 */
	public FunctionalSpaceLabel getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(FunctionalSpaceLabel label) {
		this.label = label;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
}
