package com.capgemini.wsb.fitnesstracker.user.api;


/**
 * Exception thrown when attempting to delete a user with associated trainings.
 */
public class UserHasTrainingsException extends RuntimeException {
    public UserHasTrainingsException(String message) {
        super(message);
    }
}