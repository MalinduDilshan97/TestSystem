package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.FdOrCd;

public interface FdOrCdRepository extends JpaRepository<FdOrCd, Integer> {

}
