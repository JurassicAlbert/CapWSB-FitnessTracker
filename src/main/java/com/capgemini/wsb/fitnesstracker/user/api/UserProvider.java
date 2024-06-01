package com.capgemini.wsb.fitnesstracker.user.api;

import java.util.List;
import java.util.Optional;

/**
 * Interface for providing user-related operations.
 */
public interface UserProvider {

    /**
     * Retrieves a user based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param userId id of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUser(Long userId);

    /**
     * Retrieves a user based on their email.
     * If the user with given email is not found, then {@link Optional#empty()} will be returned.
     *
     * @param email The email of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Retrieves all users.
     *
     * @return A list of all users
     */
    List<User> findAllUsers();

    /**
     * Search users by email part.
     *
     * @param emailPart The part of the email to search
     * @return A list of UserEmailDto whose emails contain the specified part.
     */
    List<UserEmailDto> searchUsersByEmailPart(String emailPart);

    /**
     * Find users older than a specified age.
     *
     * @param age The age to compare
     * @return A list of users older than the specified age
     */
    List<User> findUsersOlderThan(int age);
}
