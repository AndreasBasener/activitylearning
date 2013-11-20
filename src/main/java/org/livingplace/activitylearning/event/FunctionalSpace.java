package org.livingplace.activitylearning.event;


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
			if((x >= 0.5 && x <= 2.5) && (y >= 0.5 && y <= 3.5))
				label = FunctionalSpaceLabel.DININGROOM_TABLE;
			else if((x >= 0.5 && x <= 3.5) && (y >= 4.0 && y <= 4.73))
				label = FunctionalSpaceLabel.DININGROOM_TOUCHTABLE;
			else
				label = FunctionalSpaceLabel.DININGROOM;
		}
		else if((x >= 0 && x <= 4.13) && (y > 4.74 && y <= 10.63)) // kitchen
		{
			if((x >= 0.0 && x <= 1.75) && (y >= 4.75 && y <= 5.60))
				label = FunctionalSpaceLabel.KITCHEN_SHELF;
			else if((x >= 0.0 && x <= 1.75) && (y >= 5.65 && y <= 6.50))
				label = FunctionalSpaceLabel.KITCHEN_COFFEEMACHINE;
			else if((x >= 0.0 && x <= 1.75) && (y >= 6.55 && y <= 7.40))
				label = FunctionalSpaceLabel.KITCHEN_SINK;
			else if((x >= 3.0 && x <= 4.10) && (y >= 6.0 && y <= 7.10))
				label = FunctionalSpaceLabel.KITCHEN_FRIDGE;
			else if((x >= 1.4 && x <= 3.8) && (y >= 7.6 && y <= 10.2))
				label = FunctionalSpaceLabel.KITCHEN_COOKING;
			else
				label = FunctionalSpaceLabel.KITCHEN;
		}
		else if((x >= 0 && x <= 5.68) && (y > 10.63 && y <= 16.57)) // lounge
		{
			if((x >= 1.0 && x <= 4.8) && (y >= 12.6 && y <= 15.2))
				label = FunctionalSpaceLabel.LOUNGE_COUCH;
			else
				label = FunctionalSpaceLabel.LOUNGE;
		}
		else if((x > 4.13 && x <= 7.35) && (y >= 0 && y <= 10.63)) // hallway
		{
			if((x >= 4.6 && x <= 6.6) && (y >= 9.2 && y <= 10.6))
				label = FunctionalSpaceLabel.HALLWAY_ENTRANCE;
			else
				label = FunctionalSpaceLabel.HALLWAY;
		}
		else if((x > 7.35 && x <= 11.66) && (y >= 1.0 && y <= 6.55)) // bedroom
		{
			if((x >= 8.6 && x <= 10.9) && (y >= 3.5 && y <= 6.5))
				label = FunctionalSpaceLabel.BEDROOM_BED;
			else if((x >= 7.36 && x <= 11.6) && (y >= 1.0 && y <= 2.0))
				label = FunctionalSpaceLabel.BEDROOM_CLOSET;
			else
				label = FunctionalSpaceLabel.BEDROOM;
		}
		else if((x > 7.35 && x <= 11.66) && (y > 6.55 && y <= 8.9)) // bathroom
		{
			if((x >= 7.36 && x <= 8.36) && (y >= 7.57 && y <= 8.89))
				label = FunctionalSpaceLabel.BATHROOM_SHOWER;
			else if ((x >= 8.9 && x <= 9.9) && (y >= 7.8 && y <= 8.89))
				label = FunctionalSpaceLabel.BATHROOM_SINK;
			else if((x >= 10.4 && x <= 11.4) && (y >= 7.8 && y <= 8.89))
				label = FunctionalSpaceLabel.BATHROOM_TOILET;
			else
				label = FunctionalSpaceLabel.BATHROOM;
		}
	}
	
	public double distanceTo(FunctionalSpace space)
	{
		switch (label) {
		case KITCHEN:
			
			break;

		default:
			break;
		}
		return 0;
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
