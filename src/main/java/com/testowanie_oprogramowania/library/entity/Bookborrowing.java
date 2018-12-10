package com.testowanie_oprogramowania.library.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "bookborrowings")
public class Bookborrowing {

    private Long id;
    private Timestamp checkoutDate;
    private Timestamp returnDate;
    private Timestamp dueDate;

    private Long userId;
    private Long bookCopyId;


    public Bookborrowing() {
    }

    public Bookborrowing(Timestamp checkoutDate, Timestamp returnDate, Long userId, Long bookCopyId) {
        this.checkoutDate = checkoutDate;
        this.returnDate = returnDate;
        this.userId = userId;
        this.bookCopyId = bookCopyId;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "checkout_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Warsaw")
    public Timestamp getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Timestamp checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    @Basic
    @Column(name = "return_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Warsaw")
    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    @Basic
    @Column(name = "due_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Warsaw")
    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "book_copy_id")
    public Long getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(Long bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookborrowing that = (Bookborrowing) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(checkoutDate, that.checkoutDate) &&
                Objects.equals(returnDate, that.returnDate) &&
                Objects.equals(dueDate, that.dueDate) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(bookCopyId, that.bookCopyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checkoutDate, returnDate, dueDate, userId, bookCopyId);
    }

    @Override
    public String toString() {
        return "Bookborrowing{" +
                "id=" + id +
                ", checkoutDate=" + checkoutDate +
                ", returnDate=" + returnDate +
                ", dueDate=" + dueDate +
                ", userId=" + userId +
                ", bookCopyId=" + bookCopyId +
                '}';
    }
}