package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

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

    /**
     * Finds all trainings for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of trainings for the specified user
     */
    List<Training> findByUserId(Long userId);

    /**
     * Finds all trainings that ended after the specified date.
     *
     * @param date the date to compare
     * @return a list of trainings that ended after the specified date
     */
    List<Training> findByEndTimeAfter(Date date);

    /**
     * Finds all trainings of a specific activity type.
     *
     * @param activityType the type of activity
     * @return a list of trainings of the specified activity type
     */
    List<Training> findByActivityType(ActivityType activityType);
}
