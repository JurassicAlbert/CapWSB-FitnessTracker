package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * REST controller for managing trainings.
 */
@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    /**
     * Creates a new training.
     *
     * @param createTrainingDto the training to be created
     * @return the created training as DTO
     */
    @PostMapping
    public TrainingDto createTraining(@RequestBody CreateTrainingDto createTrainingDto) {
        return trainingService.createTraining(createTrainingDto);
    }

    /**
     * Retrieves all trainings.
     *
     * @return a list of all trainings as DTO
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings();
    }

    /**
     * Retrieves all trainings for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of trainings for the specified user as DTO
     */
    @GetMapping("/user/{userId}")
    public List<TrainingDto> getTrainingsByUserId(@PathVariable Long userId) {
        return trainingService.findTrainingsByUserId(userId);
    }

    /**
     * Retrieves all trainings that ended after a specific date.
     *
     * @param date the date to compare
     * @return a list of trainings that ended after the specified date as DTO
     */
    @GetMapping("/endtime")
    public List<TrainingDto> getTrainingsByEndTimeAfter(@RequestParam Date date) {
        return trainingService.findTrainingsByEndTimeAfter(date);
    }

    /**
     * Retrieves all trainings of a specific activity type.
     *
     * @param activityType the type of activity
     * @return a list of trainings of the specified activity type as DTO
     */
    @GetMapping("/activity")
    public List<TrainingDto> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return trainingService.findTrainingsByActivityType(activityType);
    }

    /**
     * Updates an existing training.
     *
     * @param trainingId the ID of the training to be updated
     * @param createTrainingDto the updated training information
     * @return the updated training as DTO
     */
    @PutMapping("/{trainingId}")
    public TrainingDto updateTraining(@PathVariable Long trainingId, @RequestBody CreateTrainingDto createTrainingDto) {
        return trainingService.updateTraining(trainingId, createTrainingDto);
    }
}
