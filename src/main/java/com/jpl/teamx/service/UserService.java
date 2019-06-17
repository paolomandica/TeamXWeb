package com.jpl.teamx.service;

import com.jpl.teamx.model.Team;
import com.jpl.teamx.model.User;
import com.jpl.teamx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getUser(String id){
        return userRepository.findById(id)
                .orElse(null);
    }

    public Collection<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void updateUser(User user){
        userRepository.save(user);
    }

}
