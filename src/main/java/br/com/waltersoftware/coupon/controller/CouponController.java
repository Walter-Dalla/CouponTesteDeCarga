package br.com.waltersoftware.coupon.controller;

import br.com.waltersoftware.coupon.controller.requestDTO.CreateNewCouponRequest;
import br.com.waltersoftware.coupon.domain.CouponEntity;
import br.com.waltersoftware.coupon.exception.BusinessDuplicateCouponException;
import br.com.waltersoftware.coupon.exception.BusinessException;
import br.com.waltersoftware.coupon.exception.BusinessNotFoundException;
import br.com.waltersoftware.coupon.service.CouponService;
import br.com.waltersoftware.coupon.service.dto.CreateNewCouponDto;
import br.com.waltersoftware.coupon.service.dto.ServiceReturnBase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;


    // Eu preferia que o endpoint recebesse o filtro em formato de objeto, conforme a linha abaixo:
    // public ResponseEntity<CouponEntity> GetCoupon(@Valid @ModelAttribute FindCouponFilterRequest findCouponFilterRequest)
    // Para simplificar as validações e permitir que o endpoint cressa sem alterar a controller
    // Porém o Swagger não entende o codigo acima como se fosse um body no get.
    // Mesmo com a API subindo e funcionando corretamente na url -> http://localhost:8080/coupon/3fa85f64-5717-4562-b3fc-2c963f66afa6

    @GetMapping("/{code}")
    public ResponseEntity<CouponEntity> GetCoupon(@PathVariable UUID code) throws BusinessNotFoundException {
        ServiceReturnBase<CouponEntity> couponResult =  couponService.findCoupon(code);

        if (couponResult.success()) {
            return ResponseEntity.ok(couponResult.data());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<CouponEntity> CreateNewCoupon(@RequestBody @Valid CreateNewCouponRequest createNewCouponRequest) throws BusinessDuplicateCouponException {
        ServiceReturnBase<CouponEntity> couponResult = couponService.createCoupon(CreateNewCouponDto.from(createNewCouponRequest));

        if (couponResult.success()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(couponResult.data());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // O mesmo do metodo get acima é valido aqui, eu gostaria de usar
    // public ResponseEntity<CouponEntity> DeleteCoupon(@Valid @ModelAttribute DeleteCouponRequest deleteCouponRequest)
    @DeleteMapping("/{code}")
    public ResponseEntity<CouponEntity> DeleteCoupon(@PathVariable UUID code) throws BusinessException {
        ServiceReturnBase<CouponEntity> couponResult = couponService.deleteCoupon(code);

        if (couponResult.success()) {
            return ResponseEntity.ok(couponResult.data());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
