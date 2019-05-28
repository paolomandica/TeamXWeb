package com.jpl.teamx.repository;


import com.jpl.teamx.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface UserRepository
extends CrudRepository<User, Long> {
    public User findByEmail(String email);
    public User findByFullName(String fullName);
    public Collection<User> findAll();
}
