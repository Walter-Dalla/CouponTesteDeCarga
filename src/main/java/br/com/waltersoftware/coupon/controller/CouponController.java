package br.com.waltersoftware.coupon.controller;

import br.com.waltersoftware.coupon.controller.requestDTO.DeleteCouponRequest;
import br.com.waltersoftware.coupon.controller.requestDTO.FindCouponFilterRequest;
import br.com.waltersoftware.coupon.controller.requestDTO.CreateNewCouponRequest;
import br.com.waltersoftware.coupon.service.CouponService;
import br.com.waltersoftware.coupon.service.dto.FindCouponFilterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/{code}")
    public void GetCoupon(@Valid @ModelAttribute FindCouponFilterRequest findCouponFilterRequest){
        couponService.findCoupon(FindCouponFilterDto.from(findCouponFilterRequest));
    }

    @PostMapping
    public void CreateNewCoupon(@Valid CreateNewCouponRequest createNewCouponRequest){

    }

    @DeleteMapping("/{code}")
    public void DeleteCoupon(@Valid @ModelAttribute DeleteCouponRequest deleteCouponRequest){

    }
}
