package br.com.waltersoftware.coupon.service.dto;

import br.com.waltersoftware.coupon.controller.requestDTO.DeleteCouponRequest;
import br.com.waltersoftware.coupon.controller.requestDTO.FindCouponFilterRequest;

public record DeleteCouponDto(String code) {
    public static FindCouponFilterDto from(DeleteCouponRequest deleteCouponRequest) {
        return new FindCouponFilterDto(deleteCouponRequest.code());
    }
}
