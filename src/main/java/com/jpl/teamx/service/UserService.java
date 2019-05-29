package com.jpl.teamx.service;

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

    public User createUser(String fullName,
                           String email,
                           String profileUrl,
                           String imageUrl){
        User user = new User(fullName, email,
                profileUrl, imageUrl);
        return userRepository.save(user);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getUser(Long id){
        return userRepository.findById(id)
                .orElse(null);
    }

    public Collection<User> getAllUsers(){
        return userRepository.findAll();
    }

    /*Sends email from current user to admin.
    * If it cannot send the email it throws an Exception.
    * More details soon ...*/
    public void sendEmail(User currentUser,
                          User admin,
                          String message) throws Exception{

    }

}
