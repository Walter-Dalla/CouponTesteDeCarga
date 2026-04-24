package br.com.waltersoftware.coupon.service;

import br.com.waltersoftware.coupon.models.CouponEntity;
import br.com.waltersoftware.coupon.repository.interfaces.CouponRepository;
import br.com.waltersoftware.coupon.service.dto.CreateNewCouponDto;
import br.com.waltersoftware.coupon.service.dto.FindCouponFilterDto;
import br.com.waltersoftware.coupon.service.dto.DeleteCouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public CouponEntity findCoupon(FindCouponFilterDto findCouponFilterDto){

        return couponRepository.findByCode(findCouponFilterDto.getCode())
                .orElseThrow(() -> new RuntimeException("Cupom não encontrado"));

    }

    @Transactional
    public void createNewCoupon(CreateNewCouponDto createNewCouponDto){

        if (couponRepository.existsByCode(createNewCouponDto.code())) {
            throw new RuntimeException("Cupom já existe com esse código");
        }

        CouponEntity entity = new CouponEntity(createNewCouponDto);

        couponRepository.save(entity);


    }

    @Transactional
    public void deleteCoupon(DeleteCouponDto deleteCouponDto){

        couponRepository.deleteByCode(dto.getCode());

    }

}
