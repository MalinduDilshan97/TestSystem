package com.spring.starter.Repository;

import com.spring.starter.model.CustomerServiceRequest;
import com.spring.starter.model.TransactionCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionCustomerRepository extends JpaRepository <TransactionCustomer , Integer> {

    @Query("SELECT tc FROM TransactionCustomer tc WHERE tc.identification = ?1")
    List<TransactionCustomer> getRecordFromIdentity(String identification);

    @Query("SELECT tc FROM TransactionCustomer tc WHERE tc.identification = ?1 AND date(tc.date)=?2")
    List<TransactionCustomer> getTransactionCustomerFilterBydate(String identification, Date requestDate);

    @Query("SELECT tc FROM TransactionCustomer tc WHERE date(tc.date) = 1")
    List<TransactionCustomer> getTransactionsOfadate(Date requestDate);

}
