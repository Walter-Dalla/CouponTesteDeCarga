package br.com.waltersoftware.coupon.service.dto;

import br.com.waltersoftware.coupon.controller.requestDTO.FindCouponFilterRequest;
import jakarta.validation.Valid;

public record FindCouponFilterDto(String code) {
    public static FindCouponFilterDto from(FindCouponFilterRequest findCouponFilterRequest) {
        return new FindCouponFilterDto(findCouponFilterRequest.code());
    }
}
