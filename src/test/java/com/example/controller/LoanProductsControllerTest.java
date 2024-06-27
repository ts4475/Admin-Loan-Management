package com.example.controller;

import com.example.model.LoanProducts;
import com.example.model.Vendors;
import com.example.service.LoanProductsService;
import com.example.dto.LoanProductDetailsDTO;
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
public class LoanProductsControllerTest {

    @Mock
    private LoanProductsService loanProductsService;

    @InjectMocks
    private LoanProductsController loanProductsController;

    private LoanProducts loanProduct;
    private Vendors vendor;
    private LoanProductDetailsDTO loanProductDetailsDTO;

    @BeforeEach
    void setUp() {
        loanProduct = new LoanProducts();
        loanProduct.setProduct_id(1L);
        // Set other properties of loanProduct

        vendor = new Vendors();
        vendor.setVendor_id(1L);
        // Set other properties of vendor

        loanProductDetailsDTO = new LoanProductDetailsDTO();
        // Set properties of loanProductDetailsDTO
    }

    @Test
    void testRead() {
        List<LoanProducts> loanProductsList = Arrays.asList(loanProduct);
        when(loanProductsService.getAllLoanProducts()).thenReturn(loanProductsList);

        List<LoanProducts> result = loanProductsController.read();
        assertEquals(1, result.size());
        assertEquals(loanProduct, result.get(0));
    }

    @Test
    void testReadOne() throws ResourceNotFoundException {
        when(loanProductsService.getLoanProductById(1L)).thenReturn(loanProduct);

        LoanProducts result = loanProductsController.readOne(1L);
        assertEquals(loanProduct, result);
    }

    @Test
    void testAdd() {
        when(loanProductsService.addLoanProduct(any(LoanProducts.class))).thenReturn(loanProduct);

        LoanProducts result = loanProductsController.add(loanProduct);
        assertEquals(loanProduct, result);
    }

    @Test
    void testUpdate() throws ResourceNotFoundException {
        doNothing().when(loanProductsService).updateLoanProduct(anyLong(), any(LoanProducts.class));

        loanProductsController.update(loanProduct, 1L);
        verify(loanProductsService, times(1)).updateLoanProduct(1L, loanProduct);
    }

    @Test
    void testDelete() throws ResourceNotFoundException {
        doNothing().when(loanProductsService).deleteLoanProduct(1L);

        loanProductsController.delete(1L);
        verify(loanProductsService, times(1)).deleteLoanProduct(1L);
    }

    @Test
    void testPartialUpdate() throws ResourceNotFoundException {
        doNothing().when(loanProductsService).partialUpdateLoanProduct(anyLong(), any(Vendors.class));

        ResponseEntity<Void> response = loanProductsController.partialUpdate(1L, vendor);
        assertEquals(ResponseEntity.ok().build(), response);
        verify(loanProductsService, times(1)).partialUpdateLoanProduct(1L, vendor);
    }

    @Test
    void testGetLoanProductsByVendorId() {
        List<LoanProducts> loanProductsList = Arrays.asList(loanProduct);
        when(loanProductsService.findByVendorId(1L)).thenReturn(loanProductsList);

        ResponseEntity<List<LoanProducts>> response = loanProductsController.getLoanProductsByVendorId(1L);
        assertEquals(ResponseEntity.ok(loanProductsList), response);
    }

    @Test
    void testGetLoanProductDetails() {
        when(loanProductsService.getLoanProductDetails(1L)).thenReturn(loanProductDetailsDTO);

        ResponseEntity<LoanProductDetailsDTO> response = loanProductsController.getLoanProductDetails(1L);
        assertEquals(ResponseEntity.ok(loanProductDetailsDTO), response);
    }
}
