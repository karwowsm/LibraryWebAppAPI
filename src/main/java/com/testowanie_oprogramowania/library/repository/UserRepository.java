package com.testowanie_oprogramowania.library.repository;

import com.testowanie_oprogramowania.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
