package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.BookBorrowing;
import com.testowanie_oprogramowania.library.entity.User;
import com.testowanie_oprogramowania.library.repository.BookBorrowingRepository;
import com.testowanie_oprogramowania.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookBorrowingRepository bookBorrowingRepository;

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<BookBorrowing> getBorrowings(Long id) {
        return bookBorrowingRepository.findByUserId(id);
    }

}
