package com.jpl.teamx.model;

import javax.persistence.*;

@Entity
@Table(name = "Teams")
public class Team {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private User admin;
	
	@Column(nullable = false, length = 25)
	private String name;
	
	@Column
	private String description;
	
	@Column(length = 50)
	private String location;
	
	@Column
	private String urlImage;

	public Team() {}
	
	public Team(User admin, String name, String description, String location,String urlImage) {
		this.admin = admin;
		this.name = name;
		this.description = description;
		this.location = location;
		this.urlImage = urlImage;
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

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
}