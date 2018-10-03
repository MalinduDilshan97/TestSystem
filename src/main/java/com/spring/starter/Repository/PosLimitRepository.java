package com.spring.starter.Repository;

import com.spring.starter.model.PosLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PosLimitRepository extends JpaRepository<PosLimit, Integer> {

    @Query("SELECT p FROM PosLimit p WHERE p.customerServiceRequest.customerServiceRequestId=?1")
    Optional<PosLimit> getFormFromCSR(int customerServiceRequestId);

}
