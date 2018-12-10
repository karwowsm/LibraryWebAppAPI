package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.Bookcopy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BookcopyServiceImplTest {

    @Autowired
    BookcopyService bookcopyService;


    @Test
    public void getBookcopyById() {
        Bookcopy bookcopy = new Bookcopy();
        bookcopy.setId((long) 1);
        bookcopy.setBookAvailability(true);

        assertEquals(bookcopy, bookcopyService.getBookcopyById((long) 1));
    }
}