package com.spring.starter.Repository;

import com.spring.starter.model.BankStatementAccountNo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankStatementAccountNoRepository extends JpaRepository<BankStatementAccountNo, Integer> {
}
