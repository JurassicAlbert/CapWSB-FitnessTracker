package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.user.api.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of UserService and UserProvider.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;

    @Override
    public User createUser(final User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long userId) {
        if (trainingRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("User with ID " + userId + " cannot be deleted because they have associated trainings.");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserEmailDto> searchUsersByEmailPart(String emailPart) {
        return userRepository.findUserIdsAndEmailsByEmailPart(emailPart)
                .stream()
                .map(projection -> new UserEmailDto(projection.getId(), projection.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findUsersOlderThan(int age) {
        LocalDate maxBirthdate = LocalDate.now().minusYears(age);
        return userRepository.findUsersOlderThan(maxBirthdate);
    }

    public List<User> getUsersOlderThanDate(final LocalDate time) {
        final List<User> olderUsers = new ArrayList<>();
        final List<User> allUsers = userRepository.findAll();
        if(!allUsers.isEmpty()) {
            allUsers.forEach(user -> {
                if(user.getBirthdate().isBefore(time)) {
                    olderUsers.add(user);
                }
            });
        }
        return olderUsers;
    }

    @Override
    public User updateUser(Long userId, User user) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    existingUser.setBirthdate(user.getBirthdate());
                    existingUser.setEmail(user.getEmail());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
