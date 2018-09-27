package com.spring.starter.Repository;

import com.spring.starter.model.DuplicatePassBookRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DuplicatePassBookRequestRepository extends JpaRepository<DuplicatePassBookRequest,Integer> {
}
