package org.livingplace.activitylearning.data;

public class Data  implements IData{

	protected long time;
	
	protected Copy copy;
	
	public Data()
	{
		copy = Copy.FALSE;
	}
	
	public double distanceTo(IData data) {
		return 1;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
		
	}

	public Copy getCopy() {
		return copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}

	public String toShortString() {
		return "";
	}

}
