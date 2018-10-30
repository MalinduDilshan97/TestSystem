package com.spring.starter.service.impl;

import com.spring.starter.DTO.FileDTO;
import com.spring.starter.DTO.DetailsUpdateDTO;
import com.spring.starter.DTO.TransactionSignatureDTO;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.model.Currency;
import com.spring.starter.service.CashDepositService;
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
public class CashDepositServiceImpl implements CashDepositService {

    @Autowired
    private CustomerTransactionRequestRepository customerTransactionRequestRepository;
    @Autowired
    private CashdepositRepositiry cashdepositRepositiry;
    @Autowired
    private FileStorage fileStorage;
    @Autowired
    private CashDepositFileRepository cashDepositFileRepository;
    @Autowired
    private CashDepositUpdateRecordsRepository cashDepositUpdateRecordsRepository;
    @Autowired
    private CashDepositErrorRecordsRepository cashDepositErrorRecordsRepository;
    @Autowired
    private CurrencyRepository currencyRepository;

    private ResponseModel responseModel = new ResponseModel();

    @Override
    public ResponseEntity<?> saveNewCashDepositRequest(CashDeposit cashDeposit, int customerTransactionRequestId) {

        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(customerTransactionRequestId);
        if (!customerTransactionRequest.isPresent()) {
            responseModel.setMessage("Invalied Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if (id != TransactionIdConfig.DEPOSITS) {
            responseModel.setMessage("Invalied Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }
        Optional<CashDeposit> cashDepositOpt = cashdepositRepositiry.getFormFromCSR(customerTransactionRequestId);
        if (cashDepositOpt.isPresent()) {
            cashDeposit.setCashDepositId(cashDepositOpt.get().getCashDepositId());
        }
        cashDeposit.setCustomerTransactionRequest(customerTransactionRequest.get());

        Optional<Currency> currency = currencyRepository.findById(cashDeposit.getCurrency().getCurrency_id());
        if(!currency.isPresent()){
            responseModel.setMessage("Invalied Currency details");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {
            cashDeposit.setCurrency(currency.get());
        }

        if(currency.get().getCurrency().equals("LKR")) {
            if (cashDeposit.getValueOf10Notes() == 0 && cashDeposit.getValueOf20Notes() == 0 &&
                    cashDeposit.getValueOf50Notes() == 0 && cashDeposit.getValueOf100Notes() == 0 &&
                    cashDeposit.getValueOf500Notes() == 0 && cashDeposit.getValueof1000Notes() == 0 &&
                    cashDeposit.getValueOf2000Notes() == 0 && cashDeposit.getValueOf2000Notes() == 0) {
                responseModel.setMessage("Please fill cash details");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }

            double sum = (double) (cashDeposit.getValueOf5000Notes() + cashDeposit.getValueOf2000Notes()
                    + cashDeposit.getValueof1000Notes() + cashDeposit.getValueOf100Notes() +
                    cashDeposit.getValueOf500Notes() + cashDeposit.getValueOf50Notes() +
                    cashDeposit.getValueOf20Notes() + cashDeposit.getValueOf10Notes() +
                    cashDeposit.getValueOfcoins());
            if (sum != cashDeposit.getTotal()) {
                responseModel.setMessage("Incorrect Cash Total");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        }
        try {
            cashDeposit = cashdepositRepositiry.save(cashDeposit);
            return new ResponseEntity<>(cashDeposit, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> uploadFilesToCashDeposit(FileDTO cashDepositFileDTO) throws Exception {
        try {

            Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                    .findById(cashDepositFileDTO.getCustomerTransactionRequestId());
            if (!customerTransactionRequest.isPresent()) {
                responseModel.setMessage("Invalid Transaction  Request");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }

            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if (id != TransactionIdConfig.DEPOSITS) {
                responseModel.setMessage("Invalid Transaction Request Id");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        Optional<CashDeposit> cashDepositOptional = cashdepositRepositiry.getFormFromCSR(cashDepositFileDTO
                .getCustomerTransactionRequestId());

        if (!cashDepositOptional.isPresent()) {
            responseModel.setMessage("Invalid customer transaction id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.NOT_FOUND);
        } else {
            String location = ("/cash_deposit/file_uploads/" + cashDepositFileDTO.getCustomerTransactionRequestId()
                    + "/Customer Files");
            String url = fileStorage.fileSave(cashDepositFileDTO.getFile(), location);
            if (url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload Signature");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {
                CashDepositFile cashDepositFile = new CashDepositFile();
                cashDepositFile.setCashDeposit(cashDepositOptional.get());
                cashDepositFile.setFileType(cashDepositFileDTO.getFileType());
                cashDepositFile.setFileUrl(url);

                try {
                    cashDepositFile=cashDepositFileRepository.save(cashDepositFile);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }

                if (cashDepositFile==null){
                    responseModel.setMessage("Failed To Save File");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
                }else{
                    CashDeposit cashDeposit = cashDepositOptional.get();
                    List<CashDepositFile> cashDepositFiles = cashDepositOptional.get().getCashDepositFiles();
                    if(cashDepositFiles.isEmpty()){
                        cashDepositFiles = new ArrayList<>();
                        cashDepositFiles.add(cashDepositFile);
                    } else {
                        cashDepositFiles.add(cashDepositFile);
                    }
                    cashDeposit.setCashDepositFiles(cashDepositFiles);
                    try{
                        cashdepositRepositiry.save(cashDeposit);
                        responseModel.setMessage("Files uploaded successfully");
                        responseModel.setStatus(true);
                        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                }

            }

        }

    }

    @Override
    public ResponseEntity<?> updateCashDeposit(CashDeposit cashDeposit, int customerTransactionRequestId, DetailsUpdateDTO detailsUpdateDTO) throws Exception {
        Optional<CustomerTransactionRequest> customerTransactionRequest;

        try {
            customerTransactionRequest = customerTransactionRequestRepository.findById(customerTransactionRequestId);
        } catch (Exception e) {
            throw  new Exception(e.getMessage());
        }
        if (!customerTransactionRequest.isPresent()) {
            responseModel.setMessage("Invalid Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.DEPOSITS){
            responseModel.setMessage("Invalid Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }

        Optional<CashDeposit> depositOptional;

        try {
            depositOptional=cashdepositRepositiry.getFormFromCSR(customerTransactionRequestId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (!depositOptional.isPresent()){
            responseModel.setMessage("There is no record to update");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        }

        cashDeposit.setCashDepositId(depositOptional.get().getCashDepositId());
        cashDeposit.setCustomerTransactionRequest(customerTransactionRequest.get());

        CashDepositUpdateRecords cashDepositUpdateRecords= new CashDepositUpdateRecords();

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        String extention = detailsUpdateDTO.getFile().getOriginalFilename();
        extention = FilenameUtils.getExtension(extention);

        String location =  ("/cash_deposit/signatures/update_record_verifications/" + customerTransactionRequestId );
        String filename = ""+customerTransactionRequestId + "_uuid-"+ randomUUIDString+extention;
        String url = fileStorage.fileSaveWithRenaming(detailsUpdateDTO.getFile(),location,filename);
        location = ""+location+"/"+filename;
        if(url.equals("Failed")) {
            responseModel.setMessage(" Failed To Upload Signature");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {

            cashDepositUpdateRecords.setSignatureUrl(location);
            cashDepositUpdateRecords.setComment(detailsUpdateDTO.getComment());
            cashDepositUpdateRecords.setCustomerTransactionRequest(customerTransactionRequest.get());

            cashDepositUpdateRecords=cashDepositUpdateRecordsRepository.save(cashDepositUpdateRecords);

            List<CashDepositErrorRecords>cashDepositErrorRecords =new ArrayList<>();
            cashDepositErrorRecords=getAllCashwithDrawalRecordErrors(depositOptional.get(),cashDeposit,cashDepositUpdateRecords);

            cashDepositUpdateRecords.setCashDepositErrorRecords(cashDepositErrorRecords);

            cashDepositUpdateRecords=cashDepositUpdateRecordsRepository.save(cashDepositUpdateRecords);

            if(cashDepositUpdateRecords == null){
                responseModel.setMessage("Something went wrong with the database connection");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.SERVICE_UNAVAILABLE);
            } else {
                try {
                    Optional<Currency> currency = currencyRepository.findById(cashDeposit.getCurrency().getCurrency_id());
                    if(!currency.isPresent()){
                        responseModel.setMessage("Invalied Currency details.");
                        responseModel.setStatus(false);
                        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
                    } else {
                        cashDeposit.setCurrency(currency.get());
                    }
                    cashdepositRepositiry.save(cashDeposit);
                    responseModel.setMessage("Cash Deposit updated successfully");
                    responseModel.setStatus(true);
                    return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            }

        }


    }

    @Override
    public ResponseEntity<?> saveTrasnsactionSignature(TransactionSignatureDTO signatureDTO) throws Exception {
        try {
            Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository.findById(signatureDTO.getCustomerTransactionId());
            if (!customerTransactionRequest.isPresent()) {
                responseModel.setMessage("Invalid Transaction Request");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }

            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if(id != TransactionIdConfig.DEPOSITS){
                responseModel.setMessage("Invalid Transaction Request Id");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        Optional<CashDeposit> depositOptional=cashdepositRepositiry.getFormFromCSR(signatureDTO.getCustomerTransactionId());
        if (!depositOptional.isPresent()){
            responseModel.setMessage("Invalid customer transaction id");
            responseModel.setStatus(false);
            return new ResponseEntity<>("There is no data present for that id", HttpStatus.NO_CONTENT);
        }else {

            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();

            String extention = signatureDTO.getFile().getOriginalFilename();
            extention = FilenameUtils.getExtension(extention);


            String location =  ("/cash_deposit/signatures/" + signatureDTO.getCustomerTransactionId());
            String filename = ""+signatureDTO.getCustomerTransactionId() + "_uuid-"+ randomUUIDString+extention;
            String url = fileStorage.fileSaveWithRenaming(signatureDTO.getFile(),location,filename);
            location = ""+location+"/"+filename;
            if(url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload Signature");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {
                CashDeposit cashDeposit = depositOptional.get();
                cashDeposit.setSignatureUrl(url);
                if(cashdepositRepositiry.save(cashDeposit) != null){
                    responseModel.setMessage("Signature added  successfully");
                    responseModel.setStatus(true);
                    return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
                } else {
                    responseModel.setMessage("Something went wrong with the db connection");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel, HttpStatus.SERVICE_UNAVAILABLE);
                }
            }
        }
    }

    private List<CashDepositErrorRecords> getAllCashwithDrawalRecordErrors(CashDeposit cashDepositOld,CashDeposit cashDepositNew,CashDepositUpdateRecords cashDepositUpdateRecords) throws ParseException {

        List<CashDepositErrorRecords> listOfCashDepositErrorRecords=new ArrayList<>();
        CashDepositErrorRecords cashDepositErrorRecord;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strdate = sdf.format(cashDepositNew.getDate());
        Date dates= sdf.parse(strdate);

        if(cashDepositOld.getDate().compareTo(dates)==0) {
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" Date \":\""+cashDepositOld.getDate()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" Date \":\""+cashDepositNew.getDate()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(!cashDepositOld.getAccountHolderName().equals(cashDepositNew.getAccountHolderName())){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" accountHolder \":\""+cashDepositOld.getAccountHolderName()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" accountHolder \":\""+cashDepositNew.getAccountHolderName()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(!cashDepositOld.getAccountNumber().equals(cashDepositNew.getAccountNumber())){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" accountNo \":\""+cashDepositOld.getAccountNumber()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" accountNo \":\""+cashDepositNew.getAccountNumber()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(!cashDepositOld.getNameOfDepositor().equals(cashDepositNew.getNameOfDepositor())){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" cash Depositors Name \":\""+cashDepositOld.getNameOfDepositor()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" cash Depositors Name \":\""+cashDepositNew.getNameOfDepositor()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(!cashDepositOld.getAddress().equals(cashDepositNew.getAddress())){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" Address \":\""+cashDepositOld.getAddress()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" Address \":\""+cashDepositNew.getAddress()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(!cashDepositOld.getIdentification().equals(cashDepositNew.getIdentification())){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" Identification \":\""+cashDepositOld.getIdentification()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" Identification \":\""+cashDepositNew.getIdentification()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(!cashDepositOld.getPurposeOfDeposit().equals(cashDepositNew.getPurposeOfDeposit())){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" PurposeOfDeposit \":\""+cashDepositOld.getPurposeOfDeposit()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" PurposeOfDeposit \":\""+cashDepositNew.getPurposeOfDeposit()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(!cashDepositOld.getAmmountInWords().equals(cashDepositNew.getAmmountInWords())){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" AmountInWords \":\""+cashDepositOld.getAmmountInWords()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" AmountInWords \":\""+cashDepositNew.getAmmountInWords()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(!cashDepositOld.getPhoneNumberAndExtn().equals(cashDepositNew.getPhoneNumberAndExtn())){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" PhoneNumber \":\""+cashDepositOld.getPhoneNumberAndExtn()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" PhoneNumber \":\""+cashDepositNew.getPhoneNumberAndExtn()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(!cashDepositOld.getCurrency().equals(cashDepositNew.getCurrency())){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" Currency \":\""+cashDepositOld.getCurrency()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" Currency \":\""+cashDepositNew.getCurrency()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(cashDepositNew.getValueOf5000Notes() != cashDepositOld.getValueOf5000Notes()){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" ValueOf5000Notes \":\""+cashDepositOld.getValueOf5000Notes()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" ValueOf5000Notes \":\""+cashDepositNew.getValueOf5000Notes()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(cashDepositNew.getValueOf2000Notes() != cashDepositOld.getValueOf2000Notes()){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" ValueOf2000Notes \":\""+cashDepositOld.getValueOf2000Notes()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" ValueOf2000Notes \":\""+cashDepositNew.getValueOf2000Notes()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(cashDepositNew.getValueof1000Notes() != cashDepositOld.getValueof1000Notes()){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" ValueOf1000Notes \":\""+cashDepositOld.getValueof1000Notes()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" ValueOf1000Notes \":\""+cashDepositNew.getValueof1000Notes()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(cashDepositNew.getValueOf500Notes() != cashDepositOld.getValueOf500Notes()){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" ValueOf500Notes \":\""+cashDepositOld.getValueOf500Notes()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" ValueOf500Notes \":\""+cashDepositNew.getValueOf500Notes()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(cashDepositNew.getValueOf100Notes() != cashDepositOld.getValueOf100Notes()){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" ValueOf100Notes \":\""+cashDepositOld.getValueOf100Notes()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" ValueOf100Notes \":\""+cashDepositNew.getValueOf100Notes()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(cashDepositNew.getValueOf50Notes() != cashDepositOld.getValueOf50Notes()){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" ValueOf50Notes \":\""+cashDepositOld.getValueOf50Notes()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" ValueOf50Notes \":\""+cashDepositNew.getValueOf50Notes()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(cashDepositNew.getValueOf20Notes() != cashDepositOld.getValueOf20Notes()){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" ValueOf20Notes \":\""+cashDepositOld.getValueOf20Notes()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" ValueOf20Notes \":\""+cashDepositNew.getValueOf20Notes()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(cashDepositNew.getValueOf10Notes() != cashDepositOld.getValueOf10Notes()){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" ValueOf10Notes \":\""+cashDepositOld.getValueOf10Notes()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" ValueOf10Notes \":\""+cashDepositNew.getValueOf10Notes()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(cashDepositNew.getValueOfcoins() != cashDepositOld.getValueOfcoins()){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" ValueOfCoins \":\""+cashDepositOld.getValueOfcoins()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" ValueOfCoins \":\""+cashDepositNew.getValueOfcoins()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(cashDepositNew.getTotal() != cashDepositOld.getTotal()){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" Total \":\""+cashDepositOld.getTotal()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" Total \":\""+cashDepositNew.getTotal()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        if(cashDepositNew.getCurrency().getCurrency_id() != cashDepositOld.getCurrency().getCurrency_id()){
            cashDepositErrorRecord = new CashDepositErrorRecords();
            cashDepositErrorRecord.setOldValue("{\" currency \":\""+cashDepositOld.getCurrency().getCurrency()+"\"}");
            cashDepositErrorRecord.setNewValue("{\" currency \":\""+cashDepositNew.getCurrency().getCurrency()+"\"}");
            cashDepositErrorRecord.setCashDepositUpdateRecords(cashDepositUpdateRecords);
            cashDepositErrorRecord = cashDepositErrorRecordsRepository.save(cashDepositErrorRecord);
            listOfCashDepositErrorRecords.add(cashDepositErrorRecord);
        }
        return listOfCashDepositErrorRecords;
    }

    @Override
    public ResponseEntity<?> getCashDepositRequest(int cashDepositId) {
        Optional<CashDeposit> cashDeposit = cashdepositRepositiry.findById(cashDepositId);
        if (!cashDeposit.isPresent()) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("There is no record exits for that Id.");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(cashDeposit, HttpStatus.OK);
        }
    }
}
