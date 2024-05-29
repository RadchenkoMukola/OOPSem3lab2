package com.example.oopsem2labjavae2.repositories;

import com.example.oopsem2labjavae2.Core.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
