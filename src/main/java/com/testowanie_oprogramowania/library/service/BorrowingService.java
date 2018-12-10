package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.Bookborrowing;
import com.testowanie_oprogramowania.library.error.BookBorrowingException;

import java.util.List;

public interface BorrowingService {

    void borrowBook(Bookborrowing bookborrowing) throws BookBorrowingException;

    void returnBook(Long bookCopyId) throws BookBorrowingException;

    List<Bookborrowing> getAllBookborrowings();

    Bookborrowing getBookborrowingById(Long bookCopyId);

}
