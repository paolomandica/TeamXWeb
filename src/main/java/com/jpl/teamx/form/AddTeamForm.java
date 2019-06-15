package com.jpl.teamx.form;



import com.jpl.teamx.model.User;

import javax.validation.constraints.Size;

public class AddTeamForm {

	@Size(min = 3, max = 25)
	private String name;

	@Size(max = 255)
	private String description;

	@Size(max = 50)
	private String location;
	
	
	public AddTeamForm() {
		
	}
	
	public AddTeamForm(String name, String description, String location) {
		this.name = name;
		this.description = description;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}



}
