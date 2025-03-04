package com.demo.service;

import com.demo.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


public class EmailServiceTest {

    private EmailService emailService;
    private Order order;

    @BeforeEach
    void setUp() {
        emailService = new EmailService();
        order = new Order(1, "Phone", 1000.0);
    }
    @Test
    void testGetInstance_NotNull() {
        EmailService instance1 = EmailService.getInstance();
        //instance should not be null
        assertNotNull(instance1);
    }
    @Test
    void testGetInstance_Equals(){
        EmailService instance1 = EmailService.getInstance();
        EmailService instance2 = EmailService.getInstance();

        assertEquals(instance1, instance2);
    }
    @Test
    void testSendEmail_ThrowsException() {
        Exception exception = assertThrows(RuntimeException.class, () -> emailService.sendEmail(order));
        assertEquals("An Exception Occurred", exception.getMessage());
        //Customer should not be notified if an exception occurs
        assertFalse(order.isCustomerNotified());
    }
    @Test
    void testSendEmail_WithCC() {
        boolean result = emailService.sendEmail(order, "cc@example.com");
        assertTrue(result);
        //here customer should be notified
        assertTrue(order.isCustomerNotified());
    }

}