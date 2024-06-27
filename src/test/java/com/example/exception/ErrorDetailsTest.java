package com.example.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorDetailsTest {

    private ErrorDetails errorDetails;
    private Date timestamp;
    private String message;
    private String details;

    @BeforeEach
    void setUp() {
        timestamp = new Date();
        message = "Error occurred";
        details = "Detailed error message";
        errorDetails = new ErrorDetails(timestamp, message, details);
    }

    @Test
    void testGetTimestamp() {
        assertEquals(timestamp, errorDetails.getTimestamp());
    }

    @Test
    void testGetMessage() {
        assertEquals(message, errorDetails.getMessage());
    }

    @Test
    void testGetDetails() {
        assertEquals(details, errorDetails.getDetails());
    }
}
