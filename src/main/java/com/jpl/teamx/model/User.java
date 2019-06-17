package com.jpl.teamx.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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
    @Column(length = 2000)
    private String description;

    @OneToMany(mappedBy = "admin",
    cascade = {CascadeType.PERSIST,
            CascadeType.REMOVE})
    private List<Team> createdTeams;

    @ManyToMany(mappedBy = "followers")
    private List<Team> followingTeams;

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

    @Override
    public boolean equals(Object o) {
        User that = (User) o;
        return that.getEmail().equals(this.email);
    }

    @Override
    public int hashCode() {
        return this.email.hashCode();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Team> getCreatedTeams() {
        return createdTeams;
    }

    public List<Team> getFollowingTeams() {
        return followingTeams;
    }

}