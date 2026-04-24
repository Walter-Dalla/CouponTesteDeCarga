package br.com.waltersoftware.coupon.models;

import br.com.waltersoftware.coupon.service.dto.CreateNewCouponDto;
import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class CouponEntity {

    String code;
    String description;
    float discountValue;
    Date expirationDate;
    boolean published;
    boolean deleted = false;

    public CouponEntity(CreateNewCouponDto createNewCouponDto) {
        this.code = createNewCouponDto.code();
        this.description =    createNewCouponDto.description();
        this.discountValue =createNewCouponDto.discountValue();
        this.expirationDate =createNewCouponDto.expirationDate();
        this.published =createNewCouponDto.published();
        this.deleted = false;
    }


}
