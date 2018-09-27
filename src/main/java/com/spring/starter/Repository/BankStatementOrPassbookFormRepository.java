package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.BankStatementOrPassbookForm;

public interface BankStatementOrPassbookFormRepository extends JpaRepository<BankStatementOrPassbookForm, Integer> {

}
