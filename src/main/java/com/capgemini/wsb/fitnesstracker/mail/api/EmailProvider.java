package com.capgemini.wsb.fitnesstracker.mail.api;

import java.util.List;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;

/**
 * Interface for creating email content for training reports.
 */
public interface EmailProvider {

    /**
     * Creates an email with the given recipient, title, and list of training DTOs.
     *
     * @param recipient the email address of the recipient
     * @param title     the title of the email
     * @param trainings the list of training DTOs to include in the email
     * @return the email content
     */
    EmailDto createEmail(final String recipient, final String title, final List<TrainingDto> trainings);
}
