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
    private String name;
    @Column(nullable = false)
    private String email;
    @Column
    private String imageUrl;
    @OneToMany(mappedBy = "admin",
    cascade = {CascadeType.PERSIST,
            CascadeType.REMOVE})
    private List<Team> createdTeams;
    
    public User(){}
    
    public User(String name,
                String email,
                String imageUrl) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "User "+this.email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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