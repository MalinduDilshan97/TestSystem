package com.spring.starter.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.starter.Repository.NDBBranchRepository;
import com.spring.starter.model.NDBBranch;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.BranchService;

@Service
public class BranchServiceImpl implements BranchService {

	@Autowired
	NDBBranchRepository ndbBranchRepository;
	
	@Override
	public ResponseEntity<?> addNewBranch(NDBBranch ndbBranch) {

		ResponseModel responsemodel = new ResponseModel();
		
		try {
		responsemodel.setMessage("Branch saved successfully");
		responsemodel.setStatus(true);	
		ndbBranchRepository.save(ndbBranch);	
		return new ResponseEntity<>(responsemodel,HttpStatus.CREATED);
		} catch (Exception e) {
			responsemodel.setMessage("Invalied Details Entered");
			responsemodel.setStatus(false);
			return new ResponseEntity<>(responsemodel,HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> updateBranch(NDBBranch ndbBranch, int branchId) {
		ResponseModel responsemodel = new ResponseModel();
		Optional<NDBBranch> ndbOptional = ndbBranchRepository.findById(branchId);
		if(!ndbOptional.isPresent()) {
			responsemodel.setMessage("No Branch Details Present For That Id");
			responsemodel.setStatus(false);	
			return new ResponseEntity<>(responsemodel,HttpStatus.NO_CONTENT);
		} else 
		{
			NDBBranch branch = new NDBBranch();
			branch = ndbOptional.get();
			branch.setBranch_name(ndbBranch.getBranch_name());
			branch.setBranchAddress(ndbBranch.getBranchAddress());
			branch.setBranchFax(ndbBranch.getBranchFax());
			branch.setBranchTel(ndbBranch.getBranchTel());
			branch.setBranchUid(ndbBranch.getBranchUid());
			branch.setBranchManeger(ndbBranch.getBranchManeger());
			try {
				ndbBranchRepository.save(branch);
				responsemodel.setMessage("Branch Deails Updated Successfully");
				responsemodel.setStatus(true);	
				return new ResponseEntity<>(responsemodel,HttpStatus.OK);
			} catch (Exception e) {
				responsemodel.setMessage("There was an unexpected error occured");
				responsemodel.setStatus(true);	
				return new ResponseEntity<>(responsemodel,HttpStatus.SERVICE_UNAVAILABLE);
			}
		}
	}

	@Override
	public ResponseEntity<?> deleteBranch(int branchId) {
		// TODO Auto-generated method stub
		return null;
	}

}
