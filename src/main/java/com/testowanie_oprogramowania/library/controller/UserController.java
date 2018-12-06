package com.testowanie_oprogramowania.library.controller;

import com.testowanie_oprogramowania.library.entity.User;
import com.testowanie_oprogramowania.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

}
