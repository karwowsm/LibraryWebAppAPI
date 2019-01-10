package com.testowanie_oprogramowania.library.benchmark.service;

import com.testowanie_oprogramowania.library.LibraryApplication;
import com.testowanie_oprogramowania.library.entity.BookBorrowing;
import com.testowanie_oprogramowania.library.service.BorrowingServiceImpl;
import org.openjdk.jmh.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class BorrowingServiceImplBenchmark {
    Long bookCopyID = (long) 14;
    BookBorrowing borrowing;

    static ConfigurableApplicationContext context;

    BorrowingServiceImpl borrowingService;

    @Setup(Level.Trial)
    public void setup() {
        try {
            String args = "";
            if (context == null) {
                context = SpringApplication.run(LibraryApplication.class, args);
            }
            borrowingService = context.getBean("BorrowingServiceImpl", BorrowingServiceImpl.class);
            System.out.println(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        borrowing = borrowingService.getBookborrowingById(bookCopyID);
    }

    @TearDown
    public void teardown() {
        context.close();
    }

    @Benchmark
    public List<BookBorrowing> getBorrowingsBenchmark() {
        return borrowingService.getAllBookborrowings();
    }

    @Benchmark
    public BookBorrowing getBookBenchmark() {
        return borrowingService.getBookborrowingById(bookCopyID);
    }

}
