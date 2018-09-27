package com.spring.starter.Repository;

import com.spring.starter.model.AccountStatementIssueRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatementIssueRequestRepository extends JpaRepository<AccountStatementIssueRequest,Integer> {
}
