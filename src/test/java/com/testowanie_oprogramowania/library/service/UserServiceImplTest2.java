package com.testowanie_oprogramowania.library.service;

import com.testowanie_oprogramowania.library.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Kuba
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceImplTest2 {

    @Autowired
    private UserService userService;

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void addEmptyUser() {
        User user = new User();
        userService.addUser(user);
    }

    @Test
    public void addUser() throws ParseException {
        String startDate = "2001-10-10";
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
        java.util.Date date = sdf1.parse(startDate);
        java.sql.Date birthDate = new java.sql.Date(date.getTime());

        int usersCountBefore = userService.getAllUsers().size();

        User user = new User("Jan", "Kowalski", "jann@gmaill.com", birthDate, "9382129392919",
                "jan123456", "qwerty");
        userService.addUser(user);

        assertEquals(usersCountBefore + 1, userService.getAllUsers().size());
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void addUserWithoutOneParameter() {

        User user = new User();
        user.setName("Jan");
        user.setSurname("Kowalski");
        user.setEmail("jann@gmaill.com");
        user.setPesel("9382129392919");
        user.setLogin("jan123456");
        user.setPassword("qwerty");

        userService.addUser(user);
    }

    @Test
    public void addUserWithId() throws ParseException {
        String startDate = "2001-10-10";
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
        java.util.Date date = sdf1.parse(startDate);
        java.sql.Date birthDate = new java.sql.Date(date.getTime());

        User user = new User();
        user.setId((long) 123321);
        user.setName("Jan");
        user.setSurname("Kowalski");
        user.setEmail("jann@gmaill.com");
        user.setBirthDate(birthDate);
        user.setPesel("9382129392919");
        user.setLogin("jan123456");
        user.setPassword("qwerty");

        userService.addUser(user);

        assertTrue(userService.getUserByLogin("jan123456").getId() != user.getId());
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void addTwoUsersWithThisSameEmail() throws ParseException {
        String startDate = "2001-10-10";
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
        java.util.Date date = sdf1.parse(startDate);
        java.sql.Date birthDate = new java.sql.Date(date.getTime());

        User user1 = new User("Jan", "Kowalski", "jann@gmaill.com", birthDate, "9382129392919",
                "jan123456", "qwerty");
        User user2 = new User("Jan", "Kowalski", "jann@gmaill.com", birthDate, "9382129392919",
                "jan321123", "qwerty");

        userService.addUser(user1);
        userService.addUser(user2);
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void addTwoUsersWithThisSameLogin() throws ParseException {
        String startDate = "2001-10-10";
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
        java.util.Date date = sdf1.parse(startDate);
        java.sql.Date birthDate = new java.sql.Date(date.getTime());

        User user1 = new User("Jan", "Kowalski", "jann@gmaill.com", birthDate, "9382129392919",
                "jan321123", "qwerty");
        User user2 = new User("Jan", "Kowalski", "jann321@gmaill.com", birthDate, "9382129392919",
                "jan321123", "qwerty");

        userService.addUser(user1);
        userService.addUser(user2);
    }

    @Test
    public void passwordEncryptionTest() throws ParseException {
        String startDate = "2001-10-10";
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
        java.util.Date date = sdf1.parse(startDate);
        java.sql.Date birthDate = new java.sql.Date(date.getTime());

        User user = new User("Jan", "Kowalski", "jann@gmaill.com", birthDate, "9382129392919",
                "jan321123", "qwerty");

        userService.addUser(user);

        assertFalse(userService.getUserByLogin("jan321123").equals(user.getPassword()));
    }

    @Test
    public void getUserByIdTest() throws ParseException {
        String startDate = "2001-10-10";
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
        java.util.Date date = sdf1.parse(startDate);
        java.sql.Date birthDate = new java.sql.Date(date.getTime());

        User user = new User("Jan", "Kowalski", "jann@gmaill.com", birthDate, "9382129392919",
                "jan321123", "qwerty");
        userService.addUser(user);

        assertEquals(user, userService.getUserById(userService.getUserByLogin("jan321123").getId()));
    }

    @Test
    public void getUserByIdTestForNotExistingId() {
        assertEquals(null, userService.getUserById((long) 123123123));
    }

    @Test
    public void getUserByLoginTest() throws ParseException {
        String startDate = "2001-10-10";
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
        java.util.Date date = sdf1.parse(startDate);
        java.sql.Date birthDate = new java.sql.Date(date.getTime());

        User user = new User("Jan", "Kowalski", "jann@gmaill.com", birthDate, "9382129392919",
                "jan321123", "qwerty");
        userService.addUser(user);
        assertEquals(user, userService.getUserByLogin("jan321123"));
    }

    @Test
    public void getUserByLoginTestForNotExistingLogin() {
        assertEquals(null, userService.getUserByLogin("testtesttesttest"));
    }


}
