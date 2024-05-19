package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link TrainingService}.
 */
@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final UserProvider userProvider;

    /**
     * Creates a new training.
     *
     * @param createTrainingDto the training to be created
     * @return the created training as DTO
     */
    @Override
    public TrainingDto createTraining(final CreateTrainingDto createTrainingDto) {
        User user = userProvider.getUser(createTrainingDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(createTrainingDto.getUserId()));
        Training training = trainingMapper.toEntity(createTrainingDto, user);
        return trainingMapper.toDto(trainingRepository.save(training));
    }

    /**
     * Retrieves a user based on the training's ID.
     *
     * @param trainingId the ID of the training to be retrieved
     * @return an {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    @Override
    public Optional<User> getTraining(final Long trainingId) {
        return trainingRepository.findById(trainingId)
                .map(Training::getUser);
    }

    /**
     * Retrieves all trainings.
     *
     * @return a list of all trainings as DTO
     */
    @Override
    public List<TrainingDto> findAllTrainings() {
        return trainingRepository.findAll()
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all trainings for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of trainings for the specified user as DTO
     */
    @Override
    public List<TrainingDto> findTrainingsByUserId(final Long userId) {
        return trainingRepository.findByUserId(userId)
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all trainings that ended after a specific date.
     *
     * @param date the date to compare
     * @return a list of trainings that ended after the specified date as DTO
     */
    @Override
    public List<TrainingDto> findTrainingsByEndTimeAfter(final Date date) {
        return trainingRepository.findByEndTimeAfter(date)
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all trainings of a specific activity type.
     *
     * @param activityType the type of activity
     * @return a list of trainings of the specified activity type as DTO
     */
    @Override
    public List<TrainingDto> findTrainingsByActivityType(final ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType)
                .stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates an existing training.
     *
     * @param trainingId the ID of the training to be updated
     * @param createTrainingDto the updated training information
     * @return the updated training as DTO
     */
    @Override
    public TrainingDto updateTraining(final Long trainingId, final CreateTrainingDto createTrainingDto) {
        User user = userProvider.getUser(createTrainingDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(createTrainingDto.getUserId()));
        return trainingRepository.findById(trainingId)
                .map(existingTraining -> {
                    existingTraining.setStartTime(createTrainingDto.getStartTime());
                    existingTraining.setEndTime(createTrainingDto.getEndTime());
                    existingTraining.setActivityType(createTrainingDto.getActivityType());
                    existingTraining.setDistance(createTrainingDto.getDistance());
                    existingTraining.setAverageSpeed(createTrainingDto.getAverageSpeed());
                    return trainingMapper.toDto(trainingRepository.save(existingTraining));
                })
                .orElseThrow(() -> new TrainingNotFoundException(trainingId));
    }
}
