package com.spring.starter.service.impl;

import com.spring.starter.DTO.EffectOrRevokePaymentDTO;
import com.spring.starter.DTO.EffectOrRevokePaymentDetailsDTO;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.CustomerAccountNoRepository;
import com.spring.starter.Repository.CustomerServiceRequestRepository;
import com.spring.starter.Repository.EffectOrRevokePaymentDetailsRepository;
import com.spring.starter.Repository.EffectOrRevokePaymentRepository;
import com.spring.starter.model.*;
import com.spring.starter.service.EffectOrRevokePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Transactional
@Service
public class EffectOrRevokePaymentServiceImpl implements EffectOrRevokePaymentService {

    @Autowired
    private EffectOrRevokePaymentRepository effectOrRevokePaymentRepository;
    @Autowired
    private EffectOrRevokePaymentDetailsRepository effectOrRevokePaymentDetailsRepository;
    @Autowired
    private CustomerAccountNoRepository customerAccountNoRepository;
    @Autowired
    private CustomerServiceRequestRepository customerServiceRequestRepository;

    private ResponseModel res = new ResponseModel();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public ResponseEntity<?> saveEffectOrPaymentRequest(EffectOrRevokePaymentDTO effectOrRevokePaymentDTO) {

        Optional<CustomerAccountNo> customerAccountNoOptional= customerAccountNoRepository.findByAccountNumber(effectOrRevokePaymentDTO.getAccountNo());
        if (!customerAccountNoOptional.isPresent()){
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        Optional<CustomerServiceRequest> optional = customerServiceRequestRepository.findById(effectOrRevokePaymentDTO.getCustomerServiceRequestId());
        if (!optional.isPresent()){
            res.setMessage(" No Data Found To Complete The Request");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        CustomerServiceRequest customerServiceRequest=optional.get();
        CustomerAccountNo customerAccountNo=customerAccountNoOptional.get();

        EffectOrRevokePayment effectOrRevokePayment= new EffectOrRevokePayment();
        effectOrRevokePayment.setEffectOrRevokePaymentId(effectOrRevokePaymentDTO.getEffectOrRevokePaymentId());
        effectOrRevokePayment.setCustomerAccountNo(customerAccountNo);
        effectOrRevokePayment.setCustomerServiceRequest(customerServiceRequest);

        EffectOrRevokePayment payment = effectOrRevokePaymentRepository.save(effectOrRevokePayment);

        if (payment!=null){

            for (EffectOrRevokePaymentDetailsDTO dto : effectOrRevokePaymentDTO.getList()) {

                try {
                    Date chequeDate = df.parse(dto.getDateOfCheque());

                EffectOrRevokePaymentDetails details=new EffectOrRevokePaymentDetails(0,
                        dto.getChequeNumber(),
                        dto.getValue(),
                        dto.getPayeeName(),
                        chequeDate,
                        dto.getReason(),
                        payment);

                effectOrRevokePaymentDetailsRepository.save(details);

                } catch (ParseException e) {
                    throw new  CustomException("Failed TO Save The Request... Operation Unsuccessful Input Date To This Format (YYYY-MM-DD)");
                }
            }
            res.setMessage(" Request Form Successfully Saved To The System");
            res.setStatus(true);
            return new ResponseEntity<>(res, HttpStatus.CREATED);

        }else{
            res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }
}
