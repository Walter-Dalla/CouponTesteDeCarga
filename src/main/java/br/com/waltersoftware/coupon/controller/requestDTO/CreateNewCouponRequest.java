package br.com.waltersoftware.coupon.controller.requestDTO;

import jakarta.validation.constraints.*;

import java.util.Date;

public record CreateNewCouponRequest(
    @NotNull @NotBlank String code,
    @NotNull @NotBlank  String description,
    @NotNull @DecimalMin("0.5") double discountValue,
    @NotNull  @Future Date expirationDate,
    @NotNull boolean published) {
}