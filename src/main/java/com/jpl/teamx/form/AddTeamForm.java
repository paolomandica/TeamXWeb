package com.jpl.teamx.form;



import com.jpl.teamx.model.User;

public class AddTeamForm {
	private User admin;
	private String name;
	
	private String description;
	
	private String location;
	
	public AddTeamForm() {
		
	}
	
	public AddTeamForm(User admin, String name, String description, String location) {
		this.admin = admin;
		this.name = name;
		this.description = description;
		this.location = location;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
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
