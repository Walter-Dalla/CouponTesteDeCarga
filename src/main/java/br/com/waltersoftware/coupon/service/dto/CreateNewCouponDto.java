package br.com.waltersoftware.coupon.service.dto;

import br.com.waltersoftware.coupon.controller.requestDTO.CreateNewCouponRequest;
import jakarta.validation.Valid;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateNewCouponDto(UUID code,
                                 String description,
                                 double discountValue,
                                 OffsetDateTime expirationDate,
                                 boolean published) {
    public static CreateNewCouponDto from(@Valid CreateNewCouponRequest createNewCouponRequest) {
        return new CreateNewCouponDto(createNewCouponRequest.code(),
                createNewCouponRequest.description(),
                createNewCouponRequest.discountValue(),
                createNewCouponRequest.expirationDate(),
                createNewCouponRequest.published());
    }
}
