package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.Author;
import com.testowanie_oprogramowania.library.entity.Book;
import com.testowanie_oprogramowania.library.entity.BookCopy;
import com.testowanie_oprogramowania.library.entity.Category;
import com.testowanie_oprogramowania.library.entity.Publisher;
import com.testowanie_oprogramowania.library.repository.BookCopyRepository;
import com.testowanie_oprogramowania.library.repository.BookRepository;
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

    @MockBean
    private BookCopyRepository bookCopyRepository;

    private static List<Book> books;
    private static List<BookCopy> bookCopies;

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

        BookCopy bookCopy1 = new BookCopy();
        bookCopy1.setId(new Long(1));
        bookCopy1.setBook(book1);
        bookCopy1.setBookAvailability(Boolean.TRUE);

        BookCopy bookCopy2 = new BookCopy();
        bookCopy2.setId(new Long(2));
        bookCopy2.setBook(book1);
        bookCopy2.setBookAvailability(Boolean.FALSE);

        bookCopies = new ArrayList();
        bookCopies.add(bookCopy1);
        bookCopies.add(bookCopy2);
    }

    @Test
    public void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> foundBooks = bookService.getAllBooks();
        assertEquals(books, foundBooks);
    }

    @Test
    public void testGetBook() {
        Book book = books.get(0);
        when(bookRepository.findById(book.getId()))
                .thenReturn(Optional.of(book));
        Book foundBook = bookService.getBook(book.getId());
        assertEquals(book, foundBook);
    }

    @Test
    public void testSearchBook_existing() {
        when(bookRepository.findByNameContaining(Mockito.anyString()))
                .thenReturn(books);
        List<Book> foundBooks = bookService.searchBook(Mockito.anyString());
        assertEquals(books, foundBooks);
    }

    @Test
    public void testSearchBook_notExisting() {
        when(bookRepository.findByNameContaining(Mockito.anyString()))
                .thenReturn(Collections.emptyList());
        List<Book> foundBooks = bookService.searchBook(Mockito.anyString());
        assertTrue(foundBooks.isEmpty());
    }

    @Test
    public void testGetCopies_existing() {
        when(bookCopyRepository.findByBookId(Mockito.anyLong()))
                .thenReturn(bookCopies);
        List<BookCopy> foundBookCopies = bookService.getCopies(Mockito.anyLong());
        assertEquals(bookCopies, foundBookCopies);
    }

    @Test
    public void testGetCopies_notExisting() {
        when(bookCopyRepository.findByBookId(Mockito.anyLong()))
                .thenReturn(Collections.emptyList());
        List<BookCopy> foundBookCopies = bookService.getCopies(Mockito.anyLong());
        assertTrue(foundBookCopies.isEmpty());
    }

}
