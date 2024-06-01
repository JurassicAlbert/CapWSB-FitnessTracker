package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserEmailDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserHasTrainingsException;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Long.valueOf;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/simple")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ok(userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList());
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsersDetails() {
        return ok(userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList());
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        return userService.getUser(userId)
                .map(user -> ok(userMapper.toDto(user)))
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        return userService.createUser(user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<UserEmailDto>> searchUsersByEmailPart(@RequestParam String emailPart) {
        List<UserEmailDto> users = userService.searchUsersByEmailPart(emailPart);
        return ok(users);
    }


    @GetMapping("/email")
    public List<UserEmailDto> getByEmail(@RequestParam String email) {
        final Optional<User> userByEmail = userService.getUserByEmail(email);
        if(userByEmail.isEmpty()) {
            throw new UserNotFoundException(email);
        }
        return userByEmail.stream().map(userMapper::toEmailDto).collect(toList());
    }

    @GetMapping("/search/age")
    public ResponseEntity<List<UserDto>> findUsersOlderThan(@RequestParam int age) {
        List<UserDto> users = userService.findUsersOlderThan(age)
                .stream()
                .map(userMapper::toDto)
                .collect(toList());
        return ok(users);
    }

    @GetMapping("/older/{time}")
    public ResponseEntity<List<UserDto>> getUsersOlderThanGivenAge(@PathVariable LocalDate time) {
        final List<User> olderUsers = userService.getUsersOlderThanDate(time);
        if(olderUsers.isEmpty()) {
            throw new UserNotFoundException(valueOf(String.valueOf(time)));
        }
        return ok(olderUsers.stream().map(userMapper::toDto).collect(toList()));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.updateUser(userId, user);
        return ok(updatedUser);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserHasTrainingsException.class)
    public ResponseEntity<String> handleUserHasTrainingsException(UserHasTrainingsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
