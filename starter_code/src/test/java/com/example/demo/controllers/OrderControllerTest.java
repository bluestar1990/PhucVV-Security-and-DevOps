package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderControllerTest {

    private OrderController orderController;

    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    private OrderRepository orderRepository= Mockito.mock(OrderRepository.class);

    private UserOrder userOrder = new UserOrder();
    private User user = new User();

    private Cart cart = new Cart();

    private Item item = new Item();

    private List<Item> items = new ArrayList<>();

    private List<UserOrder> userOrders = new ArrayList<>();

    @Before
    public void setup() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);


        cart.setId(1L);

        item.setPrice(BigDecimal.ONE);
        item.setId(1L);
        item.setDescription("des");
        items.add(item);

        cart.setItems(items);
        user.setCart(cart);
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);

        userOrder.setUser(user);
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(userOrder);

        userOrders.add(userOrder);
        Mockito.when(orderRepository.findByUser(Mockito.any(User.class))).thenReturn(userOrders);
    }

    @Test
    public void shouldSubmitSuccess(){
        ResponseEntity<UserOrder> response = orderController.submit("test");
        Assert.assertEquals(HttpStatus.OK , response.getStatusCode());
    }

    @Test
    public void shouldGetOrdersForUserSuccess(){
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("test");
        Assert.assertEquals(HttpStatus.OK , response.getStatusCode());
    }
}
