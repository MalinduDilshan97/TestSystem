package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.IdentificationForm;

public interface ChangeIdentificationFormRepository extends JpaRepository<IdentificationForm, Integer> {

}
