package com.testowanie_oprogramowania.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.testowanie_oprogramowania.library.entity.BookBorrowing;
import com.testowanie_oprogramowania.library.error.BookBorrowingException;
import com.testowanie_oprogramowania.library.service.BookCopyService;
import com.testowanie_oprogramowania.library.service.BorrowingService;
import com.testowanie_oprogramowania.library.service.UserService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BorrowingControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private BookCopyService bookCopyService;

    @Mock
    private BorrowingService borrowingService;

    @InjectMocks
    private BorrowingController borrowingController;

    private MockMvc mockMvc;

    private static Timestamp checkoutDate;

    private static Timestamp dueDate;

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
        dueDate = new java.sql.Timestamp(parsedDate2.getTime());

    }

    @Test
    public void borrowBook() throws Exception {
        BookBorrowing bookBorrowing = new BookBorrowing(userService.getUserById((long) 1),
                bookCopyService.getBookcopyById((long) 1), checkoutDate, dueDate);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonBookborrowing = ow.writeValueAsString(bookBorrowing);

        mockMvc.perform(post("/books/borrow")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBookborrowing))
                .andExpect(status().isOk());
    }


    @Test
    public void borrowUnavailableBook() throws Exception {
        BookBorrowing bookBorrowing = new BookBorrowing(userService.getUserById((long) 1),
                bookCopyService.getBookcopyById((long) 1), checkoutDate, dueDate);

        doThrow(new BookBorrowingException()).when(borrowingService).borrowBook(bookBorrowing);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonBookBorrowing = ow.writeValueAsString(bookBorrowing);

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
        BookBorrowing bookBorrowing1 = new BookBorrowing(userService.getUserById((long) 1),
                bookCopyService.getBookcopyById((long) 1), checkoutDate, dueDate);

        BookBorrowing bookBorrowing2 = new BookBorrowing(userService.getUserById((long) 2),
                bookCopyService.getBookcopyById((long) 4), checkoutDate, dueDate);

        bookBorrowing1.setId((long) 1);
        bookBorrowing2.setId((long) 2);

        List<BookBorrowing> bookBorrowingList = new ArrayList<>();
        bookBorrowingList.add(bookBorrowing1);
        bookBorrowingList.add(bookBorrowing2);

        when(borrowingService.getAllBookborrowings()).thenReturn(bookBorrowingList);

        MvcResult result = mockMvc.perform(get("/books/borrowings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONAssert.assertEquals(objectMapper.writeValueAsString(bookBorrowingList),
                result.getResponse().getContentAsString(), JSONCompareMode.STRICT);
    }

}