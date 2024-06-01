package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.user.api.User;

import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves the user associated with a training based on its ID.
     * If the training with the given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located User, or {@link Optional#empty()} if not found
     */
    Optional<User> getTraining(Long trainingId);

    /**
     * Retrieves all trainings.
     *
     * @return a list of all trainings
     */
    List<Training> getTrainings();
}
