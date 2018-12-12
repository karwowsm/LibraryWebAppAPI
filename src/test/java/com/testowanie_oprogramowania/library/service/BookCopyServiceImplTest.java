package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.BookCopy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookCopyServiceImplTest {

    @Autowired
    BookCopyService bookCopyService;

    @Autowired
    BookService bookService;

    @Test
    public void getBookcopyById() {
        BookCopy bookcopy = new BookCopy();
        bookcopy.setId((long) 1);
        bookcopy.setBook(bookService.getBook((long) 1));
        bookcopy.setBookAvailability(true);

        assertEquals(bookcopy, bookCopyService.getBookcopyById((long) 1));
    }
}