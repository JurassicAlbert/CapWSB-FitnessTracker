package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.CreateTrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
     * Retrieves all trainings.
     *
     * @return A list of {@link TrainingDto} containing all trainings.
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.findAllTrainings();
    }

    /**
     * Retrieves all trainings for a specific user.
     *
     * @param userId The ID of the user to retrieve trainings for.
     * @return A list of {@link TrainingDto} containing trainings for the specified user.
     */
    @GetMapping("/{userId}")
    public List<TrainingDto> getTrainingsByUserId(@PathVariable Long userId) {
        return trainingService.findTrainingsByUserId(userId);
    }

    /**
     * Retrieves all trainings that ended after a specific date.
     *
     * @param afterTime The date to compare (in yyyy-MM-dd format).
     * @return A list of {@link TrainingDto} containing trainings that ended after the specified date.
     */
    @GetMapping("/finished/{afterTime}")
    public List<TrainingDto> getFinishedTrainingsAfter(@PathVariable String afterTime) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(afterTime);
            return trainingService.findTrainingsByEndTimeAfter(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    /**
     * Retrieves all trainings of a specific activity type.
     *
     * @param activityType The type of activity.
     * @return A list of {@link TrainingDto} containing trainings of the specified activity type.
     */
    @GetMapping("/activityType")
    public List<TrainingDto> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return trainingService.findTrainingsByActivityType(activityType);
    }

    /**
     * Creates a new training.
     *
     * @param createTrainingDto The {@link CreateTrainingDto} containing information about the new training.
     * @return The created {@link TrainingDto}.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDto createTraining(@RequestBody CreateTrainingDto createTrainingDto) {
        return trainingService.createTraining(createTrainingDto);
    }

    /**
     * Updates an existing training.
     *
     * @param trainingId The ID of the training to update.
     * @param createTrainingDto The {@link CreateTrainingDto} containing updated information.
     * @return The updated {@link TrainingDto}.
     */
    @PutMapping("/{trainingId}")
    public TrainingDto updateTraining(@PathVariable Long trainingId, @RequestBody CreateTrainingDto createTrainingDto) {
        return trainingService.updateTraining(trainingId, createTrainingDto);
    }
}
