package com.spring.starter.client;


import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;

import static com.google.common.collect.Lists.newArrayList;


@Service
@Transactional
public class  EmailSendipinMime {

        @Autowired
        private EmailService emailService;

        public void sendEmail() throws UnsupportedEncodingException {
                final Email email = DefaultEmail.builder()
                        .from(new InternetAddress("lakith1995@gmail.com",
                                "Lakith Muthugala"))
                        .to(newArrayList(
                                new InternetAddress("lakith1995@gmail.com",
                                        "Senila")))
                        .subject("You shall die! It's not me, it's Psychohistory")
                        .body("Hello Planet!")
                        .encoding("UTF-8").build();

                emailService.send(email);
        }

}