package br.com.waltersoftware.coupon.repository.dto;

import java.util.Optional;

public record DatabaseDtoBase<T>(
        boolean success,
        Optional<T> data,
        boolean shouldRollback
) {

    public static <T> DatabaseDtoBase<T> ok(Optional<T> data) {
        return new DatabaseDtoBase<>(true, data, false);
    }

    public static <T> DatabaseDtoBase<T> error() {
        return new DatabaseDtoBase<>(false, null, true);
    }
}