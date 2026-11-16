package com.example.library.dto;

import jakarta.validation.constraints.*;

public record BookRequest(@NotBlank String title, @NotBlank String author, @NotBlank String isbn,
        @Min(0) int publicationYear, @Min(0) int totalCopies) {
}
