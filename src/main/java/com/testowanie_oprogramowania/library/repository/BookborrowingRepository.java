package com.testowanie_oprogramowania.library.repository;

import com.testowanie_oprogramowania.library.entity.Bookborrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;

public interface BookborrowingRepository extends JpaRepository<Bookborrowing, Long> {

    @Query("SELECT bb FROM Bookborrowing bb WHERE bb.bookCopyId=:bookCopyId AND bb.dueDate IS NULL")
    Bookborrowing getNotReturnedBookBorrowingByBookCopyId(Long bookCopyId);

}
