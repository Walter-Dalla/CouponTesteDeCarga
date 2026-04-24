package br.com.waltersoftware.coupon.service.dto;

import br.com.waltersoftware.coupon.controller.requestDTO.FindCouponFilterRequest;

public record DeleteCouponDto() {
    public static FindCouponFilterDto from(FindCouponFilterRequest findCouponFilterRequest) {
        return new FindCouponFilterDto(findCouponFilterRequest.code());
    }
}
