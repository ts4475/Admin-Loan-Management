package com.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DocumentsTest {

    private Documents document;

    @BeforeEach
    void setUp() {
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setApplication_id(1L);
        // Set other properties of loanApplication...

        document = new Documents();
        document.setDocument_id(1L);
        document.setDocument_type(Documents.DocumentType.PAN);
        document.setDocument_status(Documents.DocumentStatus.OK);
        document.setDocument_url("http://example.com/documents/pan");
        document.setUpdated_at(LocalDateTime.now());
        document.setLoanApplication(loanApplication);
    }

    @Test
    void testDocumentFields() {
        assertEquals(1L, document.getDocument_id());
        assertEquals(Documents.DocumentType.PAN, document.getDocument_type());
        assertEquals(Documents.DocumentStatus.OK, document.getDocument_status());
        assertEquals("http://example.com/documents/pan", document.getDocument_url());
        assertNotNull(document.getUpdated_at());
        assertNotNull(document.getLoanApplication());
        assertEquals(1L, document.getLoanApplication().getApplication_id());
    }

    @Test
    void testPrePersist() {
        Documents newDocument = new Documents();
        newDocument.onCreate();
        assertEquals(Documents.DocumentStatus.OK, newDocument.getDocument_status());
    }

    @Test
    void testPreUpdate() {
        LocalDateTime initialTime = document.getUpdated_at();
        document.onUpdate();
        assertNotNull(document.getUpdated_at());
        assertEquals(initialTime.getDayOfYear(), document.getUpdated_at().getDayOfYear()); // Ensure the update time changes
    }
}
