package edu.mtisw.payrollbackend.services;

import edu.mtisw.payrollbackend.entities.UserEntity;
import edu.mtisw.payrollbackend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity();
        user.setRut("12345678-9");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole("user");
    }

    @Test
    void testLogin_UserNotFound() {
        when(userRepository.findByRut("12345678-9")).thenReturn(null);

        UserEntity result = userService.login("12345678-9", "password123");

        assertNull(result, "Se esperaba que el resultado fuera null cuando el usuario no existe.");
    }

    @Test
    void testLogin_WrongPassword() {
        when(userRepository.findByRut("12345678-9")).thenReturn(user);

        UserEntity result = userService.login("12345678-9", "wrongPassword");

        assertNull(result, "Se esperaba que el resultado fuera null cuando la contraseña es incorrecta.");
    }

    @Test
    void testRegister_UserAlreadyRegistered() {
        when(userRepository.findByRut("12345678-9")).thenReturn(user);

        UserEntity result = userService.register("12345678-9", "New User", "new@example.com", "newPassword", "user");

        assertNull(result, "Se esperaba que el resultado fuera null cuando el usuario ya está registrado.");
    }

    @Test
    void testRegister_Success() {
        when(userRepository.findByRut("12345678-9")).thenReturn(null);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        UserEntity result = userService.register("12345678-9", "Test User", "test@example.com", "password123", "user");

        assertNotNull(result, "Se esperaba que el registro fuera exitoso.");
        assertEquals("12345678-9", result.getRut());
        assertEquals("Test User", result.getName());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("password123", result.getPassword());
        assertEquals("user", result.getRole());
    }

    @Test
    void testLogin_Success() {
        when(userRepository.findByRut("12345678-9")).thenReturn(user);

        UserEntity result = userService.login("12345678-9", "password123");

        assertNotNull(result, "Se esperaba que el resultado no fuera null cuando la contraseña es correcta.");
        assertEquals("12345678-9", result.getRut(), "Se esperaba que el RUT del usuario coincida.");
        assertEquals("Test User", result.getName(), "Se esperaba que el nombre del usuario coincida.");
        assertEquals("test@example.com", result.getEmail(), "Se esperaba que el email del usuario coincida.");
        assertEquals("password123", result.getPassword(), "Se esperaba que la contraseña del usuario coincida.");
        assertEquals("user", result.getRole(), "Se esperaba que el rol del usuario coincida.");
    }
}

