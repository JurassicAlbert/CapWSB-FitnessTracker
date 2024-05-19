package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    /**
     * Creates a new user.
     *
     * @param user the user to be created
     * @return the created user
     */
    @Override
    public User createUser(final User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    /**
     * Retrieves a user based on their ID.
     *
     * @param userId the ID of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Retrieves a user based on their email.
     *
     * @param email the email of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Retrieves all users.
     *
     * @return A list of all users
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to be deleted
     */
    @Override
    public void deleteUser(Long userId) {
        if (trainingRepository.existsByUserId(userId)) {
            throw new IllegalArgumentException("User with ID " + userId + " cannot be deleted because they have associated trainings.");
        }
        userRepository.deleteById(userId);
    }

    /**
     * Searches for users by part of their email address.
     *
     * @param emailPart the part of the email address to search for
     * @return A list of UserEmailDto whose emails contain the specified part
     */
    @Override
    public List<UserEmailDto> searchUsersByEmailPart(String emailPart) {
        return userRepository.findUserIdsAndEmailsByEmailPart(emailPart)
                .stream()
                .map(projection -> new UserEmailDto(projection.getId(), projection.getEmail()))
                .collect(Collectors.toList());
    }

    /**
     * Finds users older than the specified age.
     *
     * @param age the age threshold
     * @return A list of users older than the specified age
     */
    @Override
    public List<User> findUsersOlderThan(int age) {
        LocalDate maxBirthdate = LocalDate.now().minusYears(age);
        return userRepository.findUsersOlderThan(maxBirthdate);
    }

    /**
     * Updates an existing user.
     *
     * @param userId the ID of the user to be updated
     * @param user   the updated user information
     * @return the updated user
     */
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
