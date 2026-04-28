package br.com.waltersoftware.coupon.service;

import br.com.waltersoftware.coupon.domain.CouponEntity;
import br.com.waltersoftware.coupon.exception.BusinessNotFoundException;
import br.com.waltersoftware.coupon.service.dto.ServiceReturnBase;

import java.util.Optional;
import java.util.UUID;

public abstract class ServiceBase {

    // Normalmente utilizo um validador generico de erros... Esse codigo é muito simples para ter um validador desse tipo
    // porém aqui estou simulando caso houvesse uma regra mais robusta
    public boolean validateDatabaseResult(Optional<CouponEntity> databaseResult) throws BusinessNotFoundException {
        if(databaseResult.isEmpty()){
            throw new BusinessNotFoundException("Cupom não encontrato");
        }
        return databaseResult.isPresent();
    }
}
