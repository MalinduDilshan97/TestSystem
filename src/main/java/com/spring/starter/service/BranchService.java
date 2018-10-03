package com.spring.starter.service;

import org.springframework.http.ResponseEntity;

import com.spring.starter.model.NDBBranch;

public interface BranchService {

    public ResponseEntity<?> addNewBranch(NDBBranch ndbBranch);

    public ResponseEntity<?> updateBranch(NDBBranch ndbBranch, int branchId);

    public ResponseEntity<?> deleteBranch(int branchId);

}
