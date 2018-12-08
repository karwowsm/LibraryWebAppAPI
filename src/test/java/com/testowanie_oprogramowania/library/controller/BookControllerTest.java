package com.testowanie_oprogramowania.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testowanie_oprogramowania.library.entity.Author;
import com.testowanie_oprogramowania.library.entity.Book;
import com.testowanie_oprogramowania.library.entity.Category;
import com.testowanie_oprogramowania.library.entity.Publisher;
import com.testowanie_oprogramowania.library.service.BookService;
import java.util.ArrayList;
import java.util.List;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Mateusz
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    ObjectMapper objectMapper;

    private final String basePath = BookController.class
            .getAnnotation(RequestMapping.class).value()[0];

    private static List<Book> books;

    @BeforeClass
    public static void setUp() {
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

        Book book2 = new Book();
        book2.setId(new Long(17));
        book2.setAuthor(author);
        book2.setPublisher(publisher);
        book2.setCategory(category);
        book2.setName("Powrót króla");
        book2.setPublishDate(1955);

        books = new ArrayList();
        books.add(book1);
        books.add(book2);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        given(bookService.getAllBooks()).willReturn(books);
        MvcResult result = mockMvc.perform(get(basePath))
                .andExpect(status().isOk())
                .andReturn();
        JSONAssert.assertEquals(objectMapper.writeValueAsString(books),
                result.getResponse().getContentAsString(), JSONCompareMode.STRICT);
    }

    @Test
    public void testGetBook1() throws Exception {
        Book book = books.get(0);
        given(bookService.getBook(book.getId())).willReturn(book);
        String path = basePath.concat("/").concat(book.getId().toString());
        MvcResult result = mockMvc.perform(get(path))
                .andExpect(status().isOk())
                .andReturn();
        JSONAssert.assertEquals(objectMapper.writeValueAsString(book),
                result.getResponse().getContentAsString(), JSONCompareMode.STRICT);
    }

    @Test
    public void testGetBook2() throws Exception {
        Long bookId = new Long(18);
        given(bookService.getBook(bookId)).willReturn(null);
        String path = basePath.concat("/").concat(bookId.toString());
        mockMvc.perform(get(path))
                .andExpect(status().isNotFound());
    }

}
