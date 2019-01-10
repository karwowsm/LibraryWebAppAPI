package com.testowanie_oprogramowania.library.benchmark.service;


import com.testowanie_oprogramowania.library.LibraryApplication;
import com.testowanie_oprogramowania.library.entity.BookBorrowing;
import com.testowanie_oprogramowania.library.entity.User;
import com.testowanie_oprogramowania.library.service.UserServiceImpl;
import org.assertj.core.util.Lists;
import org.openjdk.jmh.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class UserServiceImplBenchmarkTest {

    private UserServiceImpl userService;

    private Long userID;

    private String userLogin;

    private User user;

    private RestTemplate template;
    private HttpHeaders jsonHeaders;

    static ConfigurableApplicationContext context;


    @Setup(Level.Trial)
    public void setup() throws ParseException {
        try {
            String args = "";
            if (context == null) {
                context = SpringApplication.run(LibraryApplication.class, args);
            }
            userService = context.getBean("UserServiceImpl1", UserServiceImpl.class);
            System.out.println(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<HttpMessageConverter<?>> messageConverters = Lists.newArrayList();
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        template = new RestTemplate(messageConverters);

        jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
    }


    @Benchmark
    public List<User> getAllUsersFromController() {
        ResponseEntity<List> response = template.exchange("http://localhost:8080/users", HttpMethod.GET, new HttpEntity<>(jsonHeaders), List.class);

        return response.getBody();
    }


    @Benchmark
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Benchmark
    public User getUser() {
        return userService.getUserById(userID);
    }

    @Benchmark
    public User getUserById() {
        return userService.getUserById(userID);
    }

    @Benchmark
    public User getUserByLogin() {
        return userService.getUserByLogin(userLogin);
    }

    @Benchmark
    public List<BookBorrowing> getBorrowings() {
        return userService.getBorrowings(userID);
    }

}
