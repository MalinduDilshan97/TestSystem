package com.spring.starter.Repository;

import com.spring.starter.model.ServiceRequestFileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestFileUploadRepository extends JpaRepository<ServiceRequestFileUpload,Integer> {
}
