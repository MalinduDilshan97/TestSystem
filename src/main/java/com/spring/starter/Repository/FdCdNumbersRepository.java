package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.FdCdNumbers;

public interface FdCdNumbersRepository extends JpaRepository<FdCdNumbers, Integer> {

}
