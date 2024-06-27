package com.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoanApplicationTest {

    private LoanApplication loanApplication;
    private Users user;
    private LoanProducts product;
    private Vendors vendor;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setUser_id(1L);
        // Set other properties of user...

        product = new LoanProducts();
        product.setProduct_id(1L);
        // Set other properties of product...

        vendor = new Vendors();
        vendor.setVendor_id(1L);
        // Set other properties of vendor...

        loanApplication = new LoanApplication();
        loanApplication.setApplication_id(1L);
        loanApplication.setAmount_required(5000);
        loanApplication.setTenure(12);
        loanApplication.setCreated_at(LocalDateTime.now());
        loanApplication.setUpdated_at(LocalDateTime.now());
        loanApplication.setReview_message("Review message");
        loanApplication.setStatus(LoanApplication.Status.Application_Submitted);
        loanApplication.setUser(user);
        loanApplication.setProduct(product);
        loanApplication.setVendor(vendor);
    }

    @Test
    void testLoanApplicationFields() {
        assertEquals(1L, loanApplication.getApplication_id());
        assertEquals(5000, loanApplication.getAmount_required());
        assertEquals(12, loanApplication.getTenure());
        assertNotNull(loanApplication.getCreated_at());
        assertNotNull(loanApplication.getUpdated_at());
        assertEquals("Review message", loanApplication.getReview_message());
        assertEquals(LoanApplication.Status.Application_Submitted, loanApplication.getStatus());
        assertEquals(user, loanApplication.getUser());
        assertEquals(product, loanApplication.getProduct());
        assertEquals(vendor, loanApplication.getVendor());
    }

    @Test
    void testPrePersist() {
        LoanApplication newLoanApplication = new LoanApplication();
        newLoanApplication.onCreate();
        assertNotNull(newLoanApplication.getCreated_at());
        assertNotNull(newLoanApplication.getUpdated_at());
        assertEquals(newLoanApplication.getCreated_at(), newLoanApplication.getUpdated_at());
    }

    @Test
    void testPreUpdate() {
        loanApplication.onUpdate();
        assertNotNull(loanApplication.getUpdated_at());
        assertEquals(loanApplication.getUpdated_at().getDayOfYear(), LocalDateTime.now().getDayOfYear());
    }
}
