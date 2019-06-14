package com.jpl.teamx.repository;


import com.jpl.teamx.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface UserRepository
extends CrudRepository<User, String> {
    public User findByEmail(String email);
    public User findByName(String name);
    public Collection<User> findAll();
}
