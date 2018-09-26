package com.spring.starter.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.starter.model.AtmOrDebit;
import com.spring.starter.model.SmsAlertsCreditCardNumbers;

public interface SmsAlertsCreditCardNumbersRepository extends JpaRepository<SmsAlertsCreditCardNumbers, Integer> {
	
}
