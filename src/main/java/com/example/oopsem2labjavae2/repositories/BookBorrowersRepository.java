package com.example.oopsem2labjavae2.repositories;

import com.example.oopsem2labjavae2.Core.Models.BookBorrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookBorrowersRepository extends JpaRepository<BookBorrower, Long> {

}
