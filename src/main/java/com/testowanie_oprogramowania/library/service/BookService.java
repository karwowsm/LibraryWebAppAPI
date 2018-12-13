package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.Book;
import com.testowanie_oprogramowania.library.entity.BookCopy;
import java.util.List;

/**
 *
 * @author Mateusz
 */
public interface BookService {

    List<Book> getAllBooks();

    Book getBook(Long id);

    List<Book> searchBook(String q);

    List<BookCopy> getCopies(Long id);

    void deleteBookById(Long id);

    void addBook(Book book);

    void updateBook(Book book);
}
