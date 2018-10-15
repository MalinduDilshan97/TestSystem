package com.spring.starter.controller;

import com.spring.starter.DTO.BranchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.starter.service.BranchService;

@RestController
@RequestMapping("/Branch")
@CrossOrigin
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping("/addNewBranch")
    public ResponseEntity<?> addNewBranch(@RequestBody BranchDTO branchDTO) {
        return branchService.addNewBranch(branchDTO);
    }

    @PutMapping("/updateBranch")
    public ResponseEntity<?> updateBranch(@RequestBody BranchDTO branchDTO) {
        return branchService.updateBranch(branchDTO);
    }

    @DeleteMapping("/delete/{mx_branch_code}")
    public ResponseEntity<?>deleteBranch(@PathVariable int mx_branch_code){
        return branchService.deleteBranch(mx_branch_code);
    }

    @GetMapping("/search-branch-by-bank/{mx_bank_code}")
    public ResponseEntity<?>searchBranchByBank(@PathVariable int mx_bank_code){
        return branchService.getBranchByBank(mx_bank_code);
    }

    @GetMapping("/search-branch/{mx_branch_code}")
    public ResponseEntity<?>searchBranch(@PathVariable int mx_branch_code){
        return branchService.searchBranch(mx_branch_code);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> viewAllBranches() {
        return branchService.getAllBranches();
    }
}
