package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.ChangeTelephoneNoOrEmail;

public interface ChangeTelephoneNoOrEmailRepository extends JpaRepository<ChangeTelephoneNoOrEmail, Integer> {

}
