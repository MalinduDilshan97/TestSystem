package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.QueueNum;

public interface QueueNumRepository extends JpaRepository<QueueNum, Integer> {

}
