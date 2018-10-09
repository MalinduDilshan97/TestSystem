package com.spring.starter.Repository;

import com.spring.starter.model.TransactionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrancsactionRequestServiceRepository extends JpaRepository<TransactionRequest,Integer> {

}
