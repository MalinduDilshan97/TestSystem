package com.spring.starter.Repository;

import com.spring.starter.model.SmsSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SmsSubscriptionRepository extends JpaRepository<SmsSubscription, Integer> {

    @Query("SELECT s FROM SmsSubscription s WHERE s.customerServiceRequest.customerServiceRequestId=?1")
    Optional<SmsSubscription> getFormFromCSR(int customerServiceRequestId);

}
