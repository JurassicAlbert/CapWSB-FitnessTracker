package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Repository for User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    default Optional<User> findByEmail(@Param("email") String email) {
        return findAll().stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst();
    }

    /**
     * Finds users older than a given birthdate.
     *
     * @param maxBirthdate The birthdate threshold.
     * @return A list of users older than the given birthdate.
     */
    @Query("SELECT u FROM User u WHERE u.birthdate < :maxBirthdate")
    List<User> findUsersOlderThan(@Param("maxBirthdate") LocalDate maxBirthdate);

    /**
     * Finds users by part of their email address.
     *
     * @param emailPart The part of the email to search.
     * @return A list of users whose emails contain the specified part.
     */
    @Query("SELECT u.id as id, u.email as email FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :emailPart, '%'))")
    List<UserEmailProjection> findUserIdsAndEmailsByEmailPart(@Param("emailPart") String emailPart);
}
