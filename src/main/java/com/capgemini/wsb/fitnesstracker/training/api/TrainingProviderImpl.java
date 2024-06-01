package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link TrainingProvider}.
 */
@Service
@RequiredArgsConstructor
public class TrainingProviderImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    /**
     * Retrieves the user associated with a training based on its ID.
     * If the training with the given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located User, or {@link Optional#empty()} if not found
     */
    @Override
    public Optional<User> getTraining(Long trainingId) {
        return trainingRepository.findById(trainingId).map(Training::getUser);
    }

    /**
     * Retrieves all trainings.
     *
     * @return a list of all trainings
     */
    @Override
    public List<Training> getTrainings() {
        return trainingRepository.findAll();
    }
}
