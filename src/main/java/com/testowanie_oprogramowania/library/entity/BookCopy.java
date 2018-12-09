package com.testowanie_oprogramowania.library.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bookcopy")
public class BookCopy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @ManyToOne
    private Book book;
    @Basic
    @Column(name = "book_availability")
    private Boolean bookAvailability;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Boolean getBookAvailability() {
        return bookAvailability;
    }

    public void setBookAvailability(Boolean bookAvailability) {
        this.bookAvailability = bookAvailability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCopy bookcopy = (BookCopy) o;
        return Objects.equals(id, bookcopy.id) &&
                Objects.equals(book, bookcopy.book) &&
                Objects.equals(bookAvailability, bookcopy.bookAvailability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookAvailability);
    }
}
