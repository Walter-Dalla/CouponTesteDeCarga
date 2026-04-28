package br.com.waltersoftware.coupon.service.dto;

public record ServiceReturnBase<T>(
        boolean success,
        T data
) {
    public static ServiceReturnBase ok() {
        return new ServiceReturnBase(true, null);
    }

    public static <T> ServiceReturnBase<T> ok(T data) {
        return new ServiceReturnBase<>(true, data);
    }

    public static <T> ServiceReturnBase<T> error() {
        return new ServiceReturnBase<>(false, null);
    }
}