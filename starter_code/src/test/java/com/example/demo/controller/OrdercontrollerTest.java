package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrdercontrollerTest {

    private OrderController orderController;
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void setUp(){
        orderController = new OrderController(null, null);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
        TestUtils.injectObjects(orderController, "userRepository", userRepository);

        Item item = new Item();
        item.setId(1L);
        item.setName("Square Widget");
        BigDecimal price = BigDecimal.valueOf(1.99);
        item.setPrice(price);
        item.setDescription("A widget that is square");
        List<Item > items = new ArrayList<>();
        items.add(item);

        User user = new User();
        Cart cart = new Cart();
        user.setId(0);
        user.setUsername("Himanshuvansal");
        user.setPassword("vansal1332");
        cart.setId(0L);
        cart.setUser(user);
        cart.setItems(items);
        BigDecimal bigDecimal = BigDecimal.valueOf(1.99);
        cart.setTotal(bigDecimal);
        user.setCart(cart);
        when(userRepository.findByUsername("Himanshuvansal")).thenReturn(user);
        when(userRepository.findByUsername("vansal")).thenReturn(null);
    }



    @Test
    public void get_orders_For_User(){
        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser("Himanshuvansal");
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        List<UserOrder> userOrders = responseEntity.getBody();
        assertNotNull(userOrders);

    }

    @Test
    public void get_orders_For_User_Not_Found(){
        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser("vansal");
        assertNotNull(responseEntity);
        assertEquals(404,responseEntity.getStatusCodeValue());


    }

    @Test
    public void submit_order(){
        ResponseEntity<UserOrder> userOrderResponseEntity = orderController.submit("Himanshuvansal");
        assertNotNull(userOrderResponseEntity);
        assertEquals(200, userOrderResponseEntity.getStatusCodeValue());
        UserOrder userOrder = userOrderResponseEntity.getBody();
        assertNotNull(userOrder);
        assertEquals(1, userOrder.getItems().size());
    }

    @Test
    public void submit_order_User_Not_Found(){
        ResponseEntity<UserOrder> responseEntity = orderController.submit("vansal");
        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());

    }





}
