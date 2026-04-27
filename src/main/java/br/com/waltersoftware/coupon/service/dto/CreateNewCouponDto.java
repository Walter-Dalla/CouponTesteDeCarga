package br.com.waltersoftware.coupon.service.dto;

import br.com.waltersoftware.coupon.controller.requestDTO.CreateNewCouponRequest;
import jakarta.validation.Valid;

import java.util.Date;

public record CreateNewCouponDto(String code,
                                String description,
                                double discountValue,
                                Date expirationDate,
                                boolean published) {
    public static CreateNewCouponDto from(@Valid CreateNewCouponRequest createNewCouponRequest) {
        return new CreateNewCouponDto(createNewCouponRequest.code(),
                createNewCouponRequest.description(),
                createNewCouponRequest.discountValue(),
                createNewCouponRequest.expirationDate(),
                createNewCouponRequest.published());
    }
}
