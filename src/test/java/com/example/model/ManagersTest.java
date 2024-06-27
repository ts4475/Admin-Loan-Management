package com.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ManagersTest {

    private Managers manager;
    private Users user;
    private Vendors vendor;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setUser_id(1L);
        // Set other properties of user...

        vendor = new Vendors();
        vendor.setVendor_id(1L);
        // Set other properties of vendor...

        manager = new Managers();
        manager.setManager_id(1L);
        manager.setUser(user);
        manager.setVendor(vendor);
        manager.setAssigned_customers("[\"customer1\", \"customer2\"]");
        manager.setCreated_at(LocalDateTime.now());
        manager.setUpdated_at(LocalDateTime.now());
    }

    @Test
    void testManagersFields() {
        assertEquals(1L, manager.getManager_id());
        assertEquals(user, manager.getUser());
        assertEquals(vendor, manager.getVendor());
        assertEquals("[\"customer1\", \"customer2\"]", manager.getAssigned_customers());
        assertNotNull(manager.getCreated_at());
        assertNotNull(manager.getUpdated_at());
    }

    @Test
    void testManagersConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Managers newManager = new Managers(2L, user, vendor, "[\"customer3\"]", now, now);

        assertEquals(2L, newManager.getManager_id());
        assertEquals(user, newManager.getUser());
        assertEquals(vendor, newManager.getVendor());
        assertEquals("[\"customer3\"]", newManager.getAssigned_customers());
        assertEquals(now, newManager.getCreated_at());
        assertEquals(now, newManager.getUpdated_at());
    }

    @Test
    void testPrePersist() {
        Managers newManager = new Managers();
        newManager.onCreate();
        assertNotNull(newManager.getCreated_at());
    }

    @Test
    void testPreUpdate() {
        manager.onUpdate();
        assertNotNull(manager.getUpdated_at());
    }
}
