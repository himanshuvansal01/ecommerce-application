package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.CartController;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemcontrollerTest {

    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void SetUp(){

        itemController = new ItemController(null);
        TestUtils.injectObjects(itemController,"itemRepository", itemRepository);
        Item item = new Item();
        item.setId(1L);
        item.setName("Round Widget");
        BigDecimal price = BigDecimal.valueOf(2.99);
        item.setPrice(price);
        item.setDescription("A widget that is round");
        when(itemRepository.findAll()).thenReturn(Collections.singletonList(item));
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item));
        when(itemRepository.findByName("Round Widget")).thenReturn(Collections.singletonList(item));




    }


    @Test
    public void get_item_by_id_not_found(){
        ResponseEntity<Item> responseEntity = itemController.getItemById(2L);
        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());

    }

    @Test
    public void get_items_by_name(){
        ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("Round Widget");
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        List<Item> item = responseEntity.getBody();
        assertNotNull(item);
        assertEquals(1,item.size());
    }


    @Test
    public void get_items_by_name_not_found(){
        ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("Square Widget");
        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());

    }

    @Test
    public void get_all_Items(){

        ResponseEntity<List<Item>> responseEntity =  itemController.getItems();
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        List<Item> items = responseEntity.getBody();
        assertNotNull(items);
        assertEquals(1,items.size());
    }

    @Test
    public void get_item_by_id(){
        ResponseEntity<Item> responseEntity = itemController.getItemById(1L);
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        Item item = responseEntity.getBody();
        assertNotNull(item);
    }


}
