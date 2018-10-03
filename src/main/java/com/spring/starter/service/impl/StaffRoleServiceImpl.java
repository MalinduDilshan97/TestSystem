package com.spring.starter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spring.starter.Repository.StaffRoleRepository;
import com.spring.starter.model.StaffRole;
import com.spring.starter.service.StaffRoleService;

@Service
public class StaffRoleServiceImpl implements StaffRoleService {

    @Autowired
    StaffRoleRepository staffRoleRepository;

    public ResponseEntity<?> saveNewUserRole(StaffRole staffRole) {
        try {
            staffRoleRepository.save(staffRole);
            return new ResponseEntity<>("{\"message\":\"Role Added Successfully\",\"Status\":" + true + "}", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("{\"Error\":\"Role Adition Failed\",\"Status\":" + false + "}", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getAllUserRoles() {
        try {
            List<StaffRole> staffRoles = staffRoleRepository.findAll();
            return new ResponseEntity<>(staffRoles, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("{\"Error\":\"Connection failed\",\"Status\":" + false + "}", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
