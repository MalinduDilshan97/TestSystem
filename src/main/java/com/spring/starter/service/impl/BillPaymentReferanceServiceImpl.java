package com.spring.starter.service.impl;

import com.spring.starter.DTO.BillPaymentReferanceDTO;
import com.spring.starter.Repository.BillPaymentReferanceRepository;
import com.spring.starter.model.BillPaymentReferance;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.BillPaymentReferanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillPaymentReferanceServiceImpl implements BillPaymentReferanceService {

    @Autowired
    BillPaymentReferanceRepository billPaymentReferanceRepository;

    @Override
    public ResponseEntity<?> saveBillPaymentReferance(BillPaymentReferanceDTO billPaymentReferanceDTO) {

        ResponseModel responseModel = new ResponseModel();
        BillPaymentReferance billPaymentReferance = setToBillPayment(billPaymentReferanceDTO);
        try {
            billPaymentReferanceRepository.save(billPaymentReferance);
            responseModel.setStatus(true);
            responseModel.setMessage("bill payment referance saved successfully");
            return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
        } catch (Exception e){
            responseModel.setStatus(true);
            responseModel.setMessage("Something went wrong with the database connection");
            return new ResponseEntity<>(responseModel,HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @Override
    public ResponseEntity<?> updateBillPaymentReferance(BillPaymentReferanceDTO billPaymentReferanceDTO,int billpaymentReferanceId) {

        ResponseModel responseModel = new ResponseModel();
        BillPaymentReferance billPaymentReferance = setToBillPayment(billPaymentReferanceDTO);
        Optional<BillPaymentReferance> billPaymentReferanceOpt = billPaymentReferanceRepository.findById(billpaymentReferanceId);

        if(billPaymentReferanceOpt.isPresent()){
            billPaymentReferance.setBillPaymentReferanceId(billPaymentReferanceOpt.get().getBillPaymentReferanceId());
            try {
                billPaymentReferanceRepository.save(billPaymentReferance);
                responseModel.setStatus(true);
                responseModel.setMessage("bill payment referance updated successfully");
                return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
            } catch (Exception e) {
                responseModel.setStatus(true);
                responseModel.setMessage("Something went wrong with the database connection");
                return new ResponseEntity<>(responseModel,HttpStatus.SERVICE_UNAVAILABLE);
            }
        } else {
            responseModel.setStatus(true);
            responseModel.setMessage("There is not that kind of billpayment record in the database");
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<?> getAllBillPaymentReferance() {
        List<BillPaymentReferance> billPaymentReferances = billPaymentReferanceRepository.findAll();
        if(billPaymentReferances.isEmpty()){
            ResponseModel responseModel = new ResponseModel();
            responseModel.setStatus(true);
            responseModel.setMessage("There is no data in the database");
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(billPaymentReferances,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> searchBillPayemtnReferance(int billpaymentReferanceId) {
        Optional<BillPaymentReferance> billPaymentReferance = billPaymentReferanceRepository.findById(billpaymentReferanceId);
        if(billPaymentReferance.isPresent()){
            return new ResponseEntity<>(billPaymentReferance,HttpStatus.OK);
        } else {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("There is no content to display for that id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<?> deleteBillReferance(int billpaymentReferanceId){
        billPaymentReferanceRepository.deleteById(billpaymentReferanceId);
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("Bill referance deleted successfully");
        responseModel.setStatus(true);
        return new ResponseEntity<>(responseModel,HttpStatus.OK);
    }

    private BillPaymentReferance setToBillPayment(BillPaymentReferanceDTO billPaymentReferanceDTO){

        BillPaymentReferance billPaymentReferance = new BillPaymentReferance();
        billPaymentReferance.setReferanceType(billPaymentReferanceDTO.getReferanceType());
        billPaymentReferance.setCollectionAccountNo(billPaymentReferanceDTO.getCollectionAccountNo());
        billPaymentReferance.setBillerNickname(billPaymentReferanceDTO.getBillerNickname());
        billPaymentReferance.setLocation(billPaymentReferanceDTO.getLocation());
        billPaymentReferance.setBillerType(billPaymentReferanceDTO.getBillerType());
        billPaymentReferance.setCollectionAccountType(billPaymentReferanceDTO.getCollectionAccountType());
        billPaymentReferance.setCollectionAccountBankAndBranch(billPaymentReferanceDTO.getCollectionAccountBankAndBranch());
        billPaymentReferance.setMaximumAmmountCanPay(billPaymentReferanceDTO.getMaximumAmmountCanPay());
        billPaymentReferance.setMinumumAmmountCanPay(billPaymentReferanceDTO.getMinumumAmmountCanPay());
        billPaymentReferance.setBillerContactEmail(billPaymentReferanceDTO.getBillerContactEmail());
        billPaymentReferance.setBillerContactPhoneNumber(billPaymentReferanceDTO.getBillerContactPhoneNumber());
        billPaymentReferance.setInputField_1(billPaymentReferanceDTO.getInputField_1());
        billPaymentReferance.setInputField_2(billPaymentReferanceDTO.getInputField_2());
        billPaymentReferance.setInputField_3(billPaymentReferanceDTO.getInputField_3());
        billPaymentReferance.setInputField_4(billPaymentReferanceDTO.getInputField_4());
        billPaymentReferance.setInputField_5(billPaymentReferanceDTO.getInputField_5());
        billPaymentReferance.setValidationRule_1(billPaymentReferanceDTO.getValidationRule_1());
        billPaymentReferance.setValidationRule_2(billPaymentReferanceDTO.getValidationRule_2());
        billPaymentReferance.setValidationRule_3(billPaymentReferanceDTO.getValidationRule_3());
        billPaymentReferance.setValidationRule_4(billPaymentReferanceDTO.getValidationRule_4());
        billPaymentReferance.setValidationRule_5(billPaymentReferanceDTO.getValidationRule_5());
        billPaymentReferance.setComment(billPaymentReferanceDTO.getComment());

        return billPaymentReferance;
    }
}
