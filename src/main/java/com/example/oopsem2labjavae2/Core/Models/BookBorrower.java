package com.example.oopsem2labjavae2.Core.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books_borrowers")
@Getter @Setter
public class BookBorrower {
    @Id
    @Column(name = "isbn")
    long isbn;

    @Column(name = "username")
    String username;

    @Override
    public String toString() {
        return "BookBorrower{" +
                "isbn=" + isbn +
                ", username='" + username + '\'' +
                '}';
    }
}
