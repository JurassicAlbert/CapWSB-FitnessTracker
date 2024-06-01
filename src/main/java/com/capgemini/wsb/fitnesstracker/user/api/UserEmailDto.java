package com.capgemini.wsb.fitnesstracker.user.api;

import lombok.Value;

/**
 * DTO for User's ID and Email.
 */
public record UserEmailDto(Long id, String email) {
}
