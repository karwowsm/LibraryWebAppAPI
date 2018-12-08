package com.testowanie_oprogramowania.library.repository;

import com.testowanie_oprogramowania.library.entity.Author;
import com.testowanie_oprogramowania.library.entity.Book;
import com.testowanie_oprogramowania.library.entity.Category;
import com.testowanie_oprogramowania.library.entity.Publisher;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Mateusz
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = NONE)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testDeleteAll() {
        bookRepository.deleteAll();
        assertEquals(bookRepository.count(), 0);
    }

    @Test
    public void testDeleteById() {
        Long id = new Long(1);
        bookRepository.deleteById(id);
        assertEquals(bookRepository.existsById(id), false);
    }

    @Test
    public void testFindAll() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        assertEquals(books.size(), 15);
    }

    @Test
    public void testSave() {
        Book newBook = new Book();
        Author author = new Author();
        author.setId(new Long(6));
        newBook.setAuthor(author);
        Publisher publisher = new Publisher();
        publisher.setId(new Long(7));
        newBook.setPublisher(publisher);
        Category category = new Category();
        category.setId(new Long(4));
        newBook.setCategory(category);
        newBook.setName("Muzyka duszy");
        newBook.setPublishDate(1994);
        bookRepository.save(newBook);
        assertEquals(bookRepository.count(), 16);
    }

}
