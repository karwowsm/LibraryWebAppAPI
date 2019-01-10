package com.testowanie_oprogramowania.library.benchmark.service;

import com.testowanie_oprogramowania.library.LibraryApplication;
import com.testowanie_oprogramowania.library.entity.BookCopy;
import com.testowanie_oprogramowania.library.service.BookCopyServiceImpl;
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
public class BookCopyServiceImplBenchmarkTest {

    private ConfigurableApplicationContext context;

    private BookCopyServiceImpl bookCopyService;

    @Setup
    public void setup() {
        context = SpringApplication.run(LibraryApplication.class);
        bookCopyService = context.getBean("BookCopyServiceImpl", BookCopyServiceImpl.class);
    }

    @TearDown
    public void tearDown() {
        context.close();
    }

    @Benchmark
    public BookCopy getBookcopyById() {
        return bookCopyService.getBookcopyById(new Long(1));
    }

}
