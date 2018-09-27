package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.FdCdNumbers;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FdCdNumbersRepository extends JpaRepository<FdCdNumbers, Integer> {

}
