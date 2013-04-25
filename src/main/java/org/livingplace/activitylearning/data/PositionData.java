package org.livingplace.activitylearning.data;

public class PositionData {

	private long time;
	
	private double x,y;
	
	public PositionData(String data)
	{
		String strarr[] = data.split(";");

		time = Long.valueOf(strarr[0]);
		x = Double.valueOf(strarr[1]);
		y = Double.valueOf(strarr[2]);
	}
	public PositionData(long time, double x, double y)
	{
		this.time = time;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o==this)
			return true;
		if(! (o instanceof PositionData))
			return false;
		
		PositionData d = (PositionData) o;
		
		return 
//				this.time == d.time &&
				this.x == d.x &&
				this.y == d.y;
		
	}
	
	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
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
	
	public static int getValueSize()
	{
		return 3;
	}
	
}
