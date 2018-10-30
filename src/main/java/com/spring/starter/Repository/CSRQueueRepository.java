package com.spring.starter.Repository;

import com.spring.starter.model.CSRQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CSRQueueRepository extends JpaRepository<CSRQueue,Integer> {

    @Query("SELECT Count(c) FROM CSRQueue c WHERE c.hold = false and c.complete=false and c.queuePending =true")
    public int getCurrentListCount();

    @Query("SELECT c FROM CSRQueue c WHERE c.customer.customerId = ?1")
    public Optional<CSRQueue> checkTokenStatus(int customerID);

    @Query("SELECT c FROM CSRQueue c ORDER BY c.queueNumIdentification")
    public List<CSRQueue> getAllQueueNumbers();

    @Query("SELECT c FROM CSRQueue c WHERE c.queueNumber=?1")
    public Optional<CSRQueue> getCSRQueueById(String queueNumber);

    @Query("SELECT c FROM CSRQueue c WHERE c.hold = true and c.complete=false ORDER BY c.queueNumIdentification")
    public List<CSRQueue> getholdQueue();

    @Query("SELECT c FROM CSRQueue c WHERE c.hold = false and c.complete=false and c.queuePending =true ORDER BY c.queueNumIdentification")
    public List<CSRQueue> getpendingQueue();

    @Query("SELECT c FROM CSRQueue c WHERE c.hold = false and c.complete=true and c.queuePending =false ORDER BY c.queueNumIdentification")
    public List<CSRQueue> getCompletedQueue();

}
