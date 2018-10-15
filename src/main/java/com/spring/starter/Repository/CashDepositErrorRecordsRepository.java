package com.spring.starter.Repository;

import com.spring.starter.model.CashDepositErrorRecords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashDepositErrorRecordsRepository extends JpaRepository<CashDepositErrorRecords,Integer> {
}
