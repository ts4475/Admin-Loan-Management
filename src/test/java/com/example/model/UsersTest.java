package com.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsersTest {

    private Users user;
    private Set<LoanApplication> loanApplications;

    @BeforeEach
    void setUp() {
        loanApplications = new HashSet<>();

        user = new Users();
        user.setUser_id(1L);
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setFirst_name("John");
        user.setLast_name("Doe");
        user.setPhone("1234567890");
        user.setAddress("123 Main St");
        user.setPin("12345");
        user.setSecurityQuestion(Users.SecurityQuestion.FIRST_SCHOOL);
        user.setSecurityAnswer("Greenwood High");
        user.setPan("ABCDE1234F");
        user.setDob(LocalDate.of(1990, 1, 1));
        user.setAnnualIncome(50000);
        user.setRole(Users.RoleEnum.CUSTOMER);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setLoanApplications(loanApplications);
    }

    @Test
    void testUsersFields() {
        assertEquals(1L, user.getUser_id());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("John", user.getFirst_name());
        assertEquals("Doe", user.getLast_name());
        assertEquals("1234567890", user.getPhone());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("12345", user.getPin());
        assertEquals(Users.SecurityQuestion.FIRST_SCHOOL, user.getSecurityQuestion());
        assertEquals("Greenwood High", user.getSecurityAnswer());
        assertEquals("ABCDE1234F", user.getPan());
        assertEquals(LocalDate.of(1990, 1, 1), user.getDob());
        assertEquals(50000, user.getAnnualIncome());
        assertEquals(Users.RoleEnum.CUSTOMER, user.getRole());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
        assertEquals(loanApplications, user.getLoanApplications());
    }

    @Test
    void testUsersConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Users newUser = new Users(2L, "new@example.com", "newpassword", "Jane", "Smith", "0987654321", "456 Main St", "67890",
                Users.SecurityQuestion.GRANDMOTHER_NAME, "Mary", "WXYZ5678K", LocalDate.of(1995, 5, 15), 75000,
                Users.RoleEnum.ADMIN, now, now, loanApplications);

        assertEquals(2L, newUser.getUser_id());
        assertEquals("new@example.com", newUser.getEmail());
        assertEquals("newpassword", newUser.getPassword());
        assertEquals("Jane", newUser.getFirst_name());
        assertEquals("Smith", newUser.getLast_name());
        assertEquals("0987654321", newUser.getPhone());
        assertEquals("456 Main St", newUser.getAddress());
        assertEquals("67890", newUser.getPin());
        assertEquals(Users.SecurityQuestion.GRANDMOTHER_NAME, newUser.getSecurityQuestion());
        assertEquals("Mary", newUser.getSecurityAnswer());
        assertEquals("WXYZ5678K", newUser.getPan());
        assertEquals(LocalDate.of(1995, 5, 15), newUser.getDob());
        assertEquals(75000, newUser.getAnnualIncome());
        assertEquals(Users.RoleEnum.ADMIN, newUser.getRole());
        assertEquals(now, newUser.getCreatedAt());
        assertEquals(now, newUser.getUpdatedAt());
        assertEquals(loanApplications, newUser.getLoanApplications());
    }

    @Test
    void testPrePersist() {
        Users newUser = new Users();
        newUser.onCreate();
        assertNotNull(newUser.getCreatedAt());
        assertNotNull(newUser.getUpdatedAt());
        assertEquals(newUser.getCreatedAt(), newUser.getUpdatedAt());
    }

    @Test
    void testPreUpdate() {
        user.onUpdate();
        assertNotNull(user.getUpdatedAt());
    }
}
