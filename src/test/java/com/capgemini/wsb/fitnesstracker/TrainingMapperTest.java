package com.capgemini.wsb.fitnesstracker;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.CreateTrainingDto;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingMapper;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class TrainingMapperTest {

    private TrainingMapper trainingMapper;

    @BeforeEach
    void setUp() {
        trainingMapper = new TrainingMapper();
    }

    @Test
    void toDto_shouldMapTrainingToDto() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        Training training = new Training(user,
                java.util.Date.from(LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant()),
                java.util.Date.from(LocalDateTime.of(2024, 1, 1, 12, 0).atZone(ZoneId.systemDefault()).toInstant()),
                ActivityType.RUNNING, 10.0, 8.0);
        training.setId(1L);

        TrainingDto trainingDto = trainingMapper.toDto(training);

        assertNotNull(trainingDto);
        assertEquals(1L, trainingDto.getId());
        assertEquals(user.getId(), trainingDto.getUserId());
        assertEquals(training.getStartTime(), trainingDto.getStartTime());
        assertEquals(training.getEndTime(), trainingDto.getEndTime());
        assertEquals(training.getActivityType(), trainingDto.getActivityType());
        assertEquals(training.getDistance(), trainingDto.getDistance());
        assertEquals(training.getAverageSpeed(), trainingDto.getAverageSpeed());
    }

    @Test
    void toEntity_shouldMapDtoToTraining() {
        CreateTrainingDto createTrainingDto = new CreateTrainingDto(1L,
                java.util.Date.from(LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant()),
                java.util.Date.from(LocalDateTime.of(2024, 1, 1, 12, 0).atZone(ZoneId.systemDefault()).toInstant()),
                ActivityType.RUNNING, 10.0, 8.0);
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");

        Training training = trainingMapper.toEntity(createTrainingDto, user);

        assertNotNull(training);
        assertEquals(user, training.getUser());
        assertEquals(createTrainingDto.getStartTime(), training.getStartTime());
        assertEquals(createTrainingDto.getEndTime(), training.getEndTime());
        assertEquals(createTrainingDto.getActivityType(), training.getActivityType());
        assertEquals(createTrainingDto.getDistance(), training.getDistance());
        assertEquals(createTrainingDto.getAverageSpeed(), training.getAverageSpeed());
    }
}
