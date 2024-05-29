package com.example.oopsem2labjavae2.repositories;

import com.example.oopsem2labjavae2.Core.Models.BookInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookInstanceRepository extends JpaRepository<BookInstance, Long> {
    @Query("select new BookInstance(b.isbn, b.id, b.status) from BookInstance b where b.id in ?1")
    List<BookInstance> findAllByIds(Iterable<Integer> ids);
}
