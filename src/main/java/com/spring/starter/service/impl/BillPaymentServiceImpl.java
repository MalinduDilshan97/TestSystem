package com.spring.starter.service.impl;

import com.spring.starter.DTO.BillPaymentUpdateDTO;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.model.Currency;
import com.spring.starter.service.BillPaymentService;
import com.spring.starter.util.FileStorage;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class BillPaymentServiceImpl implements BillPaymentService {

    @Autowired
    BillPaymentRepository billPaymentRepository;

    @Autowired
    CustomerTransactionRequestRepository customerTransactionRequestRepository;

    @Autowired
    BillPaymentErrorRecordsRepository billPaymentErrorRecordsRepository;

    @Autowired
    BillPaymentUpdateRecordsRepository billPaymentUpdateRecordsRepository;

    @Autowired
    BillPaymentReferanceRepository billPaymentReferanceRepository;

    @Autowired
    private FileStorage fileStorage;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Override
    public ResponseEntity<?> saveBillPayment(BillPayment billPayment, int customerTransactionRequestId) {
        ResponseModel responseModel = new ResponseModel();
       Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                                                                                .findById(customerTransactionRequestId);
       if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("Invalied Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
       }
       int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
       if(id != TransactionIdConfig.BILLPAYMENT){
           responseModel.setMessage("Invalied Transaction Request Id");
           responseModel.setStatus(false);
           return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
       }
       Optional<BillPayment> billPaymentOptional = billPaymentRepository.getFormFromCSR(customerTransactionRequestId);
       if(billPaymentOptional.isPresent()){
            billPayment.setBillPaymentId(billPaymentOptional.get().getBillPaymentId());
       }

       Optional<BillPaymentReferance> billPaymentReferanceOpt = billPaymentReferanceRepository.findById(billPayment.getBillPaymentReferance().getBillPaymentReferanceId());

       if(!billPaymentReferanceOpt.isPresent()){
           responseModel.setMessage("Invalied bill response details");
           responseModel.setStatus(false);
           return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
       }

       billPayment.setBillPaymentReferance(billPaymentReferanceOpt.get());

        Optional<Currency> currency = currencyRepository.findById(billPayment.getCurrency().getCurrency_id());
        if(!currency.isPresent()){
            responseModel.setMessage("Invalied Currency details");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        }else {
            billPayment.setCurrency(currency.get());
        }
       billPayment.setCustomerTransactionRequest(customerTransactionRequest.get());
       if(billPayment.getCurrency().getCurrency().equals("LKR")) {
           if (billPayment.isCurrencyIsCash()) {
               if (billPayment.getValueOf10Notes() == 0 && billPayment.getValueOf20Notes() == 0 &&
                       billPayment.getValueOf50Notes() == 0 && billPayment.getValueOf100Notes() == 0 &&
                       billPayment.getValueOf500Notes() == 0 && billPayment.getValueof1000Notes() == 0 &&
                       billPayment.getValueOf2000Notes() == 0 && billPayment.getValueOf2000Notes() == 0) {
                   responseModel.setMessage("Please fill cash details");
                   responseModel.setStatus(false);
                   return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
               }
           }
           double sum = (double) (billPayment.getValueOf5000Notes() + billPayment.getValueOf2000Notes()
                   + billPayment.getValueof1000Notes() + billPayment.getValueOf100Notes() +
                   billPayment.getValueOf500Notes() + billPayment.getValueOf50Notes() +
                   billPayment.getValueOf20Notes() + billPayment.getValueOf10Notes() +
                   billPayment.getValueOfcoins());
           if (sum != billPayment.getTotal()) {
               responseModel.setMessage("Incorrect Cash Total");
               responseModel.setStatus(false);
               return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
           }
       }

       Optional<Branch> branchOpt = branchRepository.findById(billPayment.getBranch().getBranch_id());
       if(!branchOpt.isPresent()){
           responseModel.setMessage("Incorrect bank branch details");
           responseModel.setStatus(false);
           return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
       } else {
           billPayment.setBranch(branchOpt.get());
       }

       try{
           responseModel.setMessage("saved successfully");
           responseModel.setStatus(true);
           billPayment = billPaymentRepository.save(billPayment);
           return new ResponseEntity<>(billPayment,HttpStatus.CREATED);
       } catch (Exception e) {
           responseModel.setMessage("Something Went wrong");
           responseModel.setStatus(false);
           return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
       }
    }

    @Override
    public ResponseEntity<?> updateBillPayment(BillPayment billPayment, int customerTransactionRequestId,
                                                   BillPaymentUpdateDTO billPaymentUpdateDTO) throws Exception {
            ResponseModel responseModel = new ResponseModel();
            Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                    .findById(customerTransactionRequestId);
            if(!customerTransactionRequest.isPresent()){
                responseModel.setMessage("Invalid Transaction Request");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if(id != TransactionIdConfig.BILLPAYMENT){
                responseModel.setMessage("Invalied Transaction Request Id");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
            Optional<BillPayment> billPaymentOptional = billPaymentRepository.getFormFromCSR(customerTransactionRequestId);
            if(!billPaymentOptional.isPresent()){
                responseModel.setStatus(false);
                responseModel.setMessage("There is no record to update");
                return new ResponseEntity<>(responseModel,HttpStatus.OK);
            }

            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();

            String extention = billPaymentUpdateDTO.getFile().getOriginalFilename();
            extention = FilenameUtils.getExtension(extention);

            String location =  ("/billPayment/signatures/update_record_verifications/" + customerTransactionRequestId);
            String filename = ""+customerTransactionRequestId + "_uuid-"+ randomUUIDString+"."+extention;
            String url = fileStorage.fileSaveWithRenaming(billPaymentUpdateDTO.getFile(),location,filename);
            location = location+"/"+filename;
            if(url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload Signature");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {

                BillPaymentUpdateRecords billPaymentUpdateRecords = new BillPaymentUpdateRecords();
                billPaymentUpdateRecords.setComment(billPaymentUpdateDTO.getComment());
                billPaymentUpdateRecords.setCustomerTransactionRequest(customerTransactionRequest.get());
                billPaymentUpdateRecords.setUrl(location);

                try {
                    billPaymentUpdateRecords = billPaymentUpdateRecordsRepository.save(billPaymentUpdateRecords);
                } catch (Exception e){
                    throw new Exception(e.getMessage());
                }
                List<BillPaymentErrorRecords> billPaymentErrorRecords = getBillPaymentErrors(billPaymentOptional.get()
                        ,billPayment,billPaymentUpdateRecords);
                if(billPaymentErrorRecords.isEmpty()){
                    billPaymentErrorRecords = new ArrayList<>();
                }
                billPaymentUpdateRecords.setBillPaymentErrorRecords(billPaymentErrorRecords);

                billPaymentUpdateRecords = billPaymentUpdateRecordsRepository.save(billPaymentUpdateRecords);

                if(billPaymentUpdateRecords == null) {
                    responseModel.setMessage("Something went wrong with the db connection");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel,HttpStatus.SERVICE_UNAVAILABLE);
                } else {
                    try{
                        Optional<BillPaymentReferance> billPaymentReferanceOpt = billPaymentReferanceRepository.findById(billPayment.getBillPaymentReferance().getBillPaymentReferanceId());

                        if(!billPaymentReferanceOpt.isPresent()){
                            responseModel.setMessage("Invalid bill response details");
                            responseModel.setStatus(false);
                            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
                        }

                        Optional<Branch> branchOpt = branchRepository.findById(billPayment.getBranch().getBranch_id());
                        if(!branchOpt.isPresent()){
                            responseModel.setMessage("Incorrect bank branch details.");
                            responseModel.setStatus(false);
                            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
                        } else {
                            billPayment.setBranch(branchOpt.get());
                        }

                        Optional<Currency> currency = currencyRepository.findById(billPayment.getCurrency().getCurrency_id());
                        if(!currency.isPresent()){
                            responseModel.setMessage("Invalied Currency details.");
                            responseModel.setStatus(false);
                            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
                        }else {
                            billPayment.setCurrency(currency.get());
                        }

                        billPayment.setBillPaymentReferance(billPaymentReferanceOpt.get());
                        billPaymentRepository.save(billPayment);
                        responseModel.setMessage("Bill payment Updated successfully");
                        responseModel.setStatus(true);
                        return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                }
            }

        }

    @Override
    public ResponseEntity<?> getBillPaymentRequest(int billPaymentId){
        Optional<BillPayment> billPaymentOptional = billPaymentRepository.findById(billPaymentId);
        if(!billPaymentOptional.isPresent()){
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("There is no bill payment details available for that id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(billPaymentOptional,HttpStatus.OK);
        }

    }

    private List<BillPaymentErrorRecords> getBillPaymentErrors(BillPayment billPaymentOld, BillPayment billPaymentnew,
                                                               BillPaymentUpdateRecords billPaymentUpdateRecords)
                                                               throws ParseException {

        BillPaymentErrorRecords billPaymentErrorRecords;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strdate = sdf.format(billPaymentnew.getDate());
        Date dates= sdf.parse(strdate);

        List<BillPaymentErrorRecords> billPaymentErrorRecordslist = new ArrayList<>();

        if(!billPaymentOld.getAccountName().equals(billPaymentnew.getAccountName())){
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"accountName\":\""+billPaymentOld.getAccountName()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"accountName\":\""+billPaymentnew.getAccountName()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if (!billPaymentOld.getBenificiaryName().equals(billPaymentnew.getBenificiaryName())){
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"benificiaryName\":\""+billPaymentOld.getBenificiaryName()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"benificiaryName\":\""+billPaymentnew.getBenificiaryName()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(!billPaymentnew.getBenificiaryTelNo().equals(billPaymentOld.getBenificiaryTelNo())){
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"benificiaryTelNo\":\""+billPaymentOld.getBenificiaryTelNo()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"benificiaryTelNo\":\""+billPaymentnew.getBenificiaryTelNo()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(billPaymentnew.getBranch().getBranch_id() != billPaymentOld.getBranch().getBranch_id()){
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"bankAndBranch\":\""+billPaymentOld.getBranch().getMx_branch_name()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"bankAndBranch\":\""+billPaymentnew.getBranch().getMx_branch_name()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(billPaymentnew.getDate().compareTo(dates)!= 1){
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"currencyIsCash\":\""+billPaymentOld.getDate()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"currencyIsCash\":\""+billPaymentnew.getDate()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(billPaymentnew.isCurrencyIsCash() != billPaymentOld.isCurrencyIsCash()){
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"currencyIsChaque\":\""+billPaymentOld.getDate()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"currencyIsChaque\":\""+billPaymentnew.getDate()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(billPaymentnew.isCurrencyIsChaque() != billPaymentOld.isCurrencyIsChaque()){
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"collectionAccountNo\":\""+billPaymentOld.getCollectionAccountNo()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"collectionAccountNo\":\""+billPaymentnew.getCollectionAccountNo()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(!billPaymentnew.getCollectionAccountNo().equals(billPaymentOld.getCollectionAccountNo())){
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"referanceNo\":\""+billPaymentOld.getCollectionAccountNo()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"referanceNo\":\""+billPaymentnew.getCollectionAccountNo()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(billPaymentnew.getValueOf5000Notes() != billPaymentOld.getValueOf5000Notes()){
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"valueOf5000Notes\":\""+billPaymentnew.getValueOf5000Notes()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"valueOf5000Notes\":\""+billPaymentOld.getValueOf5000Notes()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(billPaymentnew.getValueOf2000Notes() != billPaymentOld.getValueOf2000Notes()){
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"valueOf2000Notes\":\""+billPaymentnew.getValueOf2000Notes()+"\"}");
            billPaymentErrorRecords.setOldValue("{\"valueOf2000Notes\":\""+billPaymentnew.getValueOf2000Notes()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(billPaymentnew.getValueof1000Notes() != billPaymentOld.getValueof1000Notes()) {
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"valueof1000Notes\":\""+billPaymentnew.getValueof1000Notes()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"valueof1000Notes\":\""+billPaymentOld.getValueof1000Notes()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(billPaymentnew.getValueOf500Notes() != billPaymentOld.getValueOf500Notes()) {
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"valueOf500Notes\":\""+billPaymentnew.getValueOf500Notes()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"valueOf500Notes\":\""+billPaymentOld.getValueOf500Notes()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(billPaymentnew.getValueOf100Notes() != billPaymentOld.getValueOf100Notes()) {
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"valueOf100Notes\":\""+billPaymentnew.getValueOf100Notes()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"valueOf100Notes\":\""+billPaymentOld.getValueOf100Notes()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(billPaymentnew.getValueOf50Notes() != billPaymentOld.getValueOf50Notes()) {
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"valueOf50Notes\":\""+billPaymentOld.getValueOf50Notes()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"valueOf50Notes\":\""+billPaymentnew.getValueOf50Notes()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(billPaymentnew.getValueOf10Notes() != billPaymentOld.getValueOf10Notes()){
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"valueOf10Notes\":\""+billPaymentOld.getValueOf10Notes()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"valueOf10Notes\":\""+billPaymentnew.getValueOf10Notes()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(billPaymentnew.getValueOfcoins() != billPaymentOld.getValueOfcoins()){
            billPaymentErrorRecords = new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"valueOfcoins\":\""+billPaymentOld.getValueOfcoins()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"valueOfcoins\":\""+billPaymentnew.getValueOfcoins()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(billPaymentnew.getBillPaymentReferance().getBillPaymentReferanceId() != billPaymentOld.getBillPaymentReferance().getBillPaymentReferanceId()){
            billPaymentErrorRecords= new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"referance\":\""+billPaymentOld.getBillPaymentReferance().getBillPaymentReferanceId()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"referance\":\""+billPaymentnew.getBillPaymentReferance().getBillPaymentReferanceId()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        if(billPaymentnew.getCurrency().getCurrency_id() != billPaymentOld.getCurrency().getCurrency_id()){
            billPaymentErrorRecords= new BillPaymentErrorRecords();
            billPaymentErrorRecords.setOldValue("{\"currency\":\""+billPaymentOld.getCurrency().getCurrency_id()+"\"}");
            billPaymentErrorRecords.setNewValue("{\"currency\":\""+billPaymentnew.getCurrency().getCurrency_id()+"\"}");
            billPaymentErrorRecords.setBillPaymentUpdateRecords(billPaymentUpdateRecords);
            billPaymentErrorRecords = billPaymentErrorRecordsRepository.save(billPaymentErrorRecords);
            billPaymentErrorRecordslist.add(billPaymentErrorRecords);
        }
        return billPaymentErrorRecordslist;
    }
}
