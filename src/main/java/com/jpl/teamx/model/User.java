package com.jpl.teamx.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String email;
    @Column
    private String profileUrl;
    @Column
    private String imageUrl;
    @OneToMany(mappedBy = "admin",
    cascade = {CascadeType.PERSIST,
            CascadeType.REMOVE})
    @Column
    private List<Team> createdTeams;

    public User(String fullName,
                String email,
                String profileUrl,
                String imageUrl) {
        this.fullName = fullName;
        this.email = email;
        this.profileUrl = profileUrl;
        this.imageUrl = imageUrl;
    }


    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Team> getCreatedTeams() {
        return createdTeams;
    }

}