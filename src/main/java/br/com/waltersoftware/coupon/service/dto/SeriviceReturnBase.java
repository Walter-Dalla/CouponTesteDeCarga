package br.com.waltersoftware.coupon.service.dto;

import java.util.Optional;

public record SeriviceReturnBase<T>(
        boolean success,
        Optional<T> data,
        boolean shouldRollback
) {

    public static <T> SeriviceReturnBase<T> ok(Optional<T> data) {
        return new SeriviceReturnBase<>(true, data, false);
    }

    public static <T> SeriviceReturnBase<T> error() {
        return new SeriviceReturnBase<>(false, null, true);
    }
}