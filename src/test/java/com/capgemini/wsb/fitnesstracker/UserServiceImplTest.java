package com.capgemini.wsb.fitnesstracker;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserEmailDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserEmailProjection;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_shouldCreateUser() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        User savedUser = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        savedUser.setId(1L);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("john.doe@example.com", createdUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUser_shouldReturnUserWhenExists() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUser(1L);

        assertTrue(foundUser.isPresent());
        assertEquals("john.doe@example.com", foundUser.get().getEmail());
    }

    @Test
    void getUser_shouldReturnEmptyWhenNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUser(1L);

        assertFalse(foundUser.isPresent());
    }

    @Test
    void getUserByEmail_shouldReturnUserWhenExists() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        user.setId(1L);
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserByEmail("john.doe@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("john.doe@example.com", foundUser.get().getEmail());
    }

    @Test
    void getUserByEmail_shouldReturnEmptyWhenNotExists() {
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserByEmail("john.doe@example.com");

        assertFalse(foundUser.isPresent());
    }

    @Test
    void findAllUsers_shouldReturnAllUsers() {
        User user1 = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        user1.setId(1L);
        User user2 = new User("Jane", "Doe", LocalDate.of(1992, 2, 2), "jane.doe@example.com");
        user2.setId(2L);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.findAllUsers();

        assertEquals(2, users.size());
    }

    @Test
    void deleteUser_shouldDeleteUser() {
        when(trainingRepository.existsByUserId(1L)).thenReturn(false);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_shouldThrowExceptionWhenUserHasTrainings() {
        when(trainingRepository.existsByUserId(1L)).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(1L);
        });

        String expectedMessage = "User with ID 1 cannot be deleted because they have associated trainings.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void searchUsersByEmailPart_shouldReturnUsers() {
        UserEmailProjection userProjection = mock(UserEmailProjection.class);
        when(userProjection.getId()).thenReturn(1L);
        when(userProjection.getEmail()).thenReturn("john.doe@example.com");

        when(userRepository.findUserIdsAndEmailsByEmailPart("doe")).thenReturn(Arrays.asList(userProjection));

        List<UserEmailDto> users = userService.searchUsersByEmailPart("doe");

        assertEquals(1, users.size());
        assertEquals("john.doe@example.com", users.get(0).email());
    }

    @Test
    void findUsersOlderThan_shouldReturnUsers() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        user.setId(1L);
        when(userRepository.findUsersOlderThan(any(LocalDate.class))).thenReturn(Arrays.asList(user));

        List<User> users = userService.findUsersOlderThan(30);

        assertEquals(1, users.size());
        assertTrue(users.get(0).getBirthdate().isBefore(LocalDate.now().minusYears(30)));
    }

    @Test
    void updateUser_shouldUpdateUser() {
        User existingUser = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        existingUser.setId(1L);
        User updatedUser = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        updatedUser.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = userService.updateUser(1L, updatedUser);

        assertEquals("john.doe@example.com", result.getEmail());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void updateUser_shouldThrowExceptionWhenUserNotFound() {
        User updatedUser = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, updatedUser));
    }
}
