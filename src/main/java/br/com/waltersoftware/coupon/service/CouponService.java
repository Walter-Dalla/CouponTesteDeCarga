package br.com.waltersoftware.coupon.service;

import br.com.waltersoftware.coupon.models.CouponEntity;
import br.com.waltersoftware.coupon.repository.dto.DatabaseDtoBase;
import br.com.waltersoftware.coupon.repository.interfaces.CouponRepository;
import br.com.waltersoftware.coupon.service.dto.CreateNewCouponDto;
import br.com.waltersoftware.coupon.service.dto.FindCouponFilterDto;
import br.com.waltersoftware.coupon.service.dto.DeleteCouponDto;
import br.com.waltersoftware.coupon.service.dto.SeriviceReturnBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CouponService extends ServiceBase {

    private final CouponRepository couponRepository;

    @Transactional
    public SeriviceReturnBase<CouponEntity> findCoupon(FindCouponFilterDto findCouponFilterDto){

        DatabaseDtoBase<Optional<CouponEntity>> couponDatabaseResult = couponRepository.findByCode(findCouponFilterDto.code());

        boolean success = validateDatabaseResultAndRollbackIfNeeded(couponDatabaseResult);
        if(!success){
            return SeriviceReturnBase.error();
        }

        return SeriviceReturnBase.ok(couponDatabaseResult.data().get());
    }

    @Transactional
    public SeriviceReturnBase<CouponEntity> createCoupon(CreateNewCouponDto createNewCouponDto){

        DatabaseDtoBase<Boolean> existsDatabaseResult = couponRepository.existsByCode(createNewCouponDto.code());

        if(!existsDatabaseResult.success()){
            return SeriviceReturnBase.error();
        }

        if (existsResult.data().isPresent()) {
            throw new RuntimeException("Cupom já existe com esse código");
        }

        CouponEntity entity = new CouponEntity(createNewCouponDto);

        DatabaseDtoBase<Optional<CouponEntity>> couponDatabaseResult = couponRepository.save(entity);

        boolean success = validateDatabaseResultAndRollbackIfNeeded(couponDatabaseResult);
        if(!success){
            return SeriviceReturnBase.error();
        }

        return SeriviceReturnBase.ok(couponDatabaseResult.data().get());
    }

    @Transactional
    public SeriviceReturnBase<CouponEntity> deleteCoupon(DeleteCouponDto deleteCouponDto){
        DatabaseDtoBase<Optional<CouponEntity>> couponDatabaseResult = couponRepository.deleteByCode(deleteCouponDto.code());

        boolean success = validateDatabaseResultAndRollbackIfNeeded(couponDatabaseResult);
        if(!success){
            return SeriviceReturnBase.error();
        }

        return SeriviceReturnBase.ok(couponDatabaseResult.data().get());
    }
}
