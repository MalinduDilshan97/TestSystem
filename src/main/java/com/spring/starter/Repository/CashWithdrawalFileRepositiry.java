package com.spring.starter.Repository;

import com.spring.starter.model.CashWithdrawalFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashWithdrawalFileRepositiry extends JpaRepository<CashWithdrawalFile , Integer> {

}
