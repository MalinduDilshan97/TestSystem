package com.spring.starter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.starter.model.NDBBranch;
import com.spring.starter.service.BranchService;

@RestController
@RequestMapping("/branch")
@CrossOrigin
public class BranchController {

	@Autowired
	BranchService branchService;
	
	@PostMapping("/addNewBranch")
	public ResponseEntity<?> addNewBranch(@RequestBody NDBBranch ndbBranch)
	{
		return branchService.addNewBranch(ndbBranch);
	}
	
	@PutMapping("/updateBranch/{branchId}")
	public ResponseEntity<?> updateBranch(@RequestBody NDBBranch ndbBranch,@PathVariable int branchId)
	{
		return branchService.updateBranch(ndbBranch, branchId);
	}
}
