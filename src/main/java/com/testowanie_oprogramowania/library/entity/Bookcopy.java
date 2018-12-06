package com.testowanie_oprogramowania.library.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bookcopy")
public class Bookcopy {

    private Long id;
    private Boolean bookAvailability;

    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "book_availability")
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
        Bookcopy bookcopy = (Bookcopy) o;
        return id == bookcopy.id &&
                Objects.equals(bookAvailability, bookcopy.bookAvailability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookAvailability);
    }
}
