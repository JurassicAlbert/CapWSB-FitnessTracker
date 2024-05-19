package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import lombok.Value;

import java.util.Date;

/**
 * Data Transfer Object for Training.
 */
@Value
public class TrainingDto {
    Long id;
    Long userId;
    Date startTime;
    Date endTime;
    ActivityType activityType;
    double distance;
    double averageSpeed;
}
