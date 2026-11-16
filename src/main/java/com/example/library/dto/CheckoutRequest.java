package com.example.library.dto;

import jakarta.validation.constraints.NotNull;

public record CheckoutRequest(@NotNull Long bookId, @NotNull Long memberId) {
}
