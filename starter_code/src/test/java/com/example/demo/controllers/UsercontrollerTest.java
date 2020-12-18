package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsercontrollerTest {
    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public  void setUp(){
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void create_user_happy_path() throws Exception{
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test");
        createUserRequest.setPassword("testPassword");
        createUserRequest.setConfirmPassword("testPassword");

        final ResponseEntity<User> response = userController.createUser(createUserRequest);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("test", user.getUsername());
        assertEquals("thisIsHashed", user.getPassword());
    }

    @Test
    public void create_user_error() throws Exception{
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test");
        createUserRequest.setPassword("Password");
        createUserRequest.setConfirmPassword("testPassword");

        final ResponseEntity<User> response = userController.createUser(createUserRequest);
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void findUserByIdTest() throws Exception{
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test");
        createUserRequest.setPassword("Password");
        createUserRequest.setConfirmPassword("Password");
        final ResponseEntity<User> response = userController.createUser(createUserRequest);
        User user = response.getBody();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("test", response.getBody().getUsername());
    }

    @Test
    public void findUserByNameTest() throws Exception{
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test");
        createUserRequest.setPassword("Password");
        createUserRequest.setConfirmPassword("Password");
        final ResponseEntity<User> response = userController.createUser(createUserRequest);
        User user = response.getBody();
        when(userRepository.findByUsername("testUsername")).thenReturn(user);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("test", response.getBody().getUsername());
    }

    @Test
    public void findByIdNotErrorTest(){
        ResponseEntity<User> response = userController.findById(1L);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}
