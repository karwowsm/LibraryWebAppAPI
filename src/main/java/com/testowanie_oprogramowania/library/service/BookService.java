package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.Book;
import java.util.List;

/**
 *
 * @author Mateusz
 */
public interface BookService {

    List<Book> getAllBooks();

    Book getBook(Long id);
}
