package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Managers;
import com.example.model.Users;
import com.example.repository.ManagersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagersServiceTest {

    @Mock
    private ManagersRepository managersRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ManagersService managersService;

    private Managers manager;
    private Users user;

    @BeforeEach
    void setUp() {
        user = new Users();
        user.setUser_id(1L);

        manager = new Managers();
        manager.setManager_id(1L);
        manager.setUser(user);
        manager.setAssigned_customers("Customer1, Customer2");
    }

    @Test
    void testGetAllManagers() {
        List<Managers> managersList = Arrays.asList(manager);
        when(managersRepository.findAll()).thenReturn(managersList);

        List<Managers> result = managersService.getAllManagers();
        assertEquals(1, result.size());
        assertEquals(manager, result.get(0));
    }

    @Test
    void testGetManagerById() throws ResourceNotFoundException {
        when(managersRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        Managers result = managersService.getManagerById(1L);
        assertEquals(manager, result);
    }

    @Test
    void testGetManagerByIdNotFound() {
        when(managersRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> managersService.getManagerById(1L));
    }

    @Test
    void testAddManager() {
        when(restTemplate.getForObject(eq("http://localhost:8082/users/readOne/1"), eq(Users.class))).thenReturn(user);
        when(managersRepository.save(any(Managers.class))).thenReturn(manager);

        Managers result = managersService.addManager(manager);
        assertEquals(manager, result);
    }

    @Test
    void testAddManagerUserNotFound() {
        when(restTemplate.getForObject(eq("http://localhost:8082/users/readOne/1"), eq(Users.class))).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> managersService.addManager(manager));
        assertEquals("User not found with ID: 1", exception.getMessage());
    }

    @Test
    void testUpdateManager() throws ResourceNotFoundException {
        Users newUser = new Users();
        newUser.setUser_id(2L);
        Managers newManager = new Managers();
        newManager.setUser(newUser);

        when(managersRepository.findById(anyLong())).thenReturn(Optional.of(manager));
        when(restTemplate.getForObject(eq("http://localhost:8082/users/readOne/2"), eq(Users.class))).thenReturn(newUser);
        when(managersRepository.save(any(Managers.class))).thenReturn(manager);

        managersService.updateManager(1L, newManager);

        verify(managersRepository).save(manager);
        assertEquals(newUser, manager.getUser());
    }

    @Test
    void testUpdateManagerNotFound() {
        when(managersRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> managersService.updateManager(1L, manager));
    }

    @Test
    void testDeleteManager() throws ResourceNotFoundException {
        when(managersRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        managersService.deleteManager(1L);

        verify(managersRepository).delete(manager);
    }

    @Test
    void testDeleteManagerNotFound() {
        when(managersRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> managersService.deleteManager(1L));
    }

    @Test
    void testFindByUserId() {
        when(managersRepository.findByUserUserId(anyLong())).thenReturn(manager);

        Managers result = managersService.findByUserId(1L);
        assertEquals(manager, result);
    }

    @Test
    void testGetManagerByVendorId() {
        when(managersRepository.findByVendorVendorId(anyLong())).thenReturn(Optional.of(manager));

        Managers result = managersService.getManagerByVendorId(1L);
        assertEquals(manager, result);
    }

    @Test
    void testGetManagerByVendorIdNotFound() {
        when(managersRepository.findByVendorVendorId(anyLong())).thenReturn(Optional.empty());

        Managers result = managersService.getManagerByVendorId(1L);
        assertNull(result);
    }
}
