package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ItemControllerTest {
    private ItemController itemController;
    @Mock
    private ItemRepository itemRepository= Mockito.mock(ItemRepository.class);

    private List<Item> items = new ArrayList<>();

    private  Item item = new Item();
    @Before
    public void setup() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);

        item.setPrice(BigDecimal.ONE);
        item.setId(1L);
        item.setDescription("des");
        items.add(item);

        Mockito.when(itemRepository.findAll()).thenReturn(items);

        Mockito.when(itemRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(item));

        Mockito.when(itemRepository.findByName(Mockito.anyString())).thenReturn(items);
    }

    @Test
    public void shouldGetItemsSuccess(){
        ResponseEntity<List<Item>> response = itemController.getItems();
        Assert.assertEquals(HttpStatus.OK , response.getStatusCode());
        Assert.assertTrue(response.getBody().size() == 1);
    }

    @Test
    public void shouldGetItemByIdSuccess(){
        ResponseEntity<Item> response = itemController.getItemById(1L);
        Assert.assertEquals(HttpStatus.OK , response.getStatusCode());
        Assert.assertTrue(Objects.nonNull(response.getBody()));
    }

    @Test
    public void shouldGetItemByNameSuccess(){
        ResponseEntity<List<Item>> response = itemController.getItemsByName("test");
        Assert.assertEquals(HttpStatus.OK , response.getStatusCode());
        Assert.assertTrue(response.getBody().size() == 1);
    }
}
