package com.spring.starter.service;

import com.spring.starter.DTO.BranchDTO;
import com.spring.starter.model.Branch;
import org.springframework.http.ResponseEntity;

public interface BranchService {

    public ResponseEntity<?> addNewBranch(BranchDTO branchDTO);

    public ResponseEntity<?> updateBranch(BranchDTO branchDTO);

    public ResponseEntity<?> deleteBranch(int mx_branch_code);

    public ResponseEntity<?> searchBranch(int mx_branch_code);

    public ResponseEntity<?> getAllBranches();

    public ResponseEntity<?> getBranchByBank(int mx_bank_code);


}
