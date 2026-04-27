package br.com.waltersoftware.coupon.controller;

import br.com.waltersoftware.coupon.controller.requestDTO.DeleteCouponRequest;
import br.com.waltersoftware.coupon.controller.requestDTO.FindCouponFilterRequest;
import br.com.waltersoftware.coupon.controller.requestDTO.CreateNewCouponRequest;
import br.com.waltersoftware.coupon.models.CouponEntity;
import br.com.waltersoftware.coupon.service.CouponService;
import br.com.waltersoftware.coupon.service.dto.CreateNewCouponDto;
import br.com.waltersoftware.coupon.service.dto.DeleteCouponDto;
import br.com.waltersoftware.coupon.service.dto.FindCouponFilterDto;
import br.com.waltersoftware.coupon.service.dto.SeriviceReturnBase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/{code}")
    public ResponseEntity<CouponEntity> GetCoupon(@Valid @ModelAttribute FindCouponFilterRequest findCouponFilterRequest){
        SeriviceReturnBase<CouponEntity> couponResult =  couponService.findCoupon(FindCouponFilterDto.from(findCouponFilterRequest));

        if (couponResult.success()) {
            return ResponseEntity.ok(couponResult.getData());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<CouponEntity> CreateNewCoupon(@Valid CreateNewCouponRequest createNewCouponRequest){
        SeriviceReturnBase<CouponEntity> couponResult = couponService.createCoupon(CreateNewCouponDto.from(createNewCouponRequest));

        if (couponResult.success()) {
            return ResponseEntity.ok(couponResult.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{code}")
    public ResponseEntity<CouponEntity> DeleteCoupon(@Valid @ModelAttribute DeleteCouponRequest deleteCouponRequest){
        SeriviceReturnBase<CouponEntity> couponResult = couponService.deleteCoupon(DeleteCouponDto.from(deleteCouponRequest));

        if (couponResult.success()) {
            return ResponseEntity.ok(couponResult.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
