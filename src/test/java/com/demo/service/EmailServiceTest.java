package com.demo.service;

import com.demo.domain.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailServiceTest {

    private final EmailService emailService = EmailService.getInstance();

    @Test
    void testSendEmail_ThrowsException() {
        Order order = new Order(1, "Book", 100.0);
        assertThrows(RuntimeException.class, () -> emailService.sendEmail(order));
        assertFalse(order.isCustomerNotified());
    }

    @Test
    void testSendEmailWithCC_Success() {
        Order order = new Order(1, "Book", 100.0);
        boolean result = emailService.sendEmail(order, "test@gmail.com");

        assertTrue(result);
        assertTrue(order.isCustomerNotified());
    }

    @Test
    void testSingletonInstance() {
        EmailService instance1 = EmailService.getInstance();
        EmailService instance2 = EmailService.getInstance();
        assertSame(instance1, instance2);
    }
}
