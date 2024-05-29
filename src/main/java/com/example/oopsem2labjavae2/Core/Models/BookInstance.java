package com.example.oopsem2labjavae2.Core.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books_instances")
@Getter @Setter
public class BookInstance {
    @Id
    @Column(name = "isbn")
    private long isbn;

    @Column(name = "id")
    private int id;

    @Column(name = "status")
    private BookInstanceStatus status;

    @Transient
    private Book book;

    @Transient
    private User borrower;

    public BookInstance()
    {

    }

    public BookInstance(long isbn, int id, BookInstanceStatus status) {
        this.isbn = isbn;
        this.id = id;
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookInstance{" +
                "isbn='" + isbn + '\'' +
                ", book=" + book +
                ", status=" + status +
                ", borrower=" + borrower +
                '}';
    }
}
