package com.capgemini.wsb.fitnesstracker;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.internal.*;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @Mock
    private UserProvider userProvider;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTraining_shouldCreateTraining() {
        CreateTrainingDto createTrainingDto = new CreateTrainingDto(1L,
                Date.from(LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.of(2024, 1, 1, 12, 0).atZone(ZoneId.systemDefault()).toInstant()),
                ActivityType.RUNNING, 10.0, 8.0);
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        Training training = new Training(user, createTrainingDto.getStartTime(), createTrainingDto.getEndTime(), createTrainingDto.getActivityType(), createTrainingDto.getDistance(), createTrainingDto.getAverageSpeed());
        TrainingDto trainingDto = new TrainingDto(1L, user.getId(), createTrainingDto.getStartTime(), createTrainingDto.getEndTime(), createTrainingDto.getActivityType(), createTrainingDto.getDistance(), createTrainingDto.getAverageSpeed());

        when(userProvider.getUser(anyLong())).thenReturn(Optional.of(user));
        when(trainingMapper.toEntity(createTrainingDto, user)).thenReturn(training);
        when(trainingRepository.save(any(Training.class))).thenReturn(training);
        when(trainingMapper.toDto(training)).thenReturn(trainingDto);

        TrainingDto createdTrainingDto = trainingService.createTraining(createTrainingDto);

        assertNotNull(createdTrainingDto);
        assertEquals(trainingDto, createdTrainingDto);
        verify(trainingRepository, times(1)).save(training);
    }

    @Test
    void createTraining_shouldThrowUserNotFoundException() {
        CreateTrainingDto createTrainingDto = new CreateTrainingDto(1L,
                Date.from(LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.of(2024, 1, 1, 12, 0).atZone(ZoneId.systemDefault()).toInstant()),
                ActivityType.RUNNING, 10.0, 8.0);

        when(userProvider.getUser(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> trainingService.createTraining(createTrainingDto));
        verify(trainingRepository, never()).save(any(Training.class));
    }

    @Test
    void getTraining_shouldReturnUser() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        Training training = new Training(user,
                Date.from(LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.of(2024, 1, 1, 12, 0).atZone(ZoneId.systemDefault()).toInstant()),
                ActivityType.RUNNING, 10.0, 8.0);

        when(trainingRepository.findById(anyLong())).thenReturn(Optional.of(training));

        Optional<User> result = trainingService.getTraining(1L);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void getTraining_shouldReturnEmpty() {
        when(trainingRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<User> result = trainingService.getTraining(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void updateTraining_shouldUpdateTraining() {
        CreateTrainingDto createTrainingDto = new CreateTrainingDto(1L,
                Date.from(LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.of(2024, 1, 1, 12, 0).atZone(ZoneId.systemDefault()).toInstant()),
                ActivityType.RUNNING, 10.0, 8.0);
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        Training existingTraining = new Training(user,
                Date.from(LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.of(2024, 1, 1, 12, 0).atZone(ZoneId.systemDefault()).toInstant()),
                ActivityType.CYCLING, 15.0, 10.0);
        TrainingDto trainingDto = new TrainingDto(1L, user.getId(), createTrainingDto.getStartTime(), createTrainingDto.getEndTime(), createTrainingDto.getActivityType(), createTrainingDto.getDistance(), createTrainingDto.getAverageSpeed());

        when(userProvider.getUser(anyLong())).thenReturn(Optional.of(user));
        when(trainingRepository.findById(anyLong())).thenReturn(Optional.of(existingTraining));
        when(trainingRepository.save(any(Training.class))).thenReturn(existingTraining);
        when(trainingMapper.toDto(existingTraining)).thenReturn(trainingDto);

        TrainingDto updatedTrainingDto = trainingService.updateTraining(1L, createTrainingDto);

        assertNotNull(updatedTrainingDto);
        assertEquals(trainingDto, updatedTrainingDto);
        verify(trainingRepository, times(1)).save(existingTraining);
    }

    @Test
    void updateTraining_shouldThrowTrainingNotFoundException() {
        CreateTrainingDto createTrainingDto = new CreateTrainingDto(1L,
                Date.from(LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.of(2024, 1, 1, 12, 0).atZone(ZoneId.systemDefault()).toInstant()),
                ActivityType.RUNNING, 10.0, 8.0);
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");

        when(userProvider.getUser(anyLong())).thenReturn(Optional.of(user));
        when(trainingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TrainingNotFoundException.class, () -> trainingService.updateTraining(1L, createTrainingDto));
        verify(trainingRepository, never()).save(any(Training.class));
    }

    @Test
    void findAllTrainings_shouldReturnAllTrainings() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        Training training1 = new Training(user,
                Date.from(LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.of(2024, 1, 1, 12, 0).atZone(ZoneId.systemDefault()).toInstant()),
                ActivityType.RUNNING, 10.0, 8.0);
        Training training2 = new Training(user,
                Date.from(LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.of(2024, 1, 1, 12, 0).atZone(ZoneId.systemDefault()).toInstant()),
                ActivityType.CYCLING, 15.0, 10.0);
        TrainingDto trainingDto1 = new TrainingDto(1L, user.getId(), training1.getStartTime(), training1.getEndTime(), training1.getActivityType(), training1.getDistance(), training1.getAverageSpeed());
        TrainingDto trainingDto2 = new TrainingDto(2L, user.getId(), training2.getStartTime(), training2.getEndTime(), training2.getActivityType(), training2.getDistance(), training2.getAverageSpeed());

        when(trainingRepository.findAll()).thenReturn(List.of(training1, training2));
        when(trainingMapper.toDto(training1)).thenReturn(trainingDto1);
        when(trainingMapper.toDto(training2)).thenReturn(trainingDto2);

        List<TrainingDto> trainings = trainingService.findAllTrainings();

        assertEquals(2, trainings.size());
        assertTrue(trainings.contains(trainingDto1));
        assertTrue(trainings.contains(trainingDto2));
    }

    @Test
    void findTrainingsByUserId_shouldReturnTrainingsForUser() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        Training training = new Training(user,
                Date.from(LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.of(2024, 1, 1, 12, 0).atZone(ZoneId.systemDefault()).toInstant()),
                ActivityType.RUNNING, 10.0, 8.0);
        TrainingDto trainingDto = new TrainingDto(1L, user.getId(), training.getStartTime(), training.getEndTime(), training.getActivityType(), training.getDistance(), training.getAverageSpeed());

        when(trainingRepository.findByUserId(anyLong())).thenReturn(List.of(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingDto);

        List<TrainingDto> trainings = trainingService.findTrainingsByUserId(1L);

        assertEquals(1, trainings.size());
        assertTrue(trainings.contains(trainingDto));
    }

    @Test
    void findTrainingsByEndTimeAfter_shouldReturnTrainingsAfterDate() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        Training training = new Training(user,
                Date.from(LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.of(2024, 1, 1, 12, 0).atZone(ZoneId.systemDefault()).toInstant()),
                ActivityType.RUNNING, 10.0, 8.0);
        TrainingDto trainingDto = new TrainingDto(1L, user.getId(), training.getStartTime(), training.getEndTime(), training.getActivityType(), training.getDistance(), training.getAverageSpeed());

        when(trainingRepository.findByEndTimeAfter(any(Date.class))).thenReturn(List.of(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingDto);

        List<TrainingDto> trainings = trainingService.findTrainingsByEndTimeAfter(
                Date.from(LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant())
        );

        assertEquals(1, trainings.size());
        assertTrue(trainings.contains(trainingDto));
    }

    @Test
    void findTrainingsByActivityType_shouldReturnTrainingsForActivityType() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        Training training = new Training(user,
                Date.from(LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDateTime.of(2024, 1, 1, 12, 0).atZone(ZoneId.systemDefault()).toInstant()),
                ActivityType.RUNNING, 10.0, 8.0);
        TrainingDto trainingDto = new TrainingDto(1L, user.getId(), training.getStartTime(), training.getEndTime(), training.getActivityType(), training.getDistance(), training.getAverageSpeed());

        when(trainingRepository.findByActivityType(any(ActivityType.class))).thenReturn(List.of(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingDto);

        List<TrainingDto> trainings = trainingService.findTrainingsByActivityType(ActivityType.RUNNING);

        assertEquals(1, trainings.size());
        assertTrue(trainings.contains(trainingDto));
    }
}
