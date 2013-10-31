package org.livingplace.activitylearning.data;

import org.livingplace.scriptsimulator.script.entry.BedEntry.SleepState;

public class BedData extends Data{

	private SleepState state;
	
	public BedData(String data)
	{
		super();
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		state = SleepState.valueOf(strarr[1]);
	}
	
	public BedData(SleepState state, long time)
	{
		this.state = state;
		this.time = time;
	}

	/**
	 * Liefert die Distanz zwischn diesem Objekt und <code>data</code>, nach folgender Matrix:</br>
     * <table border="1">
     *   <tr>
     *     <th> </th>
     *     <th>WACH</th>
     *     <th>N1</th>
     *     <th>N2</th>
     *     <th>N3</th>
     *     <th>N4</th>
     *     <th>REM</th>
     *   </tr>
     *   <tr>
     *     <th>WACH</th>
     *     <th>0</th>
     *     <th>0.2</th>
     *     <th>0.4</th>
     *     <th>0.6</th>
     *     <th>0.8</th>
     *     <th>0.2</th>
     *   </tr>
     *   <tr>
     *     <th>N1</th>
     *     <th>0.2</th>
     *     <th>0</th>
     *     <th>0.2</th>
     *     <th>0.4</th>
     *     <th>0.6</th>
     *     <th>0.2</th>
     *   </tr>
     *   <tr>
     *     <th>N2</th>
     *     <th>0.4</th>
     *     <th>0.2</th>
     *     <th>0</th>
     *     <th>0.2</th>
     *     <th>0.4</th>
     *     <th>0.4</th>
     *   </tr>
     *   <tr>
     *     <th>N3</th>
     *     <th>0.6</th>
     *     <th>0.4</th>
     *     <th>0.2</th>
     *     <th>0</th>
     *     <th>0.2</th>
     *     <th>0.6</th>
     *   </tr>
     *   <tr>
     *     <th>N4</th>
     *     <th>0.8</th>
     *     <th>0.6</th>
     *     <th>0.4</th>
     *     <th>0.2</th>
     *     <th>0</th>
     *     <th>0.8</th>
     *   </tr>
     *   <tr>
     *     <th>REM</th>
     *     <th>0.2</th>
     *     <th>0.2</th>
     *     <th>0.4</th>
     *     <th>0.6</th>
     *     <th>0.8</th>
     *     <th>0</th>
     *   </tr>
     * </table>
	 */
	public double distanceTo(IData data) {
		if(data == this)
			return 0;
		if(data instanceof BedData)
		{
			BedData s = (BedData) data;
			switch (state) {
			case WACH:
				switch (s.state) {
				case WACH:
					return 0;
				case N1:
					return 0.2;
				case N2:
					return 0.4;
				case N3:
					return 0.6;
				case N4:
					return 0.8;
				case REM:
					return 0.2;
				default:
					return 1;
				}
			case N1:
				switch (s.state) {
				case WACH:
					return 0.2;
				case N1:
					return 0;
				case N2:
					return 0.2;
				case N3:
					return 0.4;
				case N4:
					return 0.6;
				case REM:
					return 0.2;
				default:
					return 1;
				}
			case N2:
				switch (s.state) {
				case WACH:
					return 0.4;
				case N1:
					return 0.2;
				case N2:
					return 0;
				case N3:
					return 0.2;
				case N4:
					return 0.4;
				case REM:
					return 0.4;
				default:
					return 1;
				}
			case N3:
				switch (s.state) {
				case WACH:
					return 0.6;
				case N1:
					return 0.4;
				case N2:
					return 0.2;
				case N3:
					return 0;
				case N4:
					return 0.2;
				case REM:
					return 0.6;
				default:
					return 1;
				}
			case N4:
				switch (s.state) {
				case WACH:
					return 0.8;
				case N1:
					return 0.6;
				case N2:
					return 0.4;
				case N3:
					return 0.2;
				case N4:
					return 0;
				case REM:
					return 0.8;
				default:
					return 1;
				}
			case REM:
				switch (s.state) {
				case WACH:
					return 0.2;
				case N1:
					return 0.2;
				case N2:
					return 0.4;
				case N3:
					return 0.6;
				case N4:
					return 0.8;
				case REM:
					return 0;
				default:
					return 1;
				}

			default:
				return 1;
			}
		}
		return 1;
	}
	
	public boolean equals(Object o)
	{
		if(o == null)
			return false;
		if(!(o instanceof BedData))
			return false;
		
		BedData data = (BedData) o;
		
		return state.equals(data.state);
	}

	public String toShortString()
	{
		return state.toString();
	}
	
	public String toString()
	{
		return "State: " + state;
	}
	
	/**
	 * @return the state
	 */
	public SleepState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(SleepState state) {
		this.state = state;
	}

}
