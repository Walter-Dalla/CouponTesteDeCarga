package br.com.waltersoftware.coupon.repository.interfaces;

import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {
    Optional<CouponEntity> findByCode(String code);

    List<CouponEntity> findByActiveTrue();

    List<CouponEntity> findByActiveFalse();

    boolean existsByCode(String code);

    void deleteByCode(String code);

}
