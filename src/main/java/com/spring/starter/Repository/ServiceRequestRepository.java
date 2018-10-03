package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.ServiceRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Integer>{

    @Query("select c from ServiceRequest c where c.digiFormId=?1")
    Optional<ServiceRequest> getFromDigiId(int digiid);

}
