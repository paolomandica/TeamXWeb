package com.jpl.teamx.model;

import javax.persistence.*;

@Entity
@Table(name = "Teams")
public class Team {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	
	@ManyToOne
	private User admin;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@Column
	private String location;
	
	public Team(User admin, String name, String description, String location) {
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

	public Long getId() {
		return id;
	}
}