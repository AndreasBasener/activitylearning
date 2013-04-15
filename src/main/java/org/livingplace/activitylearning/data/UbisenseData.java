package org.livingplace.activitylearning.data;

import org.joda.time.DateTime;
import org.livingplace.scriptsimulator.Point3D;

public class UbisenseData implements IData{

	private Point3D point;
	private long time;
	
	public UbisenseData(long time, Point3D point)
	{
		this.time = time;
		this.setPoint(point);
	}
	
	public double distanceTo(IData data) {
		if(data instanceof UbisenseData)
		{
			UbisenseData d = (UbisenseData) data;
			double distance = point.distance(d.getPoint());
			return distance;
		}
		return 0;
	}

	/**
	 * @return the point
	 */
	public Point3D getPoint() {
		return point;
	}

	/**
	 * @param point the point to set
	 */
	public void setPoint(Point3D point) {
		this.point = point;
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

}
