package br.com.waltersoftware.coupon.domain;

import br.com.waltersoftware.coupon.domain.enums.CouponStatus;
import br.com.waltersoftware.coupon.service.dto.CreateNewCouponDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import br.com.waltersoftware.coupon.exception.BusinessException;
import org.hibernate.annotations.SQLRestriction;


import java.time.OffsetDateTime;
import java.util.UUID;

@AllArgsConstructor
@Entity
@Table(name = "coupons")
@SQLRestriction("deleted = false")
@RequiredArgsConstructor
@Data
public class CouponEntity {

    @Id
    UUID code;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    double discountValue;

    @Column(nullable = false)
    OffsetDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponStatus status;


    @Column(nullable = false)
    boolean deleted = false;

    // Como nas regras de negocio do desafio tecnico não está especificado se os campos de Published e redeemed são independentes
    // Eu tomei a liberdade de atrela-los ao status para economizar espaço no banco de dados.
    // Até porque não faz sentido eu ter campos de deleted, status, reddeemed e published independentes
    // A arquitetura de deixar indepententes pode gerar erros de dessincronia entre status no banco de dados

    // Até porque para o usuario se o cupom já foi usado ou está ativo mas está deletado é a mesma coisa que estar inativo, inexistente ou ja usado...
    // É até uma questão de segurança, eu como programador não quero que o usuario saiba que o cupom já existiu alguma vez e foi deletado ou usado

    public boolean getRedeemed(){
        return status == CouponStatus.INACTIVE || deleted;
    }

    public boolean getPublished(){
        return status == CouponStatus.ACTIVE;
    }

    public CouponEntity(CreateNewCouponDto createNewCouponDto) {
        this.code = createNewCouponDto.code();
        this.description = createNewCouponDto.description();
        this.discountValue = createNewCouponDto.discountValue();
        this.expirationDate = createNewCouponDto.expirationDate();
        this.deleted = false;
        this.updateStatus(createNewCouponDto.published());
    }

    public static CouponEntity create(UUID code, String description,
                                      Double discountValue,
                                      OffsetDateTime expirationDate,
                                      boolean published) throws BusinessException {
        if (discountValue <= 0) {
            throw new BusinessException("discountValue must be greater than 0");
        }

        if (expirationDate.isBefore(OffsetDateTime.now())){
            throw new BusinessException("expirationDate must be in the future");
        }

        CouponEntity coupon = new CouponEntity();
        coupon.code = code;
        coupon.description = description;
        coupon.discountValue = discountValue;
        coupon.expirationDate = expirationDate;
        coupon.deleted = false;
        coupon.updateStatus(published);
        return coupon;
    }

    public void delete() throws BusinessException {
        if (deleted) {
            throw new BusinessException("Coupon already deleted");
        }
        this.deleted = true;
        this.updateStatus();
    }


    protected void updateStatus(boolean published){
        if(published) {
            this.status = CouponStatus.ACTIVE;
        }
        else {
            this.status = CouponStatus.INACTIVE;
        }

        updateStatus();
    }

    protected void updateStatus(){
       if(this.deleted){
            this.status = CouponStatus.DELETED;
       }
    }
}
