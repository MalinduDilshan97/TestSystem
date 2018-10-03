package com.spring.starter.Repository;

import com.spring.starter.model.AtmOrDebitCardRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.EffectOrRevokePayment;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EffectOrRevokePaymentRepository extends JpaRepository<EffectOrRevokePayment, Integer> {

    @Query("SELECT eor FROM EffectOrRevokePayment eor WHERE eor.customerServiceRequest.customerServiceRequestId=?1")
    Optional<EffectOrRevokePayment> getFormFromCSR(int customerServiceRequestId);

}
