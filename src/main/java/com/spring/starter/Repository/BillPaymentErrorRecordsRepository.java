package com.spring.starter.Repository;

import com.spring.starter.model.BillPaymentErrorRecords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillPaymentErrorRecordsRepository extends JpaRepository<BillPaymentErrorRecords,Integer> {
}
