package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.ChangeIdentificationForm;

public interface ChangeIdentificationFormRepository extends JpaRepository<ChangeIdentificationForm, Integer> {

}
