package com.spring.starter.Repository;

import com.spring.starter.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch,Integer> {

    @Query("SELECT br FROM Branch br WHERE br.bank.mx_bank_code = :mx_bank_code")
    public List<Branch> findByBank(@Param("mx_bank_code") int mx_bank_code);

}
