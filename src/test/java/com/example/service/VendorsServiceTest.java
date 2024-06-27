package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Vendors;
import com.example.repository.VendorsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendorsServiceTest {

    @Mock
    private VendorsRepository vendorsRepository;

    @InjectMocks
    private VendorsService vendorsService;

    private Vendors vendor;

    @BeforeEach
    void setUp() {
        vendor = new Vendors();
        vendor.setVendor_id(1L);
        vendor.setVendor_name("Vendor 1");
        vendor.setContact_phone("1234567890");
        vendor.setContact_email("vendor1@example.com");
        vendor.setVendor_logo("logo.png");
    }

    @Test
    void testGetAllVendors() {
        List<Vendors> vendorsList = Arrays.asList(vendor);
        when(vendorsRepository.findAll()).thenReturn(vendorsList);

        List<Vendors> result = vendorsService.getAllVendors();
        assertEquals(1, result.size());
        assertEquals(vendor, result.get(0));
    }

    @Test
    void testGetVendorById() throws ResourceNotFoundException {
        when(vendorsRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        Vendors result = vendorsService.getVendorById(1L);
        assertEquals(vendor, result);
    }

    @Test
    void testGetVendorByIdNotFound() {
        when(vendorsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vendorsService.getVendorById(1L));
    }

    @Test
    void testAddVendor() {
        when(vendorsRepository.save(any(Vendors.class))).thenReturn(vendor);

        Vendors result = vendorsService.addVendor(vendor);
        assertEquals(vendor, result);
    }

    @Test
    void testUpdateVendor() throws ResourceNotFoundException {
        Vendors newVendor = new Vendors();
        newVendor.setVendor_name("Updated Vendor");
        newVendor.setContact_phone("0987654321");
        newVendor.setContact_email("updated@example.com");
        newVendor.setVendor_logo("updated_logo.png");

        when(vendorsRepository.findById(anyLong())).thenReturn(Optional.of(vendor));
        when(vendorsRepository.save(any(Vendors.class))).thenReturn(vendor);

        vendorsService.updateVendor(1L, newVendor);

        verify(vendorsRepository).save(vendor);
        assertEquals("Updated Vendor", vendor.getVendor_name());
        assertEquals("0987654321", vendor.getContact_phone());
        assertEquals("updated@example.com", vendor.getContact_email());
        assertEquals("updated_logo.png", vendor.getVendor_logo());
    }

    @Test
    void testUpdateVendorNotFound() {
        when(vendorsRepository.findById(anyLong())).thenReturn(Optional.empty());

        Vendors newVendor = new Vendors();
        assertThrows(ResourceNotFoundException.class, () -> vendorsService.updateVendor(1L, newVendor));
    }

    @Test
    void testDeleteVendor() throws ResourceNotFoundException {
        when(vendorsRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        vendorsService.deleteVendor(1L);

        verify(vendorsRepository).delete(vendor);
    }

    @Test
    void testDeleteVendorNotFound() {
        when(vendorsRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vendorsService.deleteVendor(1L));
    }
}
