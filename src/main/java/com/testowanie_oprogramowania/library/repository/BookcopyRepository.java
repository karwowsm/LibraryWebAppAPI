package com.testowanie_oprogramowania.library.repository;

import com.testowanie_oprogramowania.library.entity.Bookcopy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookcopyRepository extends JpaRepository<Bookcopy, Long> {

}
