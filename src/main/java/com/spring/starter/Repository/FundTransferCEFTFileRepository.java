package com.spring.starter.Repository;

import com.spring.starter.model.FundTransferCEFTFiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundTransferCEFTFileRepository extends JpaRepository<FundTransferCEFTFiles,Integer> {
}
