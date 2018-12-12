package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.Book;
import com.testowanie_oprogramowania.library.entity.BookCopy;
import com.testowanie_oprogramowania.library.repository.BookCopyRepository;
import com.testowanie_oprogramowania.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Override
    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public List<Book> searchBook(String q) {
        return bookRepository.findByNameContaining(q);
    }

    @Override
    public List<BookCopy> getCopies(Long id) {
        return bookCopyRepository.findByBookId(id);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addBook(Book book) {
        bookRepository.save(book);
    }
}
