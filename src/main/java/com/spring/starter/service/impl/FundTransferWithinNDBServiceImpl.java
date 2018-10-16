package com.spring.starter.service.impl;

import com.spring.starter.DTO.DetailsUpdateDTO;
import com.spring.starter.DTO.FileDTO;
import com.spring.starter.DTO.FundTransferWithinNDBDTO;
import com.spring.starter.DTO.TransactionSignatureDTO;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.service.FundTransferWithinNDBService;
import com.spring.starter.util.FileStorage;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class FundTransferWithinNDBServiceImpl implements FundTransferWithinNDBService {

    @Autowired
    private FundTransferWithinNDBRepository fundTransferWithinNDBRepository;
    @Autowired
    private CustomerTransactionRequestRepository customerTransactionRequestRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private FileStorage  fileStorage;
    @Autowired
    private FundTransferWithinNDBFileRepository fundTransferWithinNDBFileRepository;
    @Autowired
    private FundTransferWithinNDBUpdateRecordRepository fundTransferWithinNDBUpdateRecordRepository;
    @Autowired
    private FundTransferWithinNDBErrorRecordsRepository fundTransferWithinNDBErrorRecordsRepository;

    private ResponseModel responseModel = new ResponseModel();

    @Override
    public ResponseEntity<?> saveFundTransferWithinNDBRequest(FundTransferWithinNDBDTO fundTransferWithinNDBDTO, int customerTransactionRequestId) {
        try {

            FundTransferWithinNDB fundTransferWithinNDB= new FundTransferWithinNDB();

            Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository.findById(customerTransactionRequestId);

            if(!customerTransactionRequest.isPresent()){
                responseModel.setMessage("Invalid Transaction Request");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }

            Optional<Branch> branchOptional= branchRepository.findById(fundTransferWithinNDBDTO.getBranchId());

            if(!branchOptional.isPresent()){
                responseModel.setMessage("Invalid Bank Branch");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }

            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if(id != TransactionIdConfig.FUND_TRANSFER_WITHIN_NDB){
                responseModel.setMessage("Invalid Transaction Request Id");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }

            Optional<FundTransferWithinNDB> optional = fundTransferWithinNDBRepository.getFormFromCSR(customerTransactionRequestId);
            if(optional.isPresent()){
                fundTransferWithinNDB.setFundTransferWithinNdbId(optional.get().getFundTransferWithinNdbId());
            }

            fundTransferWithinNDB.setCustomerTransactionRequest(customerTransactionRequest.get());
            fundTransferWithinNDB.setBranch(branchOptional.get());
            fundTransferWithinNDB.setDate(fundTransferWithinNDBDTO.getDate());
            fundTransferWithinNDB.setFromAccount(fundTransferWithinNDBDTO.getFromAccount());
            fundTransferWithinNDB.setFromAccountType(fundTransferWithinNDBDTO.getFromAccountType());
            fundTransferWithinNDB.setCurrency(fundTransferWithinNDBDTO.getCurrency());
            fundTransferWithinNDB.setAmount(fundTransferWithinNDBDTO.getAmount());
            fundTransferWithinNDB.setToAccount(fundTransferWithinNDBDTO.getToAccount());

            FundTransferWithinNDB ndb=fundTransferWithinNDBRepository.save(fundTransferWithinNDB);
            if (ndb!=null){
                return new ResponseEntity<>(ndb,HttpStatus.CREATED);
            }else{
                responseModel.setMessage("Failed To Save");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseModel.setMessage("Failed To Save");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<?> uploadFilesToFundTransferWithinNDBRequest(FileDTO fileDTO) throws Exception {
        try {

            Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                    .findById(fileDTO.getCustomerTransactionRequestId());
            if (!customerTransactionRequest.isPresent()) {
                responseModel.setMessage("Invalid  Transaction  Request");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }

            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if (id != TransactionIdConfig.FUND_TRANSFER_WITHIN_NDB) {
                responseModel.setMessage("Invalid Transaction Request Id");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        Optional<FundTransferWithinNDB> optional = fundTransferWithinNDBRepository.getFormFromCSR(fileDTO.getCustomerTransactionRequestId());

        if (!optional.isPresent()) {
            responseModel.setMessage("Invalid customer transaction id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.NOT_FOUND);
        } else {
            String location = ("/Fund_Transfer_Within_NDB/file_uploads/" + fileDTO.getCustomerTransactionRequestId()
                    + "/Customer Files");
            String url = fileStorage.fileSave(fileDTO.getFile(), location);
            if (url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload Signature");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {

                FundTransferWithinNDBFile fundTransferWithinNDBFile= new FundTransferWithinNDBFile();
                fundTransferWithinNDBFile.setFundTransferWithinNDB(optional.get());
                fundTransferWithinNDBFile.setFileType(fileDTO.getFileType());
                fundTransferWithinNDBFile.setFileUrl(url);

                try {
                    fundTransferWithinNDBFile=fundTransferWithinNDBFileRepository.save(fundTransferWithinNDBFile);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }

                if (fundTransferWithinNDBFile==null){
                    responseModel.setMessage("Failed To Save File");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
                }else{

                    FundTransferWithinNDB fundTransferWithinNDB= optional.get();
                    List<FundTransferWithinNDBFile> transferWithinNDBFiles= optional.get().getFundTransferWithinNDBFiles();

                    if(transferWithinNDBFiles.isEmpty()){
                        transferWithinNDBFiles = new ArrayList<>();
                        transferWithinNDBFiles.add(fundTransferWithinNDBFile);
                    } else {
                        transferWithinNDBFiles.add(fundTransferWithinNDBFile);
                    }
                    fundTransferWithinNDB.setFundTransferWithinNDBFiles(transferWithinNDBFiles);
                    try{
                        fundTransferWithinNDBRepository.save(fundTransferWithinNDB);
                        responseModel.setMessage("Files  uploaded successfully");
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
    public ResponseEntity<?> updateFundTransferWithinNDBRequest(FundTransferWithinNDBDTO fundTransferWithinNDBDTO, int customerTransactionRequestId, DetailsUpdateDTO detailsUpdateDTO) throws Exception {
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
        if(id != TransactionIdConfig.FUND_TRANSFER_WITHIN_NDB){
            responseModel.setMessage("Invalid Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }

        Optional<FundTransferWithinNDB> optionalFundTransferWithinNDB;

        try {
            optionalFundTransferWithinNDB=fundTransferWithinNDBRepository.getFormFromCSR(customerTransactionRequestId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (!optionalFundTransferWithinNDB.isPresent()){
            responseModel.setMessage("There is no record to update");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        }

        Optional<Branch> branchOptional= branchRepository.findById(fundTransferWithinNDBDTO.getBranchId());

        if(!branchOptional.isPresent()){
            responseModel.setMessage("Invalid Bank Branch");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }

        FundTransferWithinNDB fundTransferWithinNDB= new FundTransferWithinNDB();
        fundTransferWithinNDB.setFundTransferWithinNdbId(optionalFundTransferWithinNDB.get().getFundTransferWithinNdbId());
        fundTransferWithinNDB.setCustomerTransactionRequest(customerTransactionRequest.get());
        fundTransferWithinNDB.setBranch(branchOptional.get());
        fundTransferWithinNDB.setDate(fundTransferWithinNDBDTO.getDate());
        fundTransferWithinNDB.setFromAccount(fundTransferWithinNDBDTO.getFromAccount());
        fundTransferWithinNDB.setFromAccountType(fundTransferWithinNDBDTO.getFromAccountType());
        fundTransferWithinNDB.setCurrency(fundTransferWithinNDBDTO.getCurrency());
        fundTransferWithinNDB.setAmount(fundTransferWithinNDBDTO.getAmount());
        fundTransferWithinNDB.setToAccount(fundTransferWithinNDBDTO.getToAccount());

        FundTransferWithinNDBUpdateRecord fundTransferWithinNDBUpdateRecord= new FundTransferWithinNDBUpdateRecord();

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        String extention = detailsUpdateDTO.getFile().getOriginalFilename();
        extention = FilenameUtils.getExtension(extention);

        String location =  ("/fund_transfer_within_ndb/signatures/update_record_verifications/" + customerTransactionRequestId );
        String filename = ""+customerTransactionRequestId + "_uuid-"+ randomUUIDString+extention;
        String url = fileStorage.fileSaveWithRenaming(detailsUpdateDTO.getFile(),location,filename);
        location = ""+location+"/"+filename;
        if(url.equals("Failed")) {
            responseModel.setMessage(" Failed To Upload Signature");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {

            fundTransferWithinNDBUpdateRecord.setSignatureUrl(location);
            fundTransferWithinNDBUpdateRecord.setComment(detailsUpdateDTO.getComment());
            fundTransferWithinNDBUpdateRecord.setCustomerTransactionRequest(customerTransactionRequest.get());

            fundTransferWithinNDBUpdateRecord = fundTransferWithinNDBUpdateRecordRepository.save(fundTransferWithinNDBUpdateRecord);

            List<FundTransferWithinNDBErrorRecords>fundTransferWithinNDBErrorRecords =new ArrayList<>();
            fundTransferWithinNDBErrorRecords=getAllFundtransferWithinNDBRecordErrors(optionalFundTransferWithinNDB.get(),fundTransferWithinNDB,fundTransferWithinNDBUpdateRecord);

            fundTransferWithinNDBUpdateRecord.setFundTransferWithinNDBErrorRecords(fundTransferWithinNDBErrorRecords);

            fundTransferWithinNDBUpdateRecord=fundTransferWithinNDBUpdateRecordRepository.save(fundTransferWithinNDBUpdateRecord);

            if (fundTransferWithinNDBUpdateRecord==null){
                responseModel.setMessage("Something went wrong with the database connection");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.SERVICE_UNAVAILABLE);
            }else{
                try {
                    fundTransferWithinNDBRepository.save(fundTransferWithinNDB);
                    responseModel.setMessage("Fund Transfer Form updated successfully");
                    responseModel.setStatus(true);
                    return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            }

        }

    }

    private List<FundTransferWithinNDBErrorRecords> getAllFundtransferWithinNDBRecordErrors(FundTransferWithinNDB fundTransferWithinNDBOld,FundTransferWithinNDB fundTransferWithinNDBNew,FundTransferWithinNDBUpdateRecord fundTransferWithinNDBUpdateRecord) throws ParseException {

        List<FundTransferWithinNDBErrorRecords> listOfErrorRecords=new ArrayList<>();
        FundTransferWithinNDBErrorRecords fundTransferWithinNDBErrorRecord;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strdate = sdf.format(fundTransferWithinNDBNew.getDate());
        Date dates= sdf.parse(strdate);

        if(fundTransferWithinNDBOld.getDate().compareTo(dates)==0) {
            fundTransferWithinNDBErrorRecord = new FundTransferWithinNDBErrorRecords();
            fundTransferWithinNDBErrorRecord.setOldValue("{\" Date \":\""+fundTransferWithinNDBOld.getDate()+"\"}");
            fundTransferWithinNDBErrorRecord.setNewValue("{\" Date \":\""+fundTransferWithinNDBNew.getDate()+"\"}");
            fundTransferWithinNDBErrorRecord.setFundTransferWithinNDBUpdateRecord(fundTransferWithinNDBUpdateRecord);
            fundTransferWithinNDBErrorRecord = fundTransferWithinNDBErrorRecordsRepository.save(fundTransferWithinNDBErrorRecord);
            listOfErrorRecords.add(fundTransferWithinNDBErrorRecord);
        }
        if(fundTransferWithinNDBOld.getFromAccount() != fundTransferWithinNDBNew.getFromAccount()) {
            fundTransferWithinNDBErrorRecord = new FundTransferWithinNDBErrorRecords();
            fundTransferWithinNDBErrorRecord.setOldValue("{\" Account From \":\""+fundTransferWithinNDBOld.getFromAccount()+"\"}");
            fundTransferWithinNDBErrorRecord.setNewValue("{\" Account From \":\""+fundTransferWithinNDBNew.getFromAccount()+"\"}");
            fundTransferWithinNDBErrorRecord.setFundTransferWithinNDBUpdateRecord(fundTransferWithinNDBUpdateRecord);
            fundTransferWithinNDBErrorRecord = fundTransferWithinNDBErrorRecordsRepository.save(fundTransferWithinNDBErrorRecord);
            listOfErrorRecords.add(fundTransferWithinNDBErrorRecord);
        }
        if(!fundTransferWithinNDBOld.getFromAccountType().equals(fundTransferWithinNDBNew.getFromAccountType())){
            fundTransferWithinNDBErrorRecord = new FundTransferWithinNDBErrorRecords();
            fundTransferWithinNDBErrorRecord.setOldValue("{\" Account Type \":\""+fundTransferWithinNDBOld.getFromAccountType()+"\"}");
            fundTransferWithinNDBErrorRecord.setNewValue("{\" Account Type \":\""+fundTransferWithinNDBNew.getFromAccountType()+"\"}");
            fundTransferWithinNDBErrorRecord.setFundTransferWithinNDBUpdateRecord(fundTransferWithinNDBUpdateRecord);
            fundTransferWithinNDBErrorRecord = fundTransferWithinNDBErrorRecordsRepository.save(fundTransferWithinNDBErrorRecord);
            listOfErrorRecords.add(fundTransferWithinNDBErrorRecord);
        }
        if(!fundTransferWithinNDBOld.getCurrency().equals(fundTransferWithinNDBNew.getCurrency())){
            fundTransferWithinNDBErrorRecord = new FundTransferWithinNDBErrorRecords();
            fundTransferWithinNDBErrorRecord.setOldValue("{\" Currency \":\""+fundTransferWithinNDBOld.getCurrency()+"\"}");
            fundTransferWithinNDBErrorRecord.setNewValue("{\" Currency \":\""+fundTransferWithinNDBNew.getCurrency()+"\"}");
            fundTransferWithinNDBErrorRecord.setFundTransferWithinNDBUpdateRecord(fundTransferWithinNDBUpdateRecord);
            fundTransferWithinNDBErrorRecord = fundTransferWithinNDBErrorRecordsRepository.save(fundTransferWithinNDBErrorRecord);
            listOfErrorRecords.add(fundTransferWithinNDBErrorRecord);
        }
        if(fundTransferWithinNDBOld.getAmount() != fundTransferWithinNDBNew.getAmount()) {
            fundTransferWithinNDBErrorRecord = new FundTransferWithinNDBErrorRecords();
            fundTransferWithinNDBErrorRecord.setOldValue("{\" Amount \":\""+fundTransferWithinNDBOld.getAmount()+"\"}");
            fundTransferWithinNDBErrorRecord.setNewValue("{\" Amount \":\""+fundTransferWithinNDBNew.getAmount()+"\"}");
            fundTransferWithinNDBErrorRecord.setFundTransferWithinNDBUpdateRecord(fundTransferWithinNDBUpdateRecord);
            fundTransferWithinNDBErrorRecord = fundTransferWithinNDBErrorRecordsRepository.save(fundTransferWithinNDBErrorRecord);
            listOfErrorRecords.add(fundTransferWithinNDBErrorRecord);
        }
        if(fundTransferWithinNDBOld.getToAccount() != fundTransferWithinNDBNew.getToAccount()) {
            fundTransferWithinNDBErrorRecord = new FundTransferWithinNDBErrorRecords();
            fundTransferWithinNDBErrorRecord.setOldValue("{\" To Account \":\""+fundTransferWithinNDBOld.getToAccount()+"\"}");
            fundTransferWithinNDBErrorRecord.setNewValue("{\" To Account \":\""+fundTransferWithinNDBNew.getToAccount()+"\"}");
            fundTransferWithinNDBErrorRecord.setFundTransferWithinNDBUpdateRecord(fundTransferWithinNDBUpdateRecord);
            fundTransferWithinNDBErrorRecord = fundTransferWithinNDBErrorRecordsRepository.save(fundTransferWithinNDBErrorRecord);
            listOfErrorRecords.add(fundTransferWithinNDBErrorRecord);
        }
        return listOfErrorRecords;

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
            if (id != TransactionIdConfig.FUND_TRANSFER_WITHIN_NDB) {
                responseModel.setMessage("Invalid Transaction Request Id");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        Optional<FundTransferWithinNDB> optionalWithinNDB = fundTransferWithinNDBRepository.getFormFromCSR(signatureDTO.getCustomerTransactionId());

        if (!optionalWithinNDB.isPresent()) {
            responseModel.setMessage("Invalid customer transaction id");
            responseModel.setStatus(false);
            return new ResponseEntity<>("There is no data present for that id", HttpStatus.NO_CONTENT);
        } else {

            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();

            String extention = signatureDTO.getFile().getOriginalFilename();
            extention = FilenameUtils.getExtension(extention);


            String location = ("/Fund_Transfer_Within_NDB/signatures/" + signatureDTO.getCustomerTransactionId());
            String filename = "" + signatureDTO.getCustomerTransactionId() + "_uuid-" + randomUUIDString + extention;
            String url = fileStorage.fileSaveWithRenaming(signatureDTO.getFile(), location, filename);
            location = "" + location + "/" + filename;
            if (url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload Signature");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {
                FundTransferWithinNDB fundTransferWithinNDB = optionalWithinNDB.get();
                fundTransferWithinNDB.setSignatureUrl(url);

                if (fundTransferWithinNDBRepository.save(fundTransferWithinNDB) != null) {
                    responseModel.setMessage("Signature added  successfully");
                    responseModel.setStatus(true);
                    return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
                } else {
                    responseModel.setMessage("Something  went wrong with the db connection");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel, HttpStatus.SERVICE_UNAVAILABLE);
                }
            }
        }
    }


}
