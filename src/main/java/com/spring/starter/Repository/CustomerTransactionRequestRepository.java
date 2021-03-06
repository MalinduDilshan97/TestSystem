package com.spring.starter.Repository;

import com.spring.starter.model.CustomerTransactionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CustomerTransactionRequestRepository extends JpaRepository<CustomerTransactionRequest, Integer> {

    @Query("SELECT cs FROM CustomerTransactionRequest cs WHERE cs.transactionCustomer.transactionCustomerId = ?1")
    List<CustomerTransactionRequest> getAllTransactionCustomerRequest(int customerId);

    @Query("SELECT cs FROM CustomerTransactionRequest cs WHERE cs.transactionCustomer.transactionCustomerId = ?1 AND date(cs.requestDate)=?2")
    List<CustomerTransactionRequest> getAllTransactionCustomerRequestsFilterBudate(int customerId, Date date);

    @Query("SELECT ctr FROM CustomerTransactionRequest ctr WHERE date(ctr.requestDate) =:date AND ctr.status = false")
    List<CustomerTransactionRequest> getAllUncompleteRequests(@Param("date") Date date);
}
