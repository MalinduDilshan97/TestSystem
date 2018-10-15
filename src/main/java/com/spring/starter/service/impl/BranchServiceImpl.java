package com.spring.starter.service.impl;

import java.util.List;
import java.util.Optional;

import com.spring.starter.DTO.BranchDTO;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.BankRepository;
import com.spring.starter.Repository.BranchRepository;
import com.spring.starter.model.Bank;
import com.spring.starter.model.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.BranchService;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private BankRepository bankRepository;
    private ResponseModel response = new ResponseModel();

    @Override
    public ResponseEntity<?> addNewBranch(BranchDTO branchDTO) {

        Optional<Bank> optional= bankRepository.findById(branchDTO.getMx_bank_code());
        if (!optional.isPresent()){
            response.setMessage(" No Data Found");
            response.setStatus(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Branch branch= new Branch(branchDTO.getMx_branch_code(),branchDTO.getMx_branch_name(),branchDTO.isCeft(),optional.get());

        try {
            if (branchRepository.save(branch)!=null){
                response.setMessage(" Request Successful");
                response.setStatus(true);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }else{
                response.setMessage(" Request  Unsuccessful");
                response.setStatus(false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
           throw new CustomException("Request Unsuccessful");
        }
    }

    @Override
    public ResponseEntity<?> updateBranch(BranchDTO branchDTO) {
        Optional<Bank> optional= bankRepository.findById(branchDTO.getMx_bank_code());
        if (!optional.isPresent()){
            response.setMessage(" No Data Found");
            response.setStatus(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Optional<Branch> optionalBranch = branchRepository.findById(branchDTO.getBranch_id());
        if (!optionalBranch.isPresent()) {
            response.setMessage("No Branch Details Present");
            response.setStatus(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

            Branch branch = new Branch(optionalBranch.get().getBranch_id(),optionalBranch.get().getMx_branch_code(),branchDTO.getMx_branch_name(),
                    branchDTO.isCeft(),optional.get());

            try {

                if (branchRepository.save(branch)!=null){
                    response.setMessage("Branch Details Updated Successfully");
                    response.setStatus(true);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }else{
                    response.setMessage("There was an unexpected error occured");
                    response.setStatus(true);
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

            } catch (Exception e) {
                throw new CustomException("Request Unsuccessful");
            }
    }

    @Override
    public ResponseEntity<?> getAllBranches() {
        List<Branch> list = branchRepository.findAll();
        if (list.isEmpty()){
            response.setMessage(" No Data Found");
            response.setStatus(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getBranchByBank(int mx_bank_code) {
        List<Branch>list=branchRepository.findByBank(mx_bank_code);
        if (list.isEmpty()){
            response.setMessage(" No Data Found");
            response.setStatus(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteBranch(int mx_branch_code) {
        try {
            Optional<Branch> optionalBranch = branchRepository.findById(mx_branch_code);
            if (!optionalBranch.isPresent()) {
                response.setMessage("No Branch Details Present");
                response.setStatus(false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            branchRepository.deleteById(mx_branch_code);
            response.setMessage("Branch Details Removed Successfully");
            response.setStatus(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException("Request Unsuccessful");
        }
    }

    @Override
    public ResponseEntity<?> searchBranch(int mx_branch_code) {
        try {
            Optional<Branch> optionalBranch = branchRepository.findById(mx_branch_code);
            if (!optionalBranch.isPresent()) {
                response.setMessage("No Branch Details Present");
                response.setStatus(false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(optionalBranch.get(),HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException("Request Unsuccessful");
        }
    }

}
