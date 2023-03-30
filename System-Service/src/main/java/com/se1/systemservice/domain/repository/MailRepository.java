package com.se1.systemservice.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.se1.systemservice.domain.model.Mail;

@Repository
public interface MailRepository extends CrudRepository<Mail, Integer>{

	Mail findByMailTemplate(String mailTemplate);
}
