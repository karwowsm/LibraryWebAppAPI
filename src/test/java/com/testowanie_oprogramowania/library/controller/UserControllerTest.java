package com.testowanie_oprogramowania.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testowanie_oprogramowania.library.entity.Author;
import com.testowanie_oprogramowania.library.entity.Book;
import com.testowanie_oprogramowania.library.entity.BookBorrowing;
import com.testowanie_oprogramowania.library.entity.BookCopy;
import com.testowanie_oprogramowania.library.entity.Category;
import com.testowanie_oprogramowania.library.entity.Publisher;
import com.testowanie_oprogramowania.library.entity.User;
import com.testowanie_oprogramowania.library.service.UserService;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.Matchers.empty;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.given;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Mateusz
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    private final String basePath = UserController.class
            .getAnnotation(RequestMapping.class).value()[0];

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
    public void testGetBorrowings_existing() throws Exception {
        User user = users.get(0);
        given(userService.getUser(user.getId())).willReturn(user);
        given(userService.getBorrowings(user.getId())).willReturn(bookBorrowings);
        String path = basePath.concat("/").concat(user.getId().toString())
                .concat("/borrowings");
        MvcResult result = mockMvc.perform(get(path))
                .andExpect(status().isOk())
                .andReturn();
        JSONAssert.assertEquals(objectMapper.writeValueAsString(bookBorrowings),
                result.getResponse().getContentAsString(), JSONCompareMode.STRICT);
    }

    @Test
    public void testGetBorrowings_badRequest() throws Exception {
        String path = basePath.concat("/").concat("NaN").concat("/borrowings");
        mockMvc.perform(get(path))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetBorrowings_notExistingUser() throws Exception {
        Long bookId = new Long(18);
        given(userService.getUser(bookId)).willReturn(null);
        String path = basePath.concat("/").concat(bookId.toString())
                .concat("/borrowings");
        mockMvc.perform(get(path))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetBorrowings_notExistingBorrowings() throws Exception {
        User user = users.get(0);
        given(userService.getUser(user.getId())).willReturn(user);
        given(userService.getBorrowings(user.getId())).willReturn(Collections.emptyList());
        String path = basePath.concat("/").concat(user.getId().toString())
                .concat("/borrowings");
        mockMvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", empty()));
    }

}