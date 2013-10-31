package org.livingplace.activitylearning.data;

public class DoorData extends Data{

	private String name;
	private String description;
	
	public DoorData(String data)
	{
		super();
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		name =strarr[1];
		description = strarr[2];
		
	}
	
	public DoorData(String name, String description, long time)
	{
		this.name = name;
		this.description = description;
		this.time = time;
	}
	
	public double distanceTo(IData data) {
		if(data == this)
			return 0;
		if(data instanceof DoorData)
		{
			DoorData d = (DoorData) data;
			if(name.equals(d.name))
			{
				if(description.equals(d.description))
				{
					return 0;
				}
				else
				{
					return 0.5;
				}
			}
			else return 1;
		}
		return 1;
	}
	
	public boolean equals(Object o)
	{
		if(o == null)
			return false;
		if(!(o instanceof DoorData))
			return false;
		
		DoorData data = (DoorData) o;
		
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
