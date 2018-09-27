package com.spring.starter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.starter.model.MailingMailAccountNumbers;

public interface MailingMailModelRepository extends JpaRepository<MailingMailAccountNumbers, Integer> {

}
