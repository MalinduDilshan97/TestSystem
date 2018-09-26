package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.ContactDetails;

public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Integer> {

}
