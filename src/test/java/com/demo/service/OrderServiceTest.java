package com.demo.service;

import com.demo.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    private EmailService emailService;
    private Order order;

    @BeforeEach
    void setUp() {
        emailService = new EmailService();
        order = new Order(1, "Phone", 1000.0);
    }

    @Test
    void testGetInstance_NotNUll() {
        OrderService instance1 = OrderService.getInstance();
        assertNotNull(instance1);
    }

    @Test
    void testGetInstance_Same() {
        OrderService instance1 = OrderService.getInstance();
        OrderService instance2 = OrderService.getInstance();
        //instance 1 and 2 should be same
        assertEquals(instance1, instance2);
    }


    @Test
    void testSendEmail_ThrowsException() {
        Exception exception = assertThrows(RuntimeException.class, () -> emailService.sendEmail(order));
        assertEquals("An Exception Occurred", exception.getMessage());
        //if an exception occurs customer should not be notified here
        assertFalse(order.isCustomerNotified());
    }

    @Test
    void testSendEmail_WithCC() {
        boolean result = emailService.sendEmail(order, "cc@example.com");
        assertTrue(result);
        // here customer should be notified as succeded
        assertTrue(order.isCustomerNotified());
    }

}