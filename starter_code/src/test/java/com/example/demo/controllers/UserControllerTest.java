package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    private CartRepository cartRepository= Mockito.mock(CartRepository.class);

    private PasswordEncoder passwordEncoder= Mockito.mock(PasswordEncoder.class);

    private User user = new User();
    private Cart cart = new Cart();

    private Item item = new Item();

    private List<Item> items = new ArrayList<>();

    @Before
    public void setup(){
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "passwordEncoder", passwordEncoder);

        cart.setId(1L);
        item.setPrice(BigDecimal.ONE);
        item.setId(1L);
        item.setDescription("des");
        items.add(item);
        cart.setItems(items);
        user.setCart(cart);

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(user));
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        Mockito.when(cartRepository.save(Mockito.any())).thenReturn(cart);
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("$2a$10$jSFbRUfZv.HaCgzvf2DvN.zt85IqVuUCOnzZ2F3sfGrk0ERysm9py");
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

    }

    @Test
    public void shouldFindByIdSuccess(){
        ResponseEntity<User> response = userController.findById(1L);
        Assert.assertEquals(HttpStatus.OK , response.getStatusCode());
    }

    @Test
    public void shouldFindByUserNameSuccess(){
        ResponseEntity<User> response = userController.findByUserName("test");
        Assert.assertEquals(HttpStatus.OK , response.getStatusCode());
    }

    @Test
    public void shouldCreateUserSuccess(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setConfirmPassword("passwprd");
        createUserRequest.setPassword("passwprd");
        createUserRequest.setUsername("test");
        ResponseEntity<User> response = userController.createUser(createUserRequest);
        Assert.assertEquals(HttpStatus.OK , response.getStatusCode());
    }
}
