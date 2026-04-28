package br.com.waltersoftware.coupon.service;

import br.com.waltersoftware.coupon.domain.CouponEntity;
import br.com.waltersoftware.coupon.exception.BusinessDuplicateCouponException;
import br.com.waltersoftware.coupon.exception.BusinessException;
import br.com.waltersoftware.coupon.exception.BusinessNotFoundException;
import br.com.waltersoftware.coupon.repository.interfaces.CouponRepository;
import br.com.waltersoftware.coupon.service.dto.CreateNewCouponDto;
import br.com.waltersoftware.coupon.service.dto.ServiceReturnBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CouponService extends ServiceBase {

    private final CouponRepository couponRepository;

    @Transactional
    public ServiceReturnBase<CouponEntity> findCoupon(UUID code) throws BusinessNotFoundException {

        Optional<CouponEntity> couponDatabaseResult = couponRepository.findById(code);

        validateDatabaseResult(couponDatabaseResult);

        return ServiceReturnBase.ok(couponDatabaseResult.get());
    }

    @Transactional
    public ServiceReturnBase<CouponEntity> createCoupon(CreateNewCouponDto createNewCouponDto) throws BusinessDuplicateCouponException {

        boolean existsDatabaseResult = couponRepository.existsById(createNewCouponDto.code());

        if (existsDatabaseResult) {
            throw new BusinessDuplicateCouponException("Cupom já existe com esse código");
        }

        CouponEntity entity = new CouponEntity(createNewCouponDto);

        CouponEntity couponDatabaseResult = couponRepository.save(entity);

        return ServiceReturnBase.ok(couponDatabaseResult);
    }

    @Transactional
    public ServiceReturnBase deleteCoupon(UUID code) throws BusinessException {
        Optional<CouponEntity> result = couponRepository.findById(code);
        validateDatabaseResult(result);

        CouponEntity entity = result.get();
        entity.delete();
        couponRepository.save(entity);

        return ServiceReturnBase.ok();
    }
}
