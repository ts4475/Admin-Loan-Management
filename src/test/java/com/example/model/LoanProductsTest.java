package com.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoanProductsTest {

    private LoanProducts loanProduct;
    private Vendors vendor;

    @BeforeEach
    void setUp() {
        vendor = new Vendors();
        vendor.setVendor_id(1L);
        // Set other properties of vendor...

        loanProduct = new LoanProducts();
        loanProduct.setProduct_id(1L);
        loanProduct.setProduct_name(LoanProducts.ProductName.Home);
        loanProduct.setProduct_interest_rate(6.5f);
        loanProduct.setProduct_processing_fee(1.0f);
        loanProduct.setVendor(vendor);
        loanProduct.setProduct_prepayment_charge(2);
        loanProduct.setProduct_prepayment_conditions("No prepayment within 6 months");
    }

    @Test
    void testLoanProductsFields() {
        assertEquals(1L, loanProduct.getProduct_id());
        assertEquals(LoanProducts.ProductName.Home, loanProduct.getProduct_name());
        assertEquals(6.5f, loanProduct.getProduct_interest_rate());
        assertEquals(1.0f, loanProduct.getProduct_processing_fee());
        assertEquals(vendor, loanProduct.getVendor());
        assertEquals(2, loanProduct.getProduct_prepayment_charge());
        assertEquals("No prepayment within 6 months", loanProduct.getProduct_prepayment_conditions());
    }

    @Test
    void testLoanProductsConstructor() {
        LoanProducts newLoanProduct = new LoanProducts(2L, LoanProducts.ProductName.Personal, 5.0f, 0.5f, vendor, 1, "No prepayment within 3 months");

        assertEquals(2L, newLoanProduct.getProduct_id());
        assertEquals(LoanProducts.ProductName.Personal, newLoanProduct.getProduct_name());
        assertEquals(5.0f, newLoanProduct.getProduct_interest_rate());
        assertEquals(0.5f, newLoanProduct.getProduct_processing_fee());
        assertEquals(vendor, newLoanProduct.getVendor());
        assertEquals(1, newLoanProduct.getProduct_prepayment_charge());
        assertEquals("No prepayment within 3 months", newLoanProduct.getProduct_prepayment_conditions());
    }
}
