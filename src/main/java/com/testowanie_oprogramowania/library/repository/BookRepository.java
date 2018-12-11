package com.testowanie_oprogramowania.library.repository;

import com.testowanie_oprogramowania.library.entity.Book;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Mateusz
 */
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByNameContaining(String name);
}
