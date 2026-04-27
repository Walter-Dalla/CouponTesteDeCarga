package br.com.waltersoftware.coupon.service;

import br.com.waltersoftware.coupon.repository.dto.DatabaseDtoBase;

public class ServiceBase {
    public boolean validateDatabaseResultAndRollbackIfNeeded(DatabaseDtoBase databaseResult){
        if(!databaseResult.success()){
            if(databaseResult.shouldRollback()){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }

            return false;
        }

        return true;
    }
}
