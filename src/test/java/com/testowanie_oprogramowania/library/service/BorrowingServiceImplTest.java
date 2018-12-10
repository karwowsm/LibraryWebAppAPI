package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.Bookborrowing;
import com.testowanie_oprogramowania.library.entity.Bookcopy;
import com.testowanie_oprogramowania.library.error.BookBorrowingException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BorrowingServiceImplTest {

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private BookcopyService bookcopyService;

    private static Timestamp checkoutDate;

    private static Timestamp returnDate;

    @BeforeClass
    public static void initialize() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date parsedDate1 = dateFormat.parse("2018-12-12 12:12:12");
        checkoutDate = new java.sql.Timestamp(parsedDate1.getTime());

        Date parsedDate2 = dateFormat.parse("2018-12-15 14:22:11");
        returnDate = new java.sql.Timestamp(parsedDate2.getTime());
    }


    @Test
    public void borrowAvailableBook() throws ParseException {
        int borrowingCountBeforeBorrow = borrowingService.getAllBookborrowings().size();

        borrowingService.borrowBook(new Bookborrowing(checkoutDate, returnDate, (long) 1, (long) 1));

        int borrowingCountAfterBorrow = borrowingService.getAllBookborrowings().size();

        assertEquals(borrowingCountBeforeBorrow + 1, borrowingCountAfterBorrow);

        System.out.println(bookcopyService.getBookcopyById((long) 1).toString());

        for (Bookborrowing bs : borrowingService.getAllBookborrowings()) {
            System.out.println(bs.toString());
        }

        assertFalse(bookcopyService.getBookcopyById((long) 1).getBookAvailability());
    }

    @Test(expected = BookBorrowingException.class)
    public void borrowUnavailableBook() {
        borrowingService.borrowBook(new Bookborrowing(checkoutDate, returnDate, (long) 1, (long) 10));
    }

    @Test(expected = BookBorrowingException.class)
    public void borrowBookUsingCheckoutDateHigherThanReturnDate() {
        borrowingService.borrowBook(new Bookborrowing(returnDate, checkoutDate, (long) 1, (long) 1));
    }

    @Test(expected = BookBorrowingException.class)
    public void borrowBookUsingCheckoutDateEqualReturnDate() {
        borrowingService.borrowBook(new Bookborrowing(checkoutDate, checkoutDate, (long) 1, (long) 1));
    }

    @Test(expected = BookBorrowingException.class)
    public void borrowBookUsingNonexistentBookCopyId() throws ParseException {
        borrowingService.borrowBook(new Bookborrowing(checkoutDate, returnDate, (long) 1, (long) 10000));
    }

    @Test(expected = BookBorrowingException.class)
    public void borrowBookUsingNonexistentUserId() throws ParseException {
        borrowingService.borrowBook(new Bookborrowing(checkoutDate, returnDate, (long) 10000, (long) 1));
    }

    @Test
    public void returnBook() throws ParseException {
        borrowingService.borrowBook(new Bookborrowing(checkoutDate, returnDate, (long) 1, (long) 1));
        assertFalse(bookcopyService.getBookcopyById((long) 1).getBookAvailability());

        borrowingService.returnBook((long) 1);
        assertTrue(bookcopyService.getBookcopyById((long) 1).getBookAvailability());

        assertTrue(borrowingService.getBookborrowingById(borrowingService.getAllBookborrowings().get(0).getId()).getDueDate() != null);
    }

    @Test(expected = BookBorrowingException.class)
    public void tryReturnAvailableBook() {
        borrowingService.returnBook((long) 3);
    }

    @Test
    public void getAllBookborrowings() {
        borrowingService.borrowBook(new Bookborrowing(checkoutDate, returnDate, (long) 1, (long) 1));
        borrowingService.borrowBook(new Bookborrowing(checkoutDate, returnDate, (long) 1, (long) 2));
        borrowingService.borrowBook(new Bookborrowing(checkoutDate, returnDate, (long) 1, (long) 3));

        borrowingService.getAllBookborrowings();

        assertEquals(3, borrowingService.getAllBookborrowings().size());
    }

}