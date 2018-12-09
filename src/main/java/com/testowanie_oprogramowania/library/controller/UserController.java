package com.testowanie_oprogramowania.library.controller;

import com.testowanie_oprogramowania.library.entity.User;
import com.testowanie_oprogramowania.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}/borrowings")
    public ResponseEntity getBorrowings(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        if (user != null) {
            return new ResponseEntity(userService.getBorrowings(id), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
