package com.example.library.dto;

import jakarta.validation.constraints.*;

public record MemberRequest(@NotBlank String name, @Email @NotBlank String email, @NotBlank String phone) {
}
