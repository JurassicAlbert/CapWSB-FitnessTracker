package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for managing {@link Training} entities.
 */
public interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Checks if any training exists for a given user ID.
     *
     * @param userId the ID of the user to check
     * @return true if a training exists for the user, false otherwise
     */
    @Query("SELECT COUNT(t) > 0 FROM Training t WHERE t.user.id = :userId")
    boolean existsByUserId(@Param("userId") Long userId);
}
