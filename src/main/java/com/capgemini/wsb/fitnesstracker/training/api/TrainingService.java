package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.CreateTrainingDto;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingDto;

import java.util.Date;
import java.util.List;

/**
 * Service interface for managing and retrieving {@link Training} entities.
 */
public interface TrainingService extends TrainingProvider {

    /**
     * Creates a new training.
     *
     * @param createTrainingDto the training to be created
     * @return the created training as DTO
     */
    TrainingDto createTraining(CreateTrainingDto createTrainingDto);

    /**
     * Retrieves all trainings.
     *
     * @return a list of all trainings as DTO
     */
    List<TrainingDto> findAllTrainings();

    /**
     * Retrieves all trainings for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of trainings for the specified user as DTO
     */
    List<TrainingDto> findTrainingsByUserId(Long userId);

    /**
     * Retrieves all trainings that ended after a specific date.
     *
     * @param date the date to compare
     * @return a list of trainings that ended after the specified date as DTO
     */
    List<TrainingDto> findTrainingsByEndTimeAfter(Date date);

    /**
     * Retrieves all trainings of a specific activity type.
     *
     * @param activityType the type of activity
     * @return a list of trainings of the specified activity type as DTO
     */
    List<TrainingDto> findTrainingsByActivityType(ActivityType activityType);

    /**
     * Updates an existing training.
     *
     * @param trainingId the ID of the training to be updated
     * @param createTrainingDto the updated training information
     * @return the updated training as DTO
     */
    TrainingDto updateTraining(Long trainingId, CreateTrainingDto createTrainingDto);
}
