package org.livingplace.activitylearning.data;

public class DoorData extends Data{

	private String name;
	private String description;
	
	public DoorData(String data)
	{
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		name =strarr[1];
		description = strarr[2];
		
	}
	
	public double distanceTo(IData data) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String toShortString() {
		// TODO Auto-generated method stub
		return null;
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
