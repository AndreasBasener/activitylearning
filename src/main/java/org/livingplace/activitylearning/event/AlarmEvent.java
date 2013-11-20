package org.livingplace.activitylearning.event;

public class AlarmEvent  extends Event{

	private String name;
	private String description;
	
	public AlarmEvent(String data)
	{
		super();
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		name = strarr[1];
		description = strarr[2];
	}
	public AlarmEvent(String name, String description, long time)
	{
		this.name = name;
		this.description = description;
		this.time = time;
	}

	/**
	 * Berechnet die Distanz zwischen diesem <code>AlarmData</code>-Objekts und data.
	 * Wenn <code>name</code> und <code>description</code> gleich sind, dann wird 0 zurückgegeben,
	 * wenn die namen Überinstimmen, aber die disriptions verschieden sind, wird 0.5 zurckgegeben.
	 * Bei Verschiedenen Objekten wird 1 zurückgegeben. </br>
	 * Ist <code>data</code> keine Instanz von <code>Alarmdata</code> wird -1 zurückgegeben.
	 * 
	 * @param event das zu vergleichende Objekt
	 * @return Distanz zum übergeben Objekt.
	 */
	public double distanceTo(IEvent event) {
		if(event == this) // data ist this
			return 0;
		
		if(event instanceof AlarmEvent)
		{
			AlarmEvent d = (AlarmEvent) event;
			
			if(name.equals(d.name)) // beide namen sind gleich
			{
				if(description.equals(d.description)) // beide name und descriptions sind gleich
				{
					return 0;
				}
				else // descriptions sind unterschiedlich, aber name sind gleich
				{
					return 0.5;
				}
			}
			else // beide namen sind unterschiedlich
			{
				return 1;
			}
		}
		return 1; // data ist keine Instanz von AlarmData
	}
	
	public boolean equals(Object o)
	{
		if(o == null)
			return false;
		if(!(o instanceof AlarmEvent))
			return false;
		
		AlarmEvent data = (AlarmEvent) o;
		
		return name.equals(data.name) &&
				description.equals(data.description);
	}

	public String toShortString()
	{
		return name + ":" + description;
	}
	
	public String toString()
	{
		return "Name: " + name + " Description: " + description;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	

}
