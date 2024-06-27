package com.example.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceNotFoundExceptionTest {

    @Test
    public void testResourceNotFoundException() {
        // Given
        String errorMessage = "Resource not found";

        // When
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

        // Then
        assertEquals(errorMessage, exception.getMessage());
        assertTrue(exception instanceof ResourceNotFoundException);
    }
}
