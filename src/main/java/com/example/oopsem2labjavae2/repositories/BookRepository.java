package com.example.oopsem2labjavae2.repositories;

import com.example.oopsem2labjavae2.Core.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("select new Book(b.id, b.title, b.description) from Book b where b.title like concat('%', ?1, '%') or b.description like concat('%', ?1, '%')")
    List<Book> findAllByPrompt(String prompt);
}
