package com.spring.starter.Repository;

import com.spring.starter.model.PosLimit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosLimitRepository extends JpaRepository<PosLimit,Integer> {
}
