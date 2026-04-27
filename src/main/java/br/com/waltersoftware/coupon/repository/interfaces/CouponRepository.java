package br.com.waltersoftware.coupon.repository.interfaces;

import br.com.waltersoftware.coupon.models.CouponEntity;
import br.com.waltersoftware.coupon.repository.dto.DatabaseDtoBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {
    DatabaseDtoBase<Optional<CouponEntity>> findByCode(String code);

    DatabaseDtoBase<List<CouponEntity>> findByActiveTrue();

    DatabaseDtoBase<List<CouponEntity>> findByActiveFalse();

    DatabaseDtoBase<Boolean> existsByCode(String code);

    DatabaseDtoBase<Optional<CouponEntity>> deleteByCode(String code);

}
