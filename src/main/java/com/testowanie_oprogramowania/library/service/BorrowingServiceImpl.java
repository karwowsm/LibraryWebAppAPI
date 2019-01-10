package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.BookBorrowing;
import com.testowanie_oprogramowania.library.entity.BookCopy;
import com.testowanie_oprogramowania.library.error.BookBorrowingException;
import com.testowanie_oprogramowania.library.repository.BookBorrowingRepository;
import com.testowanie_oprogramowania.library.repository.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service("BorrowingServiceImpl")
public class BorrowingServiceImpl implements BorrowingService {

    @Autowired
    private BookBorrowingRepository bookBorrowingRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookCopyService bookCopyService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void borrowBook(BookBorrowing bookBorrowing) throws BookBorrowingException {

        Timestamp actualTimestamp = new Timestamp(System.currentTimeMillis());

        if (bookBorrowing.getCheckoutDate().after(bookBorrowing.getDueDate()) ||
                bookBorrowing.getCheckoutDate().getTime() == bookBorrowing.getDueDate().getTime()) {
            throw new BookBorrowingException("Book due date have to be bigger than checkout date");
        }
        if (bookBorrowing.getCheckoutDate().getTime() < actualTimestamp.getTime() ||
                bookBorrowing.getDueDate().getTime() < actualTimestamp.getTime()) {
            throw new BookBorrowingException("Book due date and checkout date have to be bigger than actual date");
        }
        if (bookCopyService.getBookcopyById(bookBorrowing.getBookCopy().getId()) == null) {
            throw new BookBorrowingException("Book copy with given id doesn't exist");
        }
        if (userService.getUserById(bookBorrowing.getUser().getId()) == null) {
            throw new BookBorrowingException("User with given id doesn't exist");
        }
        if (bookCopyService.getBookcopyById(bookBorrowing.getBookCopy().getId()).getBookAvailability() == false) {
            throw new BookBorrowingException("Cannot borrow unavailable book copy");
        }

        bookBorrowingRepository.save(bookBorrowing);

        BookCopy bookcopy = bookCopyService.getBookcopyById(bookBorrowing.getBookCopy().getId());
        bookcopy.setBookAvailability(false);

        bookCopyRepository.save(bookcopy);
    }

    @Override
    @Transactional
    public void returnBook(Long bookCopyId) throws BookBorrowingException {
        BookCopy bookCopy = bookCopyService.getBookcopyById(bookCopyId);

        if (bookCopy.getBookAvailability() == true) {
            throw new BookBorrowingException("Cannot return available book copy");
        }

        bookCopy.setBookAvailability(true);
        bookCopyRepository.save(bookCopy);

        BookBorrowing bookBorrowing = bookBorrowingRepository.getNotReturnedBookBorrowingByBookCopyId(bookCopyId);

        if (bookBorrowing.getReturnDate() != null) {
            throw new BookBorrowingException("Cannot return book, which was returned earlier");
        }

        bookBorrowing.setReturnDate(new Timestamp(System.currentTimeMillis()));

        bookBorrowingRepository.save(bookBorrowing);
    }

    @Override
    public List<BookBorrowing> getAllBookborrowings() {
        return (List<BookBorrowing>) bookBorrowingRepository.findAll();
    }

    @Override
    public BookBorrowing getBookborrowingById(Long bookCopyId) {
        return bookBorrowingRepository.findById(bookCopyId).orElse(null);
    }
}
