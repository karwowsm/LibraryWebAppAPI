package com.testowanie_oprogramowania.library.controller;

import com.testowanie_oprogramowania.library.entity.Book;
import com.testowanie_oprogramowania.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Mateusz
 */
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity getBook(@PathVariable("id") Long id) {
        Book book = bookService.getBook(id);
        if (book != null) {
            return new ResponseEntity(book, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/copies")
    public ResponseEntity getCopies(@PathVariable("id") Long id) {
        Book book = bookService.getBook(id);
        if (book != null) {
            return new ResponseEntity(bookService.getCopies(id), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Book book = bookService.getBook(id);
        if (book != null) {
            bookService.deleteBookById(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}")
    public ResponseEntity updateBook(@RequestBody Book book) {
        Book book1 = bookService.getBook(book.getId());
        if (book1 != null) {
            bookService.updateBook(book);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }

    @GetMapping("/search")
    public List<Book> searchBook(@RequestParam("q") String q) {
        return bookService.searchBook(q);
    }

}
