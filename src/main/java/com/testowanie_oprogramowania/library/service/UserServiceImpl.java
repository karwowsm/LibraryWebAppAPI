package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.User;
import com.testowanie_oprogramowania.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
