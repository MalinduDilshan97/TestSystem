package com.spring.starter.Repository;

import com.spring.starter.model.SmsSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsSubscriptionRepository extends JpaRepository<SmsSubscription,Integer> {
}
