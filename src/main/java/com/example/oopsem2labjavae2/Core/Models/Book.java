package com.example.oopsem2labjavae2.Core.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books_description")
@Getter @Setter
public class Book {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    public Book() {

    }

    public Book(int id) {
        this.id = id;
    }

    public Book(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
