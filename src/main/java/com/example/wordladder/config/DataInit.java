package com.example.wordladder.config;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wordladder.entity.User;
import com.example.wordladder.repository.UserRepository;

@Service
public class DataInit {

    @Autowired UserRepository userRepository;

    @PostConstruct
    public void dataInit(){

        User user = new User();
        user.setPassword("123456");
        user.setUsername("user");
        user.setRole(User.ROLE.user);
        userRepository.save(user);
    }

}