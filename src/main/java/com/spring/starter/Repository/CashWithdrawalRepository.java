package com.spring.starter.Repository;

import com.spring.starter.model.CashWithdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CashWithdrawalRepository extends JpaRepository<CashWithdrawal ,Integer> {

    @Query("SELECT cw FROM CashWithdrawal cw WHERE cw.customerTransactionRequest.customerTransactionRequestId=?1")
    Optional<CashWithdrawal> getFormFromCSR(int customerTransactionRequestID);

}
