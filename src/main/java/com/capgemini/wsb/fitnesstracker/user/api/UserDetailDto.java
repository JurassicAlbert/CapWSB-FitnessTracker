package com.capgemini.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

public record UserDetailDto(@Nullable Long id, String firstName, String lastName) {
}