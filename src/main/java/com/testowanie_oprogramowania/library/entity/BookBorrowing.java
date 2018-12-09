package com.testowanie_oprogramowania.library.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "bookborrowings")
public class BookBorrowing implements Serializable {

    @Id
    @Column(name = "ID")
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private BookCopy bookCopy;
    @Basic
    @Column(name = "checkout_date")
    private Timestamp checkoutDate;
    @Basic
    @Column(name = "due_date")
    private Timestamp dueDate;
    @Basic
    @Column(name = "return_date")
    private Timestamp returnDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public Timestamp getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Timestamp checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookBorrowing that = (BookBorrowing) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(checkoutDate, that.checkoutDate) &&
                Objects.equals(dueDate, that.dueDate) &&
                Objects.equals(returnDate, that.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checkoutDate, returnDate, dueDate);
    }
}
