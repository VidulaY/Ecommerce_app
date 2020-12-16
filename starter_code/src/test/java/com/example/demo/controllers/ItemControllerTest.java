package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public  void setUp(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void getItemsTest() throws Exception{
        Item testItem1 = TestUtils.createItem(1L, "test1", "test1 Descr", BigDecimal.valueOf(10));
        Item testItem2 = TestUtils.createItem(2L, "test1", "test2 Descr", BigDecimal.valueOf(20));
        when(itemRepository.findAll()).thenReturn(Arrays.asList(testItem1, testItem2));
        final ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertArrayEquals(Arrays.asList(testItem1, testItem2).toArray(), response.getBody().toArray());
    }

    @Test
    public void getItemByIdTest() throws Exception{
        Item testItem1 = TestUtils.createItem(1L, "test1", "test1 Descr", BigDecimal.valueOf(10));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(testItem1));

        final ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testItem1, response.getBody());
    }

    @Test
    public void getItemByNameTest() throws Exception{
        Item testItem1 = TestUtils.createItem(1L, "test1", "test1 Descr", BigDecimal.valueOf(10));
        when(itemRepository.findByName("test1")).thenReturn(Arrays.asList(testItem1));

        final ResponseEntity<List<Item>> response = itemController.getItemsByName("test1");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("test1", response.getBody().get(0).getName());
    }

}
