import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import src.model.User;
import src.repository.UserRepository;
import src.service.UserService;
import java.util.Optional;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.createUser(user);

        assertNotNull(registeredUser);
        assertEquals("test@example.com", registeredUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testLoginWithValidCredentials() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("hashed_password");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);
        when(passwordEncoder.matches("password", "hashed_password")).thenReturn(true);

        User authenticatedUser = userService.authenticate("test@example.com", "password");

        assertNotNull(authenticatedUser);
        assertEquals("test@example.com", authenticatedUser.getEmail());
    }

    @Test
    void testLoginWithInvalidCredentials() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        User authenticatedUser = userService.authenticate("test@example.com", "wrong_password");

        assertNull(authenticatedUser);
    }
}
