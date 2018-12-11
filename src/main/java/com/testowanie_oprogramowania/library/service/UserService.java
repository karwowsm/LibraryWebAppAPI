package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.BookBorrowing;
import com.testowanie_oprogramowania.library.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUser(Long id);

    void addUser(User user);

    User getUserById(Long id);

    User getUserByLogin(String login);

    List<BookBorrowing> getBorrowings(Long id);
}
