package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserHasTrainingsException;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    /**
     * Retrieves all users.
     *
     * @return A list of {@link UserDto} containing ID and email of all users.
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves detailed information about a specific user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The {@link UserDto} containing detailed information about the user.
     */
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUser(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * Creates a new user.
     *
     * @param userDto The {@link UserDto} containing information about the new user.
     * @return The created {@link User}.
     */
    @PostMapping
    public User addUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return userService.createUser(user);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete.
     */
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    /**
     * Searches for users by part of their email address.
     *
     * @param emailPart The part of the email address to search for.
     * @return A list of {@link UserEmailDto} containing users that match the email part.
     */
    @GetMapping("/search/email")
    public List<UserEmailDto> searchUsersByEmailPart(@RequestParam String emailPart) {
        return userService.searchUsersByEmailPart(emailPart);
    }

    /**
     * Finds users older than the specified age.
     *
     * @param age The age threshold.
     * @return A list of {@link UserDto} containing users older than the specified age.
     */
    @GetMapping("/search/age")
    public List<UserDto> findUsersOlderThan(@RequestParam int age) {
        return userService.findUsersOlderThan(age)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing user.
     *
     * @param userId  The ID of the user to update.
     * @param userDto The {@link UserDto} containing updated information.
     * @return The updated {@link User}.
     */
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return userService.updateUser(userId, user);
    }

    @ExceptionHandler(UserHasTrainingsException.class)
    public ResponseEntity<String> handleUserHasTrainingsException(UserHasTrainingsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
