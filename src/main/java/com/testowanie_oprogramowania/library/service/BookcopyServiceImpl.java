package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.Bookcopy;
import com.testowanie_oprogramowania.library.repository.BookcopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookcopyServiceImpl implements BookcopyService {

    @Autowired
    private BookcopyRepository bookcopyRepository;

    @Override
    public Bookcopy getBookcopyById(Long id) {
        return bookcopyRepository.findById(id).orElse(null);
    }
}
