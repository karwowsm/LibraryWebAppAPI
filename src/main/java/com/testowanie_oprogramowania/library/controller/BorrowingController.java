package com.testowanie_oprogramowania.library.controller;

import com.testowanie_oprogramowania.library.entity.Bookborrowing;
import com.testowanie_oprogramowania.library.error.BookBorrowingException;
import com.testowanie_oprogramowania.library.service.BookcopyService;
import com.testowanie_oprogramowania.library.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;


    @Autowired
    private BookcopyService bookcopyService;

    @PostMapping(value = "/borrow")
    public ResponseEntity borrowBook(@RequestBody Bookborrowing bookborrowing) {
        try {
            borrowingService.borrowBook(bookborrowing);
            return new ResponseEntity(HttpStatus.OK);
        } catch (BookBorrowingException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/return/{bookCopyId}")
    public ResponseEntity returnBook(@PathVariable Long bookCopyId) {
        try {
            borrowingService.returnBook(bookCopyId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (BookBorrowingException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/borrowings")
    public List<Bookborrowing> getAllBookBorrowing() {
        return borrowingService.getAllBookborrowings();
    }

}
