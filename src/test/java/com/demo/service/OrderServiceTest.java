package com.demo.service;

import com.demo.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private EmailService mockEmailService;
    private OrderService orderService;
    private Order order;

    @BeforeEach
    void setUp() {
        mockEmailService = mock(EmailService.class);
        orderService = OrderService.getInstance();
        orderService.setEmailService(mockEmailService);
        order = new Order(5, "Laptop", 1000.0);
    }

    @Test
    void testPlaceOrder_EmailSent_Success() {
        doNothing().when(mockEmailService).sendEmail(any(Order.class));

        orderService.placeOrder(order);

        assertEquals(1200.0, order.getPriceWithTax());
        assertTrue(order.isCustomerNotified());
        verify(mockEmailService, times(1)).sendEmail(order);
    }

    @Test
    void testPlaceOrder_Exception() {
        doThrow(new RuntimeException("An Exception Occurred"))
                .when(mockEmailService).sendEmail(any(Order.class));

        assertThrows(RuntimeException.class, () -> orderService.placeOrder(order));
        assertFalse(order.isCustomerNotified());
        verify(mockEmailService, times(1)).sendEmail(order);
    }

    @Test
    void testPlaceOrderWithCC_Success() {
        when(mockEmailService.sendEmail(any(Order.class), anyString())).thenReturn(true);

        boolean result = orderService.placeOrder(order, "krishna@gmail.com");

        assertTrue(result);
        assertTrue(order.isCustomerNotified());
        assertEquals(1200.0, order.getPriceWithTax());
        verify(mockEmailService, times(1)).sendEmail(order, "krishna@gmail.com");
    }


    @Test
    void testSingletonInstance() {
        OrderService instance1 = OrderService.getInstance();
        OrderService instance2 = OrderService.getInstance();

        assertSame(instance1, instance2);
    }
}
