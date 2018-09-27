package com.spring.starter.Repository;

import com.spring.starter.model.ChangePrimaryAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangePrimaryAccountRepository extends JpaRepository<ChangePrimaryAccount,Integer> {
}
