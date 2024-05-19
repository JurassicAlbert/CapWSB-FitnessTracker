package com.capgemini.wsb.fitnesstracker.user.internal;

import lombok.Value;

/**
 * DTO for User's ID and Email.
 */
@Value
public class UserEmailDto {
    Long id;
    String email;
}
