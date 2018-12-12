package com.testowanie_oprogramowania.library.error;

public class BookBorrowingException extends RuntimeException {

    public BookBorrowingException() {
        super();
    }

    public BookBorrowingException(String message) {
        super(message);
    }

    public BookBorrowingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookBorrowingException(Throwable cause) {
        super(cause);
    }
}
