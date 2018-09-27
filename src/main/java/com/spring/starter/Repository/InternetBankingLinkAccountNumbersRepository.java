package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.InternetBankingLinkAccountNumbers;

public interface InternetBankingLinkAccountNumbersRepository extends JpaRepository<InternetBankingLinkAccountNumbers, Integer> {

}
