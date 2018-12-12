package com.testowanie_oprogramowania.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.testowanie_oprogramowania.library.entity.User;
import com.testowanie_oprogramowania.library.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Kuba
 */
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest2 {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void getAllUsers() throws Exception {
        String startDate = "2001-10-10";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf1.parse(startDate);
        java.sql.Date birthDate = new java.sql.Date(date.getTime());
        User user1 = new User("Jan", "Kowalski", "jann@gmaill.com", birthDate, "9382129392919", "jan321123", "qwerty");
        User user2 = new User("Jan", "Kowalski", "jann321@gmaill.com", birthDate, "9382129392919", "jan321123123", "qwerty");
        user1.setId((long) 1);
        user2.setId((long) 2);
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        when(userService.getAllUsers()).thenReturn(userList);
        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Jan")))
                .andExpect(jsonPath("$[0].surname", is("Kowalski")))
                .andExpect(jsonPath("$[0].email", is("jann@gmaill.com")))
                .andExpect(jsonPath("$[0].birthDate", is("2001-10-09")))
                .andExpect(jsonPath("$[0].pesel", is("9382129392919")))
                .andExpect(jsonPath("$[0].login", is("jan321123")))
                .andExpect(jsonPath("$[0].password", is("qwerty")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Jan")))
                .andExpect(jsonPath("$[1].surname", is("Kowalski")))
                .andExpect(jsonPath("$[1].email", is("jann321@gmaill.com")))
                .andExpect(jsonPath("$[1].birthDate", is("2001-10-09")))
                .andExpect(jsonPath("$[1].pesel", is("9382129392919")))
                .andExpect(jsonPath("$[1].login", is("jan321123123")))
                .andExpect(jsonPath("$[1].password", is("qwerty")));
    }

    @Test
    public void addUserTest() throws Exception {
        String startDate = "2001-10-10";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf1.parse(startDate);
        java.sql.Date birthDate = new java.sql.Date(date.getTime());
        User user = new User("Jan", "Kowalski", "jann@gmaill.com", birthDate, "9382129392919", "jan321123", "qwerty");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonUser = ow.writeValueAsString(user);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser))
                .andExpect(status().isOk());
    }

    @Test
    public void addUserTestForNullUser() throws Exception {
        String startDate = "2001-10-10";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf1.parse(startDate);
        java.sql.Date birthDate = new java.sql.Date(date.getTime());
        User user = null;
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonUser = ow.writeValueAsString(user);
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}