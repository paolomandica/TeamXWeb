package com.jpl.teamx.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column
    private String pictureUrl;
    @Column
    private String profileUrl;

    @OneToMany(mappedBy = "admin",
    cascade = {CascadeType.PERSIST,
            CascadeType.REMOVE})
    private List<Team> createdTeams;

    public User() {
    }

    @JsonCreator
    public User(@JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("email") String email,
                @JsonProperty("picture") String picture,
                @JsonProperty("profile") String profile) {
        this.id = id;
        this.name = name;
        this.pictureUrl = picture;
        this.email = email;
        this.profileUrl = profile;
    }


    public String getId() {
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String imageUrl) {
        this.pictureUrl = imageUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public List<Team> getCreatedTeams() {
        return createdTeams;
    }

}