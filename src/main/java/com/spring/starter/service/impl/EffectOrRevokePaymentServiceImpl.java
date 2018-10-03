package com.spring.starter.service.impl;

import com.spring.starter.DTO.EffectOrRevokePaymentDTO;
import com.spring.starter.DTO.EffectOrRevokePaymentDetailsDTO;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.*;
import com.spring.starter.model.*;
import com.spring.starter.service.EffectOrRevokePaymentService;
import com.spring.starter.service.ServiceRequestCustomerLogService;
import com.spring.starter.service.ServiceRequestFormLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

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
    @Autowired
    private ServiceRequestCustomerLogService serviceRequestCustomerLogService;
    @Autowired
    private ServiceRequestFormLogService serviceRequestFormLogService;

    private ResponseModel res = new ResponseModel();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public ResponseEntity<?> saveEffectOrPaymentRequest(EffectOrRevokePaymentDTO effectOrRevokePaymentDTO, HttpServletRequest request) {

        ServiceRequestCustomerLog serviceRequestCustomerLog= new ServiceRequestCustomerLog();
        ServiceRequestFormLog serviceRequestFormLog= new ServiceRequestFormLog();

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


            serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
            serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
            serviceRequestCustomerLog.setIp(request.getRemoteAddr());
            serviceRequestCustomerLog.setMessage("Request Form Successfully Saved To The System");
            boolean result=serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

            if (result){
                serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                serviceRequestFormLog.setIp(request.getRemoteAddr());
                serviceRequestFormLog.setStatus(true);
                serviceRequestFormLog.setMessage("Request Form Successfully Saved To The System");
                serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
            }


            res.setMessage(" Request Form Successfully Saved To The System");
            res.setStatus(true);
            return new ResponseEntity<>(res, HttpStatus.CREATED);

        }else{

            serviceRequestCustomerLog.setDate(java.util.Calendar.getInstance().getTime());
            serviceRequestCustomerLog.setIdentification(customerServiceRequest.getCustomer().getIdentification());
            serviceRequestCustomerLog.setIp(request.getRemoteAddr());
            serviceRequestCustomerLog.setMessage("Failed TO Save The Request... Operation Unsuccessful");
            boolean result=serviceRequestCustomerLogService.saveServiceRequestCustomerLog(serviceRequestCustomerLog);

            if (result){
                serviceRequestFormLog.setDigiFormId(customerServiceRequest.getServiceRequest().getDigiFormId());
                serviceRequestFormLog.setCustomerId(customerServiceRequest.getCustomer().getCustomerId());
                serviceRequestFormLog.setDate(java.util.Calendar.getInstance().getTime());
                serviceRequestFormLog.setFromId(customerServiceRequest.getServiceRequest().getServiceRequestId());
                serviceRequestFormLog.setIp(request.getRemoteAddr());
                serviceRequestFormLog.setStatus(true);
                serviceRequestFormLog.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
                serviceRequestFormLogService.saveServiceRequestFormLog(serviceRequestFormLog);
            }

            res.setMessage(" Failed TO Save The Request... Operation Unsuccessful");
            res.setStatus(false);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }
}
