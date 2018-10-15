package com.spring.starter.Repository;

import com.spring.starter.model.ReissueLoginPasswordModel;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.EffectOrRevokePaymentDetails;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EffectOrRevokePaymentDetailsRepository extends JpaRepository<EffectOrRevokePaymentDetails, Integer>{

}
