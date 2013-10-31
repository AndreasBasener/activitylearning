package org.livingplace.activitylearning.data;

import org.livingplace.scriptsimulator.script.entry.CouchEntry.CouchID;

public class CouchData extends Data{

	private CouchID couchID;
	
	private static double distances[][] = 	{{0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0},
											{0.1, 0.0, 0.3, 0.2, 0.5, 0.4, 0.7, 0.6, 0.9, 0.8, 0.9, 1.0, 1.0, 1.0, 1.0, 1.0},
											{0.2, 0.3, 0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.8, 0.9, 1.0, 1.0},
											{0.3, 0.2, 0.1, 0.0, 0.3, 0.2, 0.5, 0.4, 0.7, 0.6, 0.7, 0.8, 0.9, 0.8, 0.9, 1.0},
											{0.4, 0.5, 0.2, 0.3, 0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.6, 0.7, 0.8 ,0.9},
											{0.5, 0.4, 0.3, 0.2, 0.1, 0.0, 0.3, 0.2, 0.5, 0.4, 0.5, 0.6, 0.7, 0.6, 0.7, 0.8},
											{0.6, 0.7, 0.4, 0.5, 0.2, 0.3, 0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.4, 0.5, 0.6, 0.7},
											{0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.0, 0.3, 0.4, 0.5, 0.4, 0.5, 0.4, 0.5, 0.6},
											{0.8, 0.9, 0.6, 0.7, 0.4, 0.5, 0.2, 0.3, 0.0, 0.1, 0.2, 0.3, 0.2, 0.3, 0.4, 0.5},
											{0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.0, 0.1, 0.2, 0.3, 0.2, 0.3, 0.4},
											{1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.0, 0.1, 0.4, 0.3, 0.2, 0.3},
											{1.0, 1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.0, 0.5, 0.4, 0.3, 0.2},
											{1.0, 1.0, 0.8, 0.9, 0.6, 0.7, 0.4, 0.5, 0.2, 0.3, 0.4, 0.5, 0.0, 0.1, 0.2, 0.3},
											{1.0, 1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.3, 0.4, 0.1, 0.0, 0.1, 0.2},
											{1.0, 1.0, 1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.3, 0.2, 0.1, 0.0, 0.1},
											{1.0, 1.0, 1.0, 1.0, 0.9, 0.9, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.3, 0.2, 0.1, 0.0},
											};
	
	public CouchData(String data)
	{
		super();
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		couchID = CouchID.valueOf(strarr[1]);
	}
	
	public CouchData(CouchID id, long time)
	{
		this.couchID = id;
		this.time = time;
	}
	
	@Override
	public double distanceTo(IData data)
	{
		if(data == this)
			return 0;
		
		if(data instanceof CouchData)
		{
			int x = CouchID.valueOf(couchID.name()).ordinal();
			int y = CouchID.valueOf(((CouchData) data).getCouchID().name()).ordinal();
			return distances[x][y];
		}
		
		return 1;
	}
	
	public boolean equals(Object o)
	{
		if(o == null)
			return false;
		if(!(o instanceof CouchData))
			return false;
		
		CouchData data = (CouchData) o;
		
		return couchID.equals(data.couchID);
	}


	public String toShortString()
	{
		return couchID.toString();
	}
	
	public String toString()
	{
		return "CouchID: " + couchID;
	}
	
	/**
	 * @return the couchID
	 */
	public CouchID getCouchID() {
		return couchID;
	}

	/**
	 * @param couchID the couchID to set
	 */
	public void setCouchID(CouchID couchID) {
		this.couchID = couchID;
	}
	
	
	
}
