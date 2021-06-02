package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UsercontrollerTest {

    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);
    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", passwordEncoder);
        User user = new User();
        Cart cart = new Cart();
        user.setId(0);
        user.setUsername("Himanshu");
        user.setPassword("vansal1332");
        user.setCart(cart);
        when(userRepository.findByUsername("Himanshu")).thenReturn(user);
        when(userRepository.findById(0L)).thenReturn(java.util.Optional.of(user));
        when(userRepository.findByUsername("test")).thenReturn(null);
    }
    @Test
    public void createUser(){

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("Himanshu");
        createUserRequest.setPassword("vansal1332");
        createUserRequest.setConfirmPassword("vansal1332");
        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        User user = responseEntity.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("Himanshu", user.getUsername());
    }
    @Test
    public void findUserByUsername(){
        ResponseEntity<User> userResponseEntity = userController.findByUserName("Himanshu");
        assertNotNull(userResponseEntity);
        assertEquals(200, userResponseEntity.getStatusCodeValue());
        User user = userResponseEntity.getBody();
        assertNotNull(user);
        assertEquals("Himanshu", user.getUsername());
    }
    @Test
    public void findUserByUserNameNotFound(){
        ResponseEntity<User> userResponseEntity = userController.findByUserName("test");
        assertNotNull(userResponseEntity);
        assertEquals(404, userResponseEntity.getStatusCodeValue());
    }
    @Test
    public void findUserById(){
        ResponseEntity<User> userResponseEntity = userController.findById(0L);
        assertNotNull(userResponseEntity);
        assertEquals(200, userResponseEntity.getStatusCodeValue());
        User user = userResponseEntity.getBody();
        assertNotNull(user);
        assertEquals("Himanshu", user.getUsername());
    }
    @Test
    public void findUserByIdNotFound(){
        ResponseEntity<User> userResponseEntity = userController.findById(999L);
        System.out.println(userResponseEntity);
        assertNotNull(userResponseEntity);
        assertEquals(404, userResponseEntity.getStatusCodeValue());
    }
}