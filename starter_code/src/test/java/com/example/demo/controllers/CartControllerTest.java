package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.TestAnnotationUtils;

import java.math.BigDecimal;
import java.util.Optional;

public class CartControllerTest {

    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    private CartRepository cartRepository= Mockito.mock(CartRepository.class);

    private ItemRepository itemRepository= Mockito.mock(ItemRepository.class);

    private CartController cartController;

    private ModifyCartRequest modifyCartRequest = new ModifyCartRequest();

    private User user = new User();

    private Cart cart = new Cart();

    private Item item = new Item();

    @Before
    public void setup(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository" , userRepository);
        TestUtils.injectObjects(cartController, "cartRepository" , cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository" , itemRepository);

        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setUsername("test");

        cart.setId(1L);
        user.setCart(cart);
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);

        item.setId(1L);
        item.setPrice(BigDecimal.ONE);
        Mockito.when(itemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(item));

        Mockito.when(cartRepository.save(Mockito.any())).thenReturn(cart);
    }

    @Test
    public void shouldAddTocartSuccess(){
        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        Assert.assertEquals(HttpStatus.OK , response.getStatusCode());
    }

    @Test
    public void shouldRemoveFromcartSuccess(){
        cartController.addTocart(modifyCartRequest);
        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        Assert.assertEquals(HttpStatus.OK , response.getStatusCode());
    }
}
