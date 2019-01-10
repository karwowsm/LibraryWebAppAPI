package com.testowanie_oprogramowania.library.benchmark.service;

import com.testowanie_oprogramowania.library.LibraryApplication;
import com.testowanie_oprogramowania.library.entity.Book;
import com.testowanie_oprogramowania.library.entity.BookCopy;
import com.testowanie_oprogramowania.library.service.BookServiceImpl;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author Mateusz
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class BookServiceImplBenchmarkTest {

    private ConfigurableApplicationContext context;

    private BookServiceImpl bookService;

    private Book book;

    @Setup
    public void setup() {
        context = SpringApplication.run(LibraryApplication.class);
        bookService = context.getBean("BookServiceImpl", BookServiceImpl.class);
        book = bookService.getBook(new Long(1));
    }

    @TearDown
    public void tearDown() {
        context.close();
    }

    @Benchmark
    public List<Book> benchmark1_getAllBooksTest() {
        return bookService.getAllBooks();
    }

    @Benchmark
    public Book benchmark2_getBookTest() {
        return bookService.getBook(book.getId());
    }

    @Benchmark
    public List<Book> benchmark3_searchBookTest() {
        return bookService.searchBook("Harry Potter");
    }

    @Benchmark
    public List<BookCopy> benchmark4_getCopiesTest() {
        return bookService.getCopies(book.getId());
    }

    @Benchmark
    public void benchmark5_addBookTest() {
        bookService.addBook(book);
    }

    @Benchmark
    public void benchmark6_updateBookTest() {
        bookService.updateBook(book);
    }

}
