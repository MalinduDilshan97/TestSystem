package com.spring.starter.Repository;

import com.spring.starter.model.WithholdingFdCd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WithholdingFdCdRepository extends JpaRepository<WithholdingFdCd, Integer> {

    @Query("select c from WithholdingFdCd  c where c.customerServiceRequest.customerServiceRequestId=?1")
    Optional<WithholdingFdCd> findByRequestId(int requestId);
}
