package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.ServiceRequest;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Integer>{

}
