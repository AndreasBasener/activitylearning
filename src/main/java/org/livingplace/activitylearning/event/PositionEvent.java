package org.livingplace.activitylearning.event;

import org.livingplace.activitylearning.Helper;
import org.livingplace.scriptsimulator.Point3D;

public class PositionEvent extends Event{

//	private long time;
	
	private double x,y;
	
//	private Copy copy = Copy.FALSE;
	
	private FunctionalSpace fSpace;
	
	public PositionEvent(String data)
	{
		String strarr[] = data.split(";");

		time = Long.valueOf(strarr[0]);
		x = Double.valueOf(strarr[1]);
		y = Double.valueOf(strarr[2]);
		fSpace = new FunctionalSpace(x, y);
	}
	public PositionEvent(long time, double x, double y)
	{
		this.time = time;
		this.x = x;
		this.y = y;
		fSpace = new FunctionalSpace(x, y);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o==this)
			return true;
		if(! (o instanceof PositionEvent))
			return false;
		
		PositionEvent d = (PositionEvent) o;
		
		return 
//				this.time == d.time &&
//				this.x == d.x &&
//				this.y == d.y;
				this.fSpace.equals(d.fSpace);
		
	}
	
	public Point3D toPoint3D()
	{
		return new Point3D(x, y, 0);
	}
	
	public String toString()
	{
		return "Time: " + time + " FSpace: " + fSpace + " Copy: " + copy ;
	}
	
	public String toShortString()
	{
		return fSpace.getLabel().toString();
	}
	
	public double euclidianDistance(PositionEvent data)
	{
		double dist = Math.pow(x - data.x, 2) + Math.pow(y - data.y, 2);
		dist = Math.sqrt(dist);
		
		return dist;
	}

	public double distanceTo(IEvent event) {
		if (event == this)
			return 0;
		if (event instanceof PositionEvent)
		{
			PositionEvent p = (PositionEvent) event;
//			return euclidianDistance(p);
			if(this.fSpace.equals(p.fSpace))
				return 0;
			else
				return euclidianDistance(p) / Helper.MAX_DIAGONAL;
		}
		return 1;
	}
	
	/**
	 * @return the time
	 */
//	public long getTime() {
//		return time;
//	}
//	/**
//	 * @param time the time to set
//	 */
//	public void setTime(long time) {
//		this.time = time;
//	}
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
	
	@Deprecated
	public static int getValueSize()
	{
		return 3;
	}
	/**
	 * @return the copy
	 */
//	public Copy getCopy() {
//		return copy;
//	}
//	/**
//	 * @param copy the copy to set
//	 */
//	public void setCopy(Copy copy) {
//		this.copy = copy;
//	}
	/**
	 * @return the fSpace
	 */
	public FunctionalSpace getfSpace() {
		return fSpace;
	}
	/**
	 * @param fSpace the fSpace to set
	 */
	public void setfSpace(FunctionalSpace fSpace) {
		this.fSpace = fSpace;
	}
	
}
