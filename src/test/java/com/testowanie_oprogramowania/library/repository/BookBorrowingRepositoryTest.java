package com.testowanie_oprogramowania.library.repository;

import com.testowanie_oprogramowania.library.entity.Author;
import com.testowanie_oprogramowania.library.entity.Book;
import com.testowanie_oprogramowania.library.entity.BookBorrowing;
import com.testowanie_oprogramowania.library.entity.BookCopy;
import com.testowanie_oprogramowania.library.entity.Category;
import com.testowanie_oprogramowania.library.entity.Publisher;
import com.testowanie_oprogramowania.library.entity.User;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class BookBorrowingRepositoryTest {

    @Autowired
    private BookBorrowingRepository bookBorrowingRepository;

    private static User userInstance;
    private static BookCopy bookCopyInstance;

    @BeforeClass
    public static void setUp() throws ParseException {
        userInstance = new User();
        userInstance.setId(new Long(1));
        userInstance.setName("Jan");
        userInstance.setSurname("Kowalski");
        userInstance.setEmail("jan.kowalski@gmail.com");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        long birthDateTimestamp = dateFormat.parse("01/01/1997").getTime();
        userInstance.setBirthDate(new Date(birthDateTimestamp));
        userInstance.setPesel("97010166666");
        userInstance.setLogin("jkowalski");
        userInstance.setPassword("asdf");

        bookCopyInstance = new BookCopy();
        bookCopyInstance.setId(new Long(2));
        Book book = new Book();
        book.setId(new Long(1));
        Author author = new Author();
        author.setId(new Long(1));
        author.setName("Stephen");
        author.setSurname("King");
        book.setAuthor(author);
        Publisher publisher = new Publisher();
        publisher.setId(new Long(1));
        publisher.setName("Iskry");
        book.setPublisher(publisher);
        Category category = new Category();
        category.setId(new Long(1));
        category.setName("horror");
        book.setCategory(category);
        book.setName("Lśnienie");
        book.setPublishDate(1977);
        bookCopyInstance.setBook(book);
        bookCopyInstance.setBookAvailability(Boolean.FALSE);
    }

    @Test
    public void testDeleteAll() {
        bookBorrowingRepository.deleteAll();
        assertEquals(0, bookBorrowingRepository.count());
    }

    @Test
    public void testDeleteById() {
        Long id = new Long(1);
        bookBorrowingRepository.deleteById(id);
        assertEquals(false, bookBorrowingRepository.existsById(id));
    }

    @Test
    public void testFindAll() {
        List<BookBorrowing> bookBorrowings = (List<BookBorrowing>) bookBorrowingRepository.findAll();
        assertEquals(8, bookBorrowings.size());
    }

    @Test
    public void testFindById() throws ParseException {
        BookBorrowing expectedBookBorrowing = new BookBorrowing();
        Long bookBorrowingId = new Long(1);
        expectedBookBorrowing.setId(bookBorrowingId);
        expectedBookBorrowing.setUser(userInstance);
        expectedBookBorrowing.setBookCopy(bookCopyInstance);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        long timeStamp = dateFormat.parse("01/12/2018 15:15:15").getTime();
        expectedBookBorrowing.setCheckoutDate(new Timestamp(timeStamp));
        timeStamp = dateFormat.parse("01/01/2019 15:15:15").getTime();
        expectedBookBorrowing.setDueDate(new Timestamp(timeStamp));
        expectedBookBorrowing.setReturnDate(null);
        BookBorrowing bookBorrowing = bookBorrowingRepository.findById(bookBorrowingId).orElse(null);
        assertEquals(expectedBookBorrowing, bookBorrowing);
    }

}
