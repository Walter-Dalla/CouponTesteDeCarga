package br.com.waltersoftware.coupon.repository.interfaces;

import br.com.waltersoftware.coupon.domain.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, UUID> {


}
