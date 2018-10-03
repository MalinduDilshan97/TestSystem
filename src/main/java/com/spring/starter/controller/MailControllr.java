package com.spring.starter.controller;

import com.spring.starter.client.EmailSendipinMime;
import com.spring.starter.client.EmailServiceImpl;
import com.spring.starter.model.MailObject;
import com.spring.starter.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.AddressException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/mail")
public class MailControllr {

    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    EmailSendipinMime emailSendipinMime;

    ResponseModel model=new ResponseModel();

 /*   @PostMapping
    public String mailSend(@RequestBody MailObject mailObject){

       // emailService.sendSimpleMessage(mailObject);
        return "Success";
    }*/



    @GetMapping("sendmail")
    public ResponseEntity<?> sendEmail(){
        try {
            emailService.sendSimpleMessage("lakith1995@gmail.com", "first sub", "this is the set of template","lakith1995@gmail.com");
            model.setStatus(true);
            model.setMessage("email sent");
            return new ResponseEntity<>(model, HttpStatus.OK);
        }catch(Exception e){
            model.setStatus(false);
            model.setMessage(e.getCause().getMessage());
            return new ResponseEntity<>(model,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("sendmail2")
    public ResponseEntity<?> sendEmail2() throws UnsupportedEncodingException, AddressException {
        emailSendipinMime.sendEmail();
        model.setStatus(true);
        model.setMessage("email sent");
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
}
