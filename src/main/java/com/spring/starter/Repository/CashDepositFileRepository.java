package com.spring.starter.Repository;

import com.spring.starter.model.CashDepositFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashDepositFileRepository extends JpaRepository<CashDepositFile,Integer> {
}
