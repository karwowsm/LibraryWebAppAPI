package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.BookBorrowing;
import com.testowanie_oprogramowania.library.entity.BookCopy;
import com.testowanie_oprogramowania.library.entity.User;
import com.testowanie_oprogramowania.library.error.BookBorrowingException;
import com.testowanie_oprogramowania.library.repository.BookBorrowingRepository;
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
    private BookBorrowingRepository bookBorrowingRepository;

    @Autowired
    private BookCopyService bookCopyService;

    @Autowired
    private UserService userService;

    private static Timestamp checkoutDate;

    private static Timestamp dueDate;

    @BeforeClass
    public static void initialize() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date parsedDate1 = dateFormat.parse("2018-12-12 12:12:12");
        checkoutDate = new java.sql.Timestamp(parsedDate1.getTime());

        Date parsedDate2 = dateFormat.parse("2018-12-15 14:22:11");
        dueDate = new java.sql.Timestamp(parsedDate2.getTime());
    }


    @Test
    public void borrowAvailableBook() throws ParseException {
        int borrowingCountBeforeBorrow = borrowingService.getAllBookborrowings().size();


        BookBorrowing bookBorrowing1 = new BookBorrowing(userService.getUserById((long) 1),
                bookCopyService.getBookcopyById((long) 1), checkoutDate, dueDate);

        borrowingService.borrowBook(bookBorrowing1);

        int borrowingCountAfterBorrow = borrowingService.getAllBookborrowings().size();

        assertEquals(borrowingCountBeforeBorrow + 1, borrowingCountAfterBorrow);

        for (BookBorrowing bs : borrowingService.getAllBookborrowings()) {
            System.out.println(bs.toString());
        }

        assertFalse(bookCopyService.getBookcopyById((long) 1).getBookAvailability());
    }

    @Test(expected = BookBorrowingException.class)
    public void borrowUnavailableBook() {
        BookBorrowing bookBorrowing1 = new BookBorrowing(userService.getUserById((long) 1),
                bookCopyService.getBookcopyById((long) 12), checkoutDate, dueDate);

        borrowingService.borrowBook(bookBorrowing1);
    }

    @Test(expected = BookBorrowingException.class)
    public void borrowBookUsingCheckoutDateHigherThanReturnDate() {
        BookBorrowing bookBorrowing1 = new BookBorrowing(userService.getUserById((long) 1),
                bookCopyService.getBookcopyById((long) 1), dueDate, checkoutDate);

        borrowingService.borrowBook(bookBorrowing1);
    }

    @Test(expected = BookBorrowingException.class)
    public void borrowBookUsingCheckoutDateEqualReturnDate() {
        BookBorrowing bookBorrowing1 = new BookBorrowing(userService.getUserById((long) 1),
                bookCopyService.getBookcopyById((long) 1), checkoutDate, checkoutDate);

        borrowingService.borrowBook(bookBorrowing1);
    }

    @Test(expected = BookBorrowingException.class)
    public void borrowBookUsingNonexistentBookCopyId() throws ParseException {
        BookCopy bookCopy = bookCopyService.getBookcopyById((long) 1);
        bookCopy.setId((long) 1000);

        BookBorrowing bookBorrowing1 = new BookBorrowing(userService.getUserById((long) 1),
                bookCopy, checkoutDate, dueDate);

        borrowingService.borrowBook(bookBorrowing1);
    }

    @Test(expected = BookBorrowingException.class)
    public void borrowBookUsingNonexistentUserId() throws ParseException {
        User user = userService.getUserById((long) 1);
        user.setId((long) 1000);

        BookBorrowing bookBorrowing1 = new BookBorrowing(user,
                bookCopyService.getBookcopyById((long) 1), checkoutDate, dueDate);

        borrowingService.borrowBook(bookBorrowing1);
    }

    @Test(expected = BookBorrowingException.class)
    public void borrowBookUsingDatesFromPast() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date parsedDate1 = dateFormat.parse("1889-12-12 12:12:12");
        Timestamp chDate = new java.sql.Timestamp(parsedDate1.getTime());

        Date parsedDate2 = dateFormat.parse("1889-12-15 14:22:11");
        Timestamp duDate = new java.sql.Timestamp(parsedDate2.getTime());

        BookBorrowing bookBorrowing1 = new BookBorrowing(userService.getUserById((long) 1),
                bookCopyService.getBookcopyById((long) 1), chDate, duDate);

        borrowingService.borrowBook(bookBorrowing1);
    }

    @Test
    public void returnBook() throws ParseException {
        BookBorrowing bookBorrowing1 = new BookBorrowing(userService.getUserById((long) 1),
                bookCopyService.getBookcopyById((long) 1), checkoutDate, dueDate);

        borrowingService.borrowBook(bookBorrowing1);

        assertFalse(bookCopyService.getBookcopyById((long) 1).getBookAvailability());

        borrowingService.returnBook((long) 1);
        assertTrue(bookCopyService.getBookcopyById((long) 1).getBookAvailability());

        assertTrue(borrowingService.getBookborrowingById(borrowingService.getAllBookborrowings().get(0).getId()).getDueDate() != null);
    }

    @Test(expected = BookBorrowingException.class)
    public void tryReturnAvailableBook() {
        borrowingService.returnBook((long) 3);
    }

    @Test
    public void getAllBookborrowings() {
        bookBorrowingRepository.deleteAll();

        BookBorrowing bookBorrowing1 = new BookBorrowing(userService.getUserById((long) 1),
                bookCopyService.getBookcopyById((long) 1), checkoutDate, dueDate);

        BookBorrowing bookBorrowing2 = new BookBorrowing(userService.getUserById((long) 1),
                bookCopyService.getBookcopyById((long) 5), checkoutDate, dueDate);

        BookBorrowing bookBorrowing3 = new BookBorrowing(userService.getUserById((long) 1),
                bookCopyService.getBookcopyById((long) 7), checkoutDate, dueDate);

        borrowingService.borrowBook(bookBorrowing1);
        borrowingService.borrowBook(bookBorrowing2);
        borrowingService.borrowBook(bookBorrowing3);

        borrowingService.getAllBookborrowings();

        assertEquals(3, borrowingService.getAllBookborrowings().size());
    }

}