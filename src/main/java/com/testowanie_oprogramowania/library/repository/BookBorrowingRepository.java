package com.testowanie_oprogramowania.library.repository;

import com.testowanie_oprogramowania.library.entity.BookBorrowing;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Mateusz
 */
public interface BookBorrowingRepository extends CrudRepository<BookBorrowing, Long> {

    List<BookBorrowing> findByUserId(Long userId);
}
