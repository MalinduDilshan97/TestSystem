package com.spring.starter.service.impl;

import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.service.FundTransferSLIPService;
import com.spring.starter.util.FileStorage;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class FundTransferSLIPServiceImpl implements FundTransferSLIPService {

    @Autowired
    private CustomerTransactionRequestRepository customerTransactionRequestRepository;

    @Autowired
    private FundTransferSLIPRepository fundTransferSLIPRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private FileStorage fileStorage;

    @Autowired
    private FundTransferSLIPSFilesRepository fundTransferSLIPSFilesRepository;

    @Override
    public ResponseEntity<?> addNewFundTransferSlipRequest(FundTransferSLIPS fundTransferSLIPS, int customerTransactionRequestId) throws Exception {
        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(customerTransactionRequestId);
        if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("Invalied Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_SLIP){
            responseModel.setMessage("Invalied Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        Optional<FundTransferSLIPS> optionalTransferSLIPS = fundTransferSLIPRepository.getFormFromCSR(customerTransactionRequestId);
        if(optionalTransferSLIPS.isPresent()){
            fundTransferSLIPS.setFundTransferSLIPSId(optionalTransferSLIPS.get().getFundTransferSLIPSId());
        }

        Optional<Bank> bank = bankRepository.findById(fundTransferSLIPS.getBank().getMx_bank_code());
        if(!bank.isPresent()){
            responseModel.setMessage("invalied bank details.");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        } else {
            fundTransferSLIPS.setBank(bank.get());
        }

        Optional<Branch> branch = branchRepository.findById(fundTransferSLIPS.getBranch().getBranch_id());
        if(!branch.isPresent()){
            responseModel.setMessage("invalied bank branch details.");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }  else {
            fundTransferSLIPS.setBranch(branch.get());
        }

        fundTransferSLIPS.setCustomerTransactionRequest(customerTransactionRequest.get());
        try{
            fundTransferSLIPS = fundTransferSLIPRepository.save(fundTransferSLIPS);
            return new ResponseEntity<>(fundTransferSLIPS,HttpStatus.CREATED);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> addFileToSLIP(MultipartFile file , String fileType, int customerServiceRequestId){
        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(customerServiceRequestId);
        if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("Invalied Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_SLIP){
            responseModel.setMessage("Invalied Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        Optional<FundTransferSLIPS> optionalTransferSLIPS = fundTransferSLIPRepository.getFormFromCSR(customerServiceRequestId);
        if(!optionalTransferSLIPS.isPresent()){
           responseModel.setMessage("There is no record to upload files");
        }   else {
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();

            String extention = file.getOriginalFilename();
            extention = FilenameUtils.getExtension(extention);

            String location =  ("/fundTransferSLIP/file_uploads/" + customerServiceRequestId);
            String filename = customerServiceRequestId + "_uuid-"+ randomUUIDString+"."+extention;
            String url = fileStorage.fileSaveWithRenaming(file,location,filename);
            location = location+"/"+filename;
            if(url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload Signature");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {

                FundTransferSLIPSFiles fundTransferSLIPSFile = new FundTransferSLIPSFiles();
                fundTransferSLIPSFile.setFileUrl(location);
                fundTransferSLIPSFile.setFileType(fileType);
                fundTransferSLIPSFile.setFundTransferSLIPS(optionalTransferSLIPS.get());

                try {
                    fundTransferSLIPSFile = fundTransferSLIPSFilesRepository.save(fundTransferSLIPSFile);
                } catch (Exception e){
                    throw new CustomException(e.getMessage());
                }
                List<FundTransferSLIPSFiles> fundTransferSLIPSFiles;
                if(optionalTransferSLIPS.get().getFundTransferSLIPSIds().isEmpty()){
                    fundTransferSLIPSFiles = new ArrayList<>();
                } else {
                    fundTransferSLIPSFiles = optionalTransferSLIPS.get().getFundTransferSLIPSIds();
                }

                fundTransferSLIPSFiles.add(fundTransferSLIPSFile);
                FundTransferSLIPS fundTransferSLIPS = optionalTransferSLIPS.get();
                fundTransferSLIPS.setFundTransferSLIPSIds(fundTransferSLIPSFiles);

                try {
                    fundTransferSLIPRepository.save(fundTransferSLIPS);
                    responseModel.setMessage("file uploded succesfully");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
                } catch (Exception e) {
                    throw new CustomException(e.getMessage());
                }

            }

        }
        return  null;
    }

    @Override
    public ResponseEntity<?> addSignatureForSLIP(MultipartFile multipartFile, int customerTransactionRequestId){

        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(customerTransactionRequestId);
        if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("Invalied Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_SLIP){
            responseModel.setMessage("Invalied Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        Optional<FundTransferSLIPS> optionalTransferSLIPS = fundTransferSLIPRepository.getFormFromCSR(customerTransactionRequestId);
        if(!optionalTransferSLIPS.isPresent()){
           responseModel.setMessage("There is no record in the db for update");
           responseModel.setStatus(false);
           return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        } else {
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();

            String extention = multipartFile.getOriginalFilename();
            extention = FilenameUtils.getExtension(extention);

            String location =  ("/fundTransferSLIP/signatures/" + customerTransactionRequestId);
            String filename = customerTransactionRequestId + "_uuid-"+ randomUUIDString+"."+extention;
            String url = fileStorage.fileSaveWithRenaming(multipartFile,location,filename);
            location = location+"/"+filename;
            if(url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload Signature");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {
                FundTransferSLIPS fundTransferSLIPS = optionalTransferSLIPS.get();
                fundTransferSLIPS.setUrl(location);

                try{
                    fundTransferSLIPRepository.save(fundTransferSLIPS);
                    responseModel.setMessage("fund transfer saved successfully");
                    responseModel.setStatus(true);
                    return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
                } catch (Exception e) {
                    responseModel.setMessage("Something went wrong with the database connection");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel,HttpStatus.SERVICE_UNAVAILABLE);
                }
            }
        }
    }

    @Override
    public ResponseEntity<?> getFundTransferSlipRequest(int fundTransferSlipID){
        Optional<FundTransferSLIPS> optionalTransferSLIPS = fundTransferSLIPRepository.findById(fundTransferSlipID);
        if(!optionalTransferSLIPS.isPresent()){
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("No content For that id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(optionalTransferSLIPS,HttpStatus.OK);
        }
    }
}
