package com.example.demo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class TestUtils {
    public static void injectObjects(Object target, String fieldName, Object toInject){
        boolean wasPrivate = false;
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            if(!field.isAccessible()){
                field.setAccessible(true);
                wasPrivate = true;
            }
            field.set(target, toInject);
            if(wasPrivate){
                field.setAccessible(false);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Item createItem(long id, String name, String description, BigDecimal price) {
        Item item = new Item();
        item.setId(1L);
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        return item;
    }

    public static Cart createCart(long id, ArrayList<Item> items, User user){
        Cart cart = new Cart();
        cart.setId(id);
        cart.setItems(items);
        cart.setUser(user);
        cart.setTotal(BigDecimal.valueOf(10));
        return cart;
    }

    public static User createUser(long id, String name, String password, Cart cart){
        User user = new User();
        user.setId(id);
        user.setUsername(name);
        user.setPassword(password);
        user.setCart(cart);
        return user;
    }
}
