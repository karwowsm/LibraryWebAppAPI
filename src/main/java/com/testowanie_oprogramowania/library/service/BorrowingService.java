package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.BookBorrowing;
import com.testowanie_oprogramowania.library.error.BookBorrowingException;

import java.util.List;

public interface BorrowingService {

    void borrowBook(BookBorrowing bookborrowing) throws BookBorrowingException;

    void returnBook(Long bookCopyId) throws BookBorrowingException;

    List<BookBorrowing> getAllBookborrowings();

    BookBorrowing getBookborrowingById(Long bookCopyId);

}
