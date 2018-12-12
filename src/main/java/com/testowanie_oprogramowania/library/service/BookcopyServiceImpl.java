package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.BookCopy;
import com.testowanie_oprogramowania.library.repository.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCopyServiceImpl implements BookCopyService {

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Override
    public BookCopy getBookcopyById(Long id) {
        return bookCopyRepository.findById(id).orElse(null);
    }
}
