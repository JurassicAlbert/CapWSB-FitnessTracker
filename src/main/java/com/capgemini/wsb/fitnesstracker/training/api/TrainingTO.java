package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * Data Transfer Object for Training.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingTO {
    Long id;
    Long userId;
    Date startTime;
    Date endTime;
    ActivityType activityType;
    double distance;
    double averageSpeed;
}
