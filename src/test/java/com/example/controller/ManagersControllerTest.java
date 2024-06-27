package com.example.controller;

import com.example.model.Managers;
import com.example.service.ManagersService;
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

public class ManagersControllerTest {

    @Mock
    private ManagersService managersService;

    @InjectMocks
    private ManagersController managersController;

    private Managers manager;

    @BeforeEach
    void setUp() {
        manager = new Managers();
        manager.setManager_id(1L);
        // Set other properties of manager
    }

    @Test
    void testRead() {
        List<Managers> managersList = Arrays.asList(manager);
        when(managersService.getAllManagers()).thenReturn(managersList);

        List<Managers> result = managersController.read();
        assertEquals(1, result.size());
        assertEquals(manager, result.get(0));
    }

    @Test
    void testReadOne() throws ResourceNotFoundException {
        when(managersService.getManagerById(1L)).thenReturn(manager);

        Managers result = managersController.readOne(1L);
        assertEquals(manager, result);
    }

    @Test
    void testAdd() {
        when(managersService.addManager(any(Managers.class))).thenReturn(manager);

        Managers result = managersController.add(manager);
        assertEquals(manager, result);
    }

    @Test
    void testUpdate() throws ResourceNotFoundException {
        doNothing().when(managersService).updateManager(anyLong(), any(Managers.class));

        managersController.update(manager, 1L);
        verify(managersService, times(1)).updateManager(1L, manager);
    }

    @Test
    void testDelete() throws ResourceNotFoundException {
        doNothing().when(managersService).deleteManager(1L);

        managersController.delete(1L);
        verify(managersService, times(1)).deleteManager(1L);
    }

    @Test
    void testGetManagerByUserId() {
        when(managersService.findByUserId(1L)).thenReturn(manager);

        ResponseEntity<Managers> response = managersController.getManagerByUserId(1L);
        assertEquals(ResponseEntity.ok(manager), response);
    }

    @Test
    void testGetManagerByVendorId() {
        when(managersService.getManagerByVendorId(1L)).thenReturn(manager);

        ResponseEntity<Managers> response = managersController.getManagerByVendorId(1L);
        assertEquals(ResponseEntity.ok(manager), response);
    }

    @Test
    void testGetManagerByVendorId_NotFound() {
        when(managersService.getManagerByVendorId(1L)).thenReturn(null);

        ResponseEntity<Managers> response = managersController.getManagerByVendorId(1L);
        assertEquals(ResponseEntity.notFound().build(), response);
    }
}
