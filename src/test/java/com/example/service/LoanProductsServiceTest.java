package com.example.service;

import com.example.dto.LoanProductDetailsDTO;
import com.example.exception.ResourceNotFoundException;
import com.example.model.LoanProducts;
import com.example.model.Vendors;
import com.example.repository.LoanProductsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanProductsServiceTest {

    @Mock
    private LoanProductsRepository loanProductsRepository;

    @InjectMocks
    private LoanProductsService loanProductsService;

    private LoanProducts loanProduct;
    private Vendors vendor;

    @BeforeEach
    void setUp() {
        vendor = new Vendors();
        vendor.setVendor_id(1L);
        // Set other properties of vendor

        loanProduct = new LoanProducts();
        loanProduct.setProduct_id(1L);
        loanProduct.setProduct_name(LoanProducts.ProductName.Home);
        loanProduct.setProduct_interest_rate(6.5f);
        loanProduct.setProduct_processing_fee(1.0f);
        loanProduct.setVendor(vendor);
        loanProduct.setProduct_prepayment_charge(2);
        loanProduct.setProduct_prepayment_conditions("No prepayment within 6 months");
        // Set other properties of loanProduct
    }

    @Test
    void testGetAllLoanProducts() {
        List<LoanProducts> loanProductsList = Arrays.asList(loanProduct);
        when(loanProductsRepository.findAll()).thenReturn(loanProductsList);

        List<LoanProducts> result = loanProductsService.getAllLoanProducts();
        assertEquals(1, result.size());
        assertEquals(loanProduct, result.get(0));
    }

    @Test
    void testGetLoanProductById() throws ResourceNotFoundException {
        when(loanProductsRepository.findById(1L)).thenReturn(Optional.of(loanProduct));

        LoanProducts result = loanProductsService.getLoanProductById(1L);
        assertEquals(loanProduct, result);
    }

    @Test
    void testGetLoanProductById_NotFound() {
        when(loanProductsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> loanProductsService.getLoanProductById(1L));
    }

    @Test
    void testAddLoanProduct() {
        when(loanProductsRepository.save(any(LoanProducts.class))).thenReturn(loanProduct);

        LoanProducts result = loanProductsService.addLoanProduct(loanProduct);
        assertEquals(loanProduct, result);
    }

    @Test
    void testUpdateLoanProduct() throws ResourceNotFoundException {
        when(loanProductsRepository.findById(1L)).thenReturn(Optional.of(loanProduct));
        when(loanProductsRepository.save(any(LoanProducts.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LoanProducts newLoanProduct = new LoanProducts();
        newLoanProduct.setProduct_name(LoanProducts.ProductName.Personal);
        newLoanProduct.setProduct_interest_rate(7.0f);
        newLoanProduct.setProduct_processing_fee(1.5f);
        newLoanProduct.setVendor(vendor);
        newLoanProduct.setProduct_prepayment_charge(3);
        newLoanProduct.setProduct_prepayment_conditions("No prepayment within 3 months");

        loanProductsService.updateLoanProduct(1L, newLoanProduct);

        assertEquals(LoanProducts.ProductName.Personal, loanProduct.getProduct_name());
        assertEquals(7.0f, loanProduct.getProduct_interest_rate());
        assertEquals(1.5f, loanProduct.getProduct_processing_fee());
        assertEquals(vendor, loanProduct.getVendor());
        assertEquals(3, loanProduct.getProduct_prepayment_charge());
        assertEquals("No prepayment within 3 months", loanProduct.getProduct_prepayment_conditions());
    }
    
    
    @Test
    void testUpdateLoanProduct_NotFound() {
        when(loanProductsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> loanProductsService.updateLoanProduct(1L, loanProduct));
    }

    @Test
    void testDeleteLoanProduct() throws ResourceNotFoundException {
        when(loanProductsRepository.findById(1L)).thenReturn(Optional.of(loanProduct));
        doNothing().when(loanProductsRepository).delete(loanProduct);

        loanProductsService.deleteLoanProduct(1L);

        verify(loanProductsRepository, times(1)).delete(loanProduct);
    }

    @Test
    void testDeleteLoanProduct_NotFound() {
        when(loanProductsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> loanProductsService.deleteLoanProduct(1L));
    }

    @Test
    void testPartialUpdateLoanProduct() throws ResourceNotFoundException {
        when(loanProductsRepository.findById(1L)).thenReturn(Optional.of(loanProduct));
        when(loanProductsRepository.save(any(LoanProducts.class))).thenReturn(loanProduct);

        loanProductsService.partialUpdateLoanProduct(1L, vendor);

        verify(loanProductsRepository, times(1)).save(loanProduct);
        assertEquals(vendor, loanProduct.getVendor());
    }

    @Test
    void testFindByVendorId() {
        List<LoanProducts> loanProductsList = Arrays.asList(loanProduct);
        when(loanProductsRepository.findByVendorVendorId(1L)).thenReturn(loanProductsList);

        List<LoanProducts> result = loanProductsService.findByVendorId(1L);
        assertEquals(1, result.size());
        assertEquals(loanProduct, result.get(0));
    }

    @Test
    void testGetLoanProductDetails() {
        when(loanProductsRepository.findById(1L)).thenReturn(Optional.of(loanProduct));

        LoanProductDetailsDTO result = loanProductsService.getLoanProductDetails(1L);
        assertEquals(loanProduct.getProduct_interest_rate(), result.getProduct_interest_rate());
        assertEquals(loanProduct.getProduct_prepayment_charge(), result.getProduct_prepayment_charge());
    }

    @Test
    void testGetLoanProductDetails_NotFound() {
        when(loanProductsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> loanProductsService.getLoanProductDetails(1L));
    }
}
