package br.com.waltersoftware.coupon.controller.requestDTO;

import jakarta.validation.constraints.*;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateNewCouponRequest(
    @NotNull UUID code,
    @NotNull @NotBlank  String description,
    @NotNull @DecimalMin("0.5") double discountValue,
    @NotNull  @Future OffsetDateTime expirationDate,
    @NotNull boolean published) {
}