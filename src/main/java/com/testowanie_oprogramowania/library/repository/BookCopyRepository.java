package com.testowanie_oprogramowania.library.repository;

import com.testowanie_oprogramowania.library.entity.BookCopy;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Mateusz
 */
public interface BookCopyRepository extends CrudRepository<BookCopy, Long> {

    List<BookCopy> findByBookId(Long bookId);
}
