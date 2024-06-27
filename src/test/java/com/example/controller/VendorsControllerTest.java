package com.example.controller;

import com.example.model.Vendors;
import com.example.service.VendorsService;
import com.example.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class VendorsControllerTest {

    @Mock
    private VendorsService vendorsService;

    @InjectMocks
    private VendorsController vendorsController;

    private Vendors vendor;

    @BeforeEach
    void setUp() {
        vendor = new Vendors();
        vendor.setVendor_id(1L);
        // Set other properties of vendor
    }

    @Test
    void testRead() {
        List<Vendors> vendorsList = Arrays.asList(vendor);
        when(vendorsService.getAllVendors()).thenReturn(vendorsList);

        List<Vendors> result = vendorsController.read();
        assertEquals(1, result.size());
        assertEquals(vendor, result.get(0));
    }

    @Test
    void testReadOne() throws ResourceNotFoundException {
        when(vendorsService.getVendorById(1L)).thenReturn(vendor);

        Vendors result = vendorsController.readOne(1L);
        assertEquals(vendor, result);
    }

    @Test
    void testAdd() {
        when(vendorsService.addVendor(any(Vendors.class))).thenReturn(vendor);

        Vendors result = vendorsController.add(vendor);
        assertEquals(vendor, result);
    }

    @Test
    void testUpdate() throws ResourceNotFoundException {
        doNothing().when(vendorsService).updateVendor(anyLong(), any(Vendors.class));

        vendorsController.update(vendor, 1L);
        verify(vendorsService, times(1)).updateVendor(1L, vendor);
    }

    @Test
    void testDelete() throws ResourceNotFoundException {
        doNothing().when(vendorsService).deleteVendor(1L);

        vendorsController.delete(1L);
        verify(vendorsService, times(1)).deleteVendor(1L);
    }
}
