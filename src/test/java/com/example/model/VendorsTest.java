package com.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VendorsTest {

    private Vendors vendor;

    @BeforeEach
    void setUp() {
        vendor = new Vendors();
        vendor.setVendor_id(1L);
        vendor.setVendor_name("Test Vendor");
        vendor.setContact_phone("1234567890");
        vendor.setContact_email("vendor@example.com");
        vendor.setVendor_logo("logo.png");
    }

    @Test
    void testVendorsFields() {
        assertEquals(1L, vendor.getVendor_id());
        assertEquals("Test Vendor", vendor.getVendor_name());
        assertEquals("1234567890", vendor.getContact_phone());
        assertEquals("vendor@example.com", vendor.getContact_email());
        assertEquals("logo.png", vendor.getVendor_logo());
    }

    @Test
    void testVendorsConstructor() {
        Vendors newVendor = new Vendors(2L, "New Vendor", "0987654321", "newvendor@example.com", "newlogo.png");

        assertEquals(2L, newVendor.getVendor_id());
        assertEquals("New Vendor", newVendor.getVendor_name());
        assertEquals("0987654321", newVendor.getContact_phone());
        assertEquals("newvendor@example.com", newVendor.getContact_email());
        assertEquals("newlogo.png", newVendor.getVendor_logo());
    }
}
