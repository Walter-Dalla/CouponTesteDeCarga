package br.com.waltersoftware.coupon.service.dto;

import java.util.Date;

public record CreateNewCouponDto(String code,
                                String description,
                                float discountValue,
                                Date expirationDate,
                                boolean published) {
}
