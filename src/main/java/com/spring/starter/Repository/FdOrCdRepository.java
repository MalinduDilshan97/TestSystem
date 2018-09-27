package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.FdOrCd;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FdOrCdRepository extends JpaRepository<FdOrCd, Integer> {

/*    @Query("select foc from FdOrCd foc where foc.customerServiceRequest.customerServiceRequestId=?1")
    Optional<FdOrCd> getFodFromcsr(int requestId);*/
}
