package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interface for modifying operations on {@link User} entities.
 */
public interface UserService {

    /**
     * Creates a new user.
     *
     * @param user the user to be created
     * @return the created user
     */
    User createUser(User user);

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to be deleted
     */
    void deleteUser(Long userId);

    /**
     * Updates an existing user.
     *
     * @param userId the ID of the user to be updated
     * @param user   the updated user information
     * @return the updated user
     */
    User updateUser(Long userId, User user);

    /**
     * Searches for users by part of their email address.
     *
     * @param emailPart The part of the email address to search for.
     * @return A list of UserEmailDto whose emails contain the specified part.
     */
    List<UserEmailDto> searchUsersByEmailPart(String emailPart);

    /**
     * Finds users older than the specified age.
     *
     * @param age The age to compare.
     * @return A list of users older than the specified age.
     */
    List<User> findUsersOlderThan(int age);

    /**
     * Finds users older than the specified date.
     *
     * @param providedDate The age to compare.
     * @return A list of users older than the specified date (time).
     */
    List<User> getUsersOlderThanDate(LocalDate providedDate);

    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     */
    List<User> findAllUsers();

    /**
     * Retrieves a user based on their ID.
     *
     * @param userId the ID of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUser(Long userId);

    /**
     * Retrieves a user based on their email.
     *
     * @param email the email of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUserByEmail(String email);
}
