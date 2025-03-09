package com.demo.service;

import com.demo.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    private EmailService emailService;
    private Order order;

    @BeforeEach
    void setUp() {
        emailService = EmailService.getInstance();
        order = mock(Order.class);
    }

    @Test
    void testSendEmail_ThrowsException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> emailService.sendEmail(order));
        assertEquals("An Exception Occurred", exception.getMessage());
        //verify that the email notification was set to false
        verify(order).setCustomerNotified(false);
        //verify that no notification was sent
        verify(order, never()).setCustomerNotified(true);
    }

    @Test
    void testSendEmailWithCC_Success() {
        boolean result = emailService.sendEmail(order, "test@gmail.com");
        assertTrue(result);

        verify(order).setCustomerNotified(true);
    }

    @Test
    void testSingletonInstance() {
        EmailService instance1 = EmailService.getInstance();
        EmailService instance2 = EmailService.getInstance();
        assertSame(instance1, instance2);
    }
}
