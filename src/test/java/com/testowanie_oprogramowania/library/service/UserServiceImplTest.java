package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.Author;
import com.testowanie_oprogramowania.library.entity.Book;
import com.testowanie_oprogramowania.library.entity.BookBorrowing;
import com.testowanie_oprogramowania.library.entity.BookCopy;
import com.testowanie_oprogramowania.library.entity.Category;
import com.testowanie_oprogramowania.library.entity.Publisher;
import com.testowanie_oprogramowania.library.entity.User;
import com.testowanie_oprogramowania.library.repository.BookBorrowingRepository;
import com.testowanie_oprogramowania.library.repository.UserRepository;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Mateusz
 */
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BookBorrowingRepository bookBorrowingRepository;

    private static List<User> users;
    private static List<BookBorrowing> bookBorrowings;

    @BeforeClass
    public static void setUp() throws ParseException {
        User user1 = new User();
        user1.setId(new Long(1));
        user1.setName("Jan");
        user1.setSurname("Kowalski");
        user1.setEmail("jan.kowalski@gmail.com");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        long datetime = dateFormat.parse("01/01/1997").getTime();
        user1.setBirthDate(new Date(datetime));
        user1.setPesel("97010166666");
        user1.setLogin("jkowalski");
        user1.setPassword("asdf");

        User user2 = new User();
        user2.setId(new Long(2));
        user2.setName("Jan");
        user2.setSurname("Nowak");
        user2.setEmail("jan.nowak@gmail.com");
        datetime = dateFormat.parse("12/12/1983").getTime();
        user2.setBirthDate(new Date(datetime));
        user2.setPesel("83121277777");
        user2.setLogin("jnowak");
        user2.setPassword("zxcv");

        users = new ArrayList();
        users.add(user1);
        users.add(user2);

        BookBorrowing bookBorrowing1 = new BookBorrowing();
        bookBorrowing1.setId(new Long(1));
        bookBorrowing1.setUser(user1);
        BookCopy bookCopy1 = new BookCopy();
        bookCopy1.setId(new Long(16));
        Book book1 = new Book();
        book1.setId(new Long(16));
        Author author = new Author();
        author.setId(new Long(5));
        author.setName("J.R.R.");
        author.setSurname("Tolkien");
        book1.setAuthor(author);
        Publisher publisher = new Publisher();
        publisher.setId(new Long(2));
        publisher.setName("Amber");
        book1.setPublisher(publisher);
        Category category = new Category();
        category.setId(new Long(4));
        category.setName("fantasy");
        book1.setCategory(category);
        book1.setName("Dwie wieże");
        book1.setPublishDate(1954);
        bookCopy1.setBook(book1);
        bookCopy1.setBookAvailability(Boolean.FALSE);
        bookBorrowing1.setBookCopy(bookCopy1);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        datetime = dateFormat.parse("01/12/2018 15:15:15").getTime();
        bookBorrowing1.setCheckoutDate(new Timestamp(datetime));
        datetime = dateFormat.parse("01/01/2019 15:15:15").getTime();
        bookBorrowing1.setDueDate(new Timestamp(datetime));

        BookBorrowing bookBorrowing2 = new BookBorrowing();
        bookBorrowing2.setId(new Long(2));
        bookBorrowing2.setUser(user1);
        BookCopy bookCopy2 = new BookCopy();
        bookCopy2.setId(new Long(17));
        Book book2 = new Book();
        book2.setId(new Long(17));
        book2.setAuthor(author);
        book2.setPublisher(publisher);
        book2.setCategory(category);
        book2.setName("Powrót króla");
        book2.setPublishDate(1955);
        bookCopy2.setBook(book2);
        bookCopy2.setBookAvailability(Boolean.FALSE);
        bookBorrowing1.setBookCopy(bookCopy1);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        datetime = dateFormat.parse("01/12/2017 15:15:15").getTime();
        bookBorrowing1.setCheckoutDate(new Timestamp(datetime));
        datetime = dateFormat.parse("01/01/2018 15:15:15").getTime();
        bookBorrowing1.setDueDate(new Timestamp(datetime));
        datetime = dateFormat.parse("31/12/2017 15:15:15").getTime();
        bookBorrowing1.setReturnDate(new Timestamp(datetime));

        bookBorrowings = new ArrayList();
        bookBorrowings.add(bookBorrowing1);
        bookBorrowings.add(bookBorrowing2);
    }

    @Test
    public void testGetBook() {
        User user = users.get(0);
        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        User foundUser = userService.getUser(user.getId());
        assertEquals(user, foundUser);
    }

    @Test
    public void testGetBorrowings_existing() {
        Long userId = Mockito.anyLong();
        when(bookBorrowingRepository.findByUserId(userId))
                .thenReturn(bookBorrowings);
        List<BookBorrowing> foundBookCopies = userService.getBorrowings(userId);
        assertEquals(bookBorrowings, foundBookCopies);
    }

    @Test
    public void testGetBorrowings_notExisting() {
        Long userId = Mockito.anyLong();
        when(bookBorrowingRepository.findByUserId(userId))
                .thenReturn(Collections.emptyList());
        List<BookBorrowing> foundBookBorrowings = userService.getBorrowings(userId);
        assertTrue(foundBookBorrowings.isEmpty());
    }

}
