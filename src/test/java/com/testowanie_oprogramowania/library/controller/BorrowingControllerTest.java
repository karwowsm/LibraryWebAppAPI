package com.testowanie_oprogramowania.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.testowanie_oprogramowania.library.entity.Bookborrowing;
import com.testowanie_oprogramowania.library.error.BookBorrowingException;
import com.testowanie_oprogramowania.library.service.BorrowingService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BorrowingControllerTest {

    @Mock
    private BorrowingService borrowingService;

    @InjectMocks
    private BorrowingController borrowingController;

    private MockMvc mockMvc;

    private static Timestamp checkoutDate;

    private static Timestamp returnDate;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(borrowingController).build();
    }

    @BeforeClass
    public static void initialize() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date parsedDate1 = dateFormat.parse("2018-12-11 12:12:12");
        checkoutDate = new java.sql.Timestamp(parsedDate1.getTime());

        Date parsedDate2 = dateFormat.parse("2018-12-15 14:22:11");
        returnDate = new java.sql.Timestamp(parsedDate2.getTime());

    }

    @Test
    public void borrowBook() throws Exception {
        Bookborrowing bookborrowing = new Bookborrowing(checkoutDate, returnDate, (long) 1, (long) 1);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonBookborrowing = ow.writeValueAsString(bookborrowing);

        mockMvc.perform(post("/books/borrow")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBookborrowing))
                .andExpect(status().isOk());
    }


    @Test
    public void borrowUnavailableBook() throws Exception {
        Bookborrowing bookborrowing = new Bookborrowing(checkoutDate, returnDate, (long) 1, (long) 1);

        doThrow(new BookBorrowingException()).when(borrowingService).borrowBook(bookborrowing);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonBookBorrowing = ow.writeValueAsString(bookborrowing);

        mockMvc.perform(post("/books/borrow")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBookBorrowing))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void returnBook() throws Exception {
        doNothing().when(borrowingService).returnBook((long) 1);

        mockMvc.perform(put("/books/return/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void returnAvailableBook() throws Exception {

        doThrow(new BookBorrowingException()).when(borrowingService).returnBook((long) 1);

        mockMvc.perform(put("/books/return/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllBookBorrowing() throws Exception {
        Bookborrowing bookborrowing1 = new Bookborrowing(checkoutDate, returnDate, (long) 1, (long) 1);
        Bookborrowing bookborrowing2 = new Bookborrowing(checkoutDate, returnDate, (long) 2, (long) 2);

        bookborrowing1.setId((long) 1);
        bookborrowing2.setId((long) 2);

        List<Bookborrowing> bookBorrowingList = new ArrayList<>();
        bookBorrowingList.add(bookborrowing1);
        bookBorrowingList.add(bookborrowing2);

        when(borrowingService.getAllBookborrowings()).thenReturn(bookBorrowingList);

        mockMvc.perform(get("/books/borrowings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].checkoutDate", is("2018-12-11 12:12:12")))
                .andExpect(jsonPath("$[0].returnDate", is("2018-12-15 14:22:11")))
                .andExpect(jsonPath("$[0].dueDate", is(nullValue())))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].bookCopyId", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].checkoutDate", is("2018-12-11 12:12:12")))
                .andExpect(jsonPath("$[1].returnDate", is("2018-12-15 14:22:11")))
                .andExpect(jsonPath("$[1].dueDate", is(nullValue())))
                .andExpect(jsonPath("$[1].userId", is(2)))
                .andExpect(jsonPath("$[1].bookCopyId", is(2)));
    }

}