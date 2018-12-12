package com.testowanie_oprogramowania.library.repository;

import com.testowanie_oprogramowania.library.entity.Author;
import com.testowanie_oprogramowania.library.entity.Book;
import com.testowanie_oprogramowania.library.entity.BookCopy;
import com.testowanie_oprogramowania.library.entity.Category;
import com.testowanie_oprogramowania.library.entity.Publisher;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
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
public class BookCopyRepositoryTest {

    @Autowired
    private BookCopyRepository bookCopyRepository;

    private static Book bookIstance;

    @BeforeClass
    public static void setUp() {
        bookIstance = new Book();
        bookIstance.setId(new Long(1));
        Author author = new Author();
        author.setId(new Long(1));
        author.setName("Stephen");
        author.setSurname("King");
        bookIstance.setAuthor(author);
        Publisher publisher = new Publisher();
        publisher.setId(new Long(1));
        publisher.setName("Iskry");
        bookIstance.setPublisher(publisher);
        Category category = new Category();
        category.setId(new Long(1));
        category.setName("horror");
        bookIstance.setCategory(category);
        bookIstance.setName("Lśnienie");
        bookIstance.setPublishDate(1977);
    }

    @Test
    public void testFindAll() {
        List<BookCopy> bookCopies = (List<BookCopy>) bookCopyRepository.findAll();
        assertEquals(15, bookCopies.size());
    }

    @Test
    public void testFindById() {
        BookCopy expectedBookCopy = new BookCopy();
        Long bookCopyId = new Long(1);
        expectedBookCopy.setId(bookCopyId);
        expectedBookCopy.setBook(bookIstance);
        expectedBookCopy.setBookAvailability(Boolean.TRUE);
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId).orElse(null);
        assertEquals(expectedBookCopy, bookCopy);
    }

    @Test
    public void testFindByBookId() {
        BookCopy expectedBookCopy1 = new BookCopy();
        expectedBookCopy1.setId(new Long(1));
        expectedBookCopy1.setBook(bookIstance);
        expectedBookCopy1.setBookAvailability(Boolean.TRUE);
        BookCopy expectedBookCopy2 = new BookCopy();
        expectedBookCopy2.setId(new Long(2));
        expectedBookCopy2.setBook(bookIstance);
        expectedBookCopy2.setBookAvailability(Boolean.FALSE);
        List<BookCopy> expectedBookCopies = new ArrayList();
        expectedBookCopies.add(expectedBookCopy1);
        expectedBookCopies.add(expectedBookCopy2);
        List<BookCopy> bookCopies = bookCopyRepository.findByBookId(bookIstance.getId());
        assertEquals(expectedBookCopies, bookCopies);
    }

}