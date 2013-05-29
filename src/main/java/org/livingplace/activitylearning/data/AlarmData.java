package org.livingplace.activitylearning.data;

public class AlarmData  extends Data{

	private String name;
	private String description;
	
	public AlarmData(String data)
	{
		String strarr[] = data.split(";");
		time = Long.valueOf(strarr[0]);
		name = strarr[1];
		description = strarr[2];
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
