package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.Author;
import com.testowanie_oprogramowania.library.entity.Book;
import com.testowanie_oprogramowania.library.entity.Category;
import com.testowanie_oprogramowania.library.entity.Publisher;
import com.testowanie_oprogramowania.library.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class BookServiceImplTest {

    @TestConfiguration
    static class BookServiceImplTestContextConfiguration {

        @Bean
        public BookService bookService() {
            return new BookServiceImpl();
        }
    }

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    private static List<Book> books;

    @BeforeClass
    public static void setUp() {
        Book book1 = new Book();
        Author author = new Author();
        author.setId(new Long(5));
        book1.setAuthor(author);
        Publisher publisher = new Publisher();
        publisher.setId(new Long(2));
        book1.setPublisher(publisher);
        Category category = new Category();
        category.setId(new Long(4));
        book1.setCategory(category);
        book1.setName("Dwie wieże");
        book1.setPublishDate(1954);

        Book book2 = new Book();
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
    public void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> foundBooks = bookService.getAllBooks();
        assertEquals(foundBooks, books);
    }
}
