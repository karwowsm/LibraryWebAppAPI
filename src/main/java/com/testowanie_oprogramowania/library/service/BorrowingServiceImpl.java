package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.Bookborrowing;
import com.testowanie_oprogramowania.library.entity.Bookcopy;
import com.testowanie_oprogramowania.library.error.BookBorrowingException;
import com.testowanie_oprogramowania.library.repository.BookborrowingRepository;
import com.testowanie_oprogramowania.library.repository.BookcopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
public class BorrowingServiceImpl implements BorrowingService {

    @Autowired
    private BookborrowingRepository bookborrowingRepository;

    @Autowired
    private BookcopyRepository bookcopyRepository;

    @Autowired
    private BookcopyService bookcopyService;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void borrowBook(Bookborrowing bookborrowing) throws BookBorrowingException {
        if (bookborrowing.getCheckoutDate().after(bookborrowing.getReturnDate()) ||
                bookborrowing.getCheckoutDate().getTime() == bookborrowing.getReturnDate().getTime()) {
            throw new BookBorrowingException("Book return date have to be bigger than checkout date");
        }
        if (bookcopyService.getBookcopyById(bookborrowing.getBookCopyId()) == null) {
            throw new BookBorrowingException("Book copy with given id doesn't exist");
        }
        if (userService.getUserById(bookborrowing.getUserId()) == null) {
            throw new BookBorrowingException("User with given id doesn't exist");
        }
        if (bookcopyService.getBookcopyById(bookborrowing.getBookCopyId()).getBookAvailability() == false) {
            throw new BookBorrowingException("Cannot borrow unavailable book copy");
        }

        bookborrowingRepository.save(bookborrowing);

        Bookcopy bookcopy = bookcopyService.getBookcopyById(bookborrowing.getBookCopyId());
        bookcopy.setBookAvailability(false);

        bookcopyRepository.save(bookcopy);
    }

    @Override
    @Transactional
    public void returnBook(Long bookCopyId) throws BookBorrowingException {
        Bookcopy bookcopy = bookcopyService.getBookcopyById(bookCopyId);

        if (bookcopy.getBookAvailability() == true) {
            throw new BookBorrowingException("Cannot return available book copy");
        }

        bookcopy.setBookAvailability(true);
        bookcopyRepository.save(bookcopy);

        Bookborrowing bookborrowing = bookborrowingRepository.getNotReturnedBookBorrowingByBookCopyId(bookCopyId);

        if (bookborrowing.getDueDate() != null) {
            throw new BookBorrowingException("Cannot return book, which was returned earlier");
        }

        bookborrowing.setDueDate(new Timestamp(System.currentTimeMillis()));

        bookborrowingRepository.save(bookborrowing);
    }

    @Override
    public List<Bookborrowing> getAllBookborrowings() {
        return bookborrowingRepository.findAll();
    }

    @Override
    public Bookborrowing getBookborrowingById(Long bookCopyId) {
        return bookborrowingRepository.findById(bookCopyId).orElse(null);
    }
}
