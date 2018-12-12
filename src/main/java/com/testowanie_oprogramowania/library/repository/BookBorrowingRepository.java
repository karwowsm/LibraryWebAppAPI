package com.testowanie_oprogramowania.library.repository;

import com.testowanie_oprogramowania.library.entity.BookBorrowing;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Mateusz
 */
public interface BookBorrowingRepository extends CrudRepository<BookBorrowing, Long> {

    List<BookBorrowing> findByUserId(Long userId);

    @Query("SELECT bb FROM BookBorrowing bb WHERE bb.bookCopy.id=:bookCopyId AND bb.returnDate IS NULL")
    BookBorrowing getNotReturnedBookBorrowingByBookCopyId(Long bookCopyId);
}
