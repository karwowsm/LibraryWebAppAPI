package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.BookBorrowing;
import com.testowanie_oprogramowania.library.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUser(Long id);

    List<BookBorrowing> getBorrowings(Long id);

    User getUserById(Long id);

}
