package com.spring.starter.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.starter.model.QueueNum;
import com.spring.starter.model.QueueService;



public interface QueueServiceRepository extends JpaRepository<QueueService, Integer> {

	@Query("SELECT s FROM QueueService s WHERE s.queue.queueNumId=?1 ")
	public QueueService getQueueService(int queueId);
	
}
