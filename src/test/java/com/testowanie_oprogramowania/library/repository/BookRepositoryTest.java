package com.testowanie_oprogramowania.library.repository;

import com.testowanie_oprogramowania.library.entity.Author;
import com.testowanie_oprogramowania.library.entity.Book;
import com.testowanie_oprogramowania.library.entity.Category;
import com.testowanie_oprogramowania.library.entity.Publisher;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private static List<Book> harryPotterBooks;

    @BeforeClass
    public static void setUp() {
        Book harryPotterBook1 = new Book();
        harryPotterBook1.setId(new Long(11));
        Author harryPotterAuthor = new Author();
        harryPotterAuthor.setId(new Long(4));
        harryPotterAuthor.setName("J.K.");
        harryPotterAuthor.setSurname("Rowling");
        harryPotterBook1.setAuthor(harryPotterAuthor);
        Publisher harryPotterPublisher = new Publisher();
        harryPotterPublisher.setId(new Long(5));
        harryPotterPublisher.setName("Media Rodzina");
        harryPotterBook1.setPublisher(harryPotterPublisher);
        Category harryPotterCategory = new Category();
        harryPotterCategory.setId(new Long(4));
        harryPotterCategory.setName("fantasy");
        harryPotterBook1.setCategory(harryPotterCategory);
        harryPotterBook1.setName("Harry Potter i Komnata Tajemnic");
        harryPotterBook1.setPublishDate(1998);

        Book harryPotterBook2 = new Book();
        harryPotterBook2.setId(new Long(12));
        harryPotterBook2.setAuthor(harryPotterAuthor);
        harryPotterBook2.setPublisher(harryPotterPublisher);
        harryPotterBook2.setCategory(harryPotterCategory);
        harryPotterBook2.setName("Harry Potter i Insygnia Śmierci");
        harryPotterBook2.setPublishDate(2007);

        harryPotterBooks = new ArrayList();
        harryPotterBooks.add(harryPotterBook1);
        harryPotterBooks.add(harryPotterBook2);
    }

    @Test
    public void testFindAll() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        assertEquals(15, books.size());
    }

    @Test
    public void testFindById() {
        Book expectedBook = new Book();
        Long bookId = new Long(1);
        expectedBook.setId(bookId);
        Author author = new Author();
        author.setId(new Long(1));
        author.setName("Stephen");
        author.setSurname("King");
        expectedBook.setAuthor(author);
        Publisher publisher = new Publisher();
        publisher.setId(new Long(1));
        publisher.setName("Iskry");
        expectedBook.setPublisher(publisher);
        Category category = new Category();
        category.setId(new Long(1));
        category.setName("horror");
        expectedBook.setCategory(category);
        expectedBook.setName("Lśnienie");
        expectedBook.setPublishDate(1977);
        Book book = bookRepository.findById(bookId).orElse(null);
        assertEquals(expectedBook, book);
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
        assertEquals(16, bookRepository.count());
    }

    @Test
    public void testFindByNameContaining_atTheBeginning_beginningWithUpperCase() {
        List<Book> books = bookRepository.findByNameContaining("Harry");
        assertEquals(harryPotterBooks, books);
    }

    @Test
    public void testFindByNameContaining_atTheBeginning_allLowerCase() {
        List<Book> books = bookRepository.findByNameContaining("harry");
        assertEquals(harryPotterBooks, books);
    }

    @Test
    public void testFindByNameContaining_atTheBeginning_allUpperCase() {
        List<Book> books = bookRepository.findByNameContaining("HARRY");
        assertEquals(harryPotterBooks, books);
    }

    @Test
    public void testFindByNameContaining_atTheBeginning_mixedCase_beginningWithLowerCase() {
        List<Book> books = bookRepository.findByNameContaining("hArRy");
        assertEquals(harryPotterBooks, books);
    }

    @Test
    public void testFindByNameContaining_atTheBeginning_mixedCase_beginningWithUpperCase() {
        List<Book> books = bookRepository.findByNameContaining("HaRrY");
        assertEquals(harryPotterBooks, books);
    }

    @Test
    public void testFindByNameContaining_inTheMiddle_beginningWithUpperCase() {
        List<Book> books = bookRepository.findByNameContaining("Potter");
        assertEquals(harryPotterBooks, books);
    }

    @Test
    public void testFindByNameContaining_inTheMiddle_allLowerCase() {
        List<Book> books = bookRepository.findByNameContaining("potter");
        assertEquals(harryPotterBooks, books);
    }

    @Test
    public void testFindByNameContaining_inTheMiddle_allUpperCase() {
        List<Book> books = bookRepository.findByNameContaining("POTTER");
        assertEquals(harryPotterBooks, books);
    }

    @Test
    public void testFindByNameContaining_inTheMiddle_mixedCase_beginningWithLowerCase() {
        List<Book> books = bookRepository.findByNameContaining("pOtTeR");
        assertEquals(harryPotterBooks, books);
    }

    @Test
    public void testFindByNameContaining_inTheMiddle_mixedCase_beginningWithUpperCase() {
        List<Book> books = bookRepository.findByNameContaining("PoTtEr");
        assertEquals(harryPotterBooks, books);
    }

    @Test
    public void testFindByNameContaining_notExisting() {
        List<Book> books = bookRepository.findByNameContaining("kjdsabjsadfig");
        assertTrue(books.isEmpty());
    }

}
