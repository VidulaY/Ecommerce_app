package com.example.demo.controllers;

import com.example.demo.TestUtils;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;
    private UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public  void setUp(){
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void submitOrderTest() throws Exception{
        Item item = TestUtils.createItem(2L,"item#1", "itemDescription", BigDecimal.ONE);
        ArrayList<Item> items = new ArrayList<>();
        items.add(item);

        Cart cart = TestUtils.createCart(3L, items,null);
        User user = TestUtils.createUser(1, "test", "password", cart);
        cart.setUser(user);

        when(userRepository.findByUsername("test")).thenReturn(user);
        final ResponseEntity<UserOrder> response = orderController.submit("test");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        UserOrder result =  response.getBody();
        assertEquals(items, result.getItems());
        assertEquals(user, result.getUser());
    }

    @Test
    public void getOrdersForUserTest() throws Exception{
        Item item = TestUtils.createItem(2L,"item#1", "itemDescription", BigDecimal.ONE);
        ArrayList<Item> items = new ArrayList<>();
        items.add(item);

        Cart cart = TestUtils.createCart(3L, items,null);
        User user = TestUtils.createUser(1, "test", "password", cart);
        cart.setUser(user);

        when(userRepository.findByUsername("test")).thenReturn(user);
        final ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("test");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<UserOrder> result = response.getBody();
        assertNotNull( result);
    }

}
