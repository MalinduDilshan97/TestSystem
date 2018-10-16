package com.spring.starter.service.impl;

import com.spring.starter.DTO.FileDTO;
import com.spring.starter.DTO.FundTransferCEFTDTO;
import com.spring.starter.DTO.TransactionSignatureDTO;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.service.FundTransferCEFTService;
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
public class FundTransferCEFTServiceIMPL implements FundTransferCEFTService {

    @Autowired
    CustomerTransactionRequestRepository customerTransactionRequestRepository;

    @Autowired
    FundTransferCEFTRepository fundTransferCEFTRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    BranchRepository branchRepository;
    @Autowired
    FileStorage fileStorage;
    @Autowired
    FundTransferCEFTFileRepository fundTransferCEFTFileRepository;


    @Override
    public ResponseEntity<?> addNewFundTransferCEFTRequest(FundTransferCEFT fundTransferCEFT, int customerTransactionRequestId) throws Exception {
        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(customerTransactionRequestId);
        if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("Invalid Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_CEFT){
            responseModel.setMessage("Invalid Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        Optional<FundTransferCEFT> optionalTransferCEFT = fundTransferCEFTRepository.getFormFromCSR(customerTransactionRequestId);
        if(optionalTransferCEFT.isPresent()){
            fundTransferCEFT.setFundTransferCEFTId(optionalTransferCEFT.get().getFundTransferCEFTId());
        }

        Optional<Bank> bank = bankRepository.findById(fundTransferCEFT.getBank().getMx_bank_code());
        if(!bank.isPresent()){
            responseModel.setMessage("Invalid bank details");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        } else {
            fundTransferCEFT.setBank(bank.get());
        }

        Optional<Branch> branch = branchRepository.findById(fundTransferCEFT.getBranch().getBranch_id());
        if(!branch.isPresent()){
            responseModel.setMessage("Invalid bank branch details");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }  else {
            fundTransferCEFT.setBranch(branch.get());
        }

        fundTransferCEFT.setCustomerTransactionRequest(customerTransactionRequest.get());
        try{
            fundTransferCEFT = fundTransferCEFTRepository.save(fundTransferCEFT);
            return new ResponseEntity<>(fundTransferCEFT,HttpStatus.CREATED);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getFundTransferCEFTRequest(int fundTransferCEFTID) {
        Optional<FundTransferCEFT> optionalTransferCEFT = fundTransferCEFTRepository.findById(fundTransferCEFTID);
        if(!optionalTransferCEFT.isPresent()){
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("No content For that id.");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(optionalTransferCEFT,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> saveCEFTSignature(TransactionSignatureDTO signatureDTO) throws Exception {
        ResponseModel responseModel=new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest;
        try {
            customerTransactionRequest = customerTransactionRequestRepository.findById(signatureDTO.getCustomerTransactionId());
            if (!customerTransactionRequest.isPresent()) {
                responseModel.setMessage("Invalid Transfer CEFT Request!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }

            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_CEFT){
                responseModel.setMessage("Invalid Transfer CEFT Request Id!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

        Optional<FundTransferCEFT> optionalFundTransferCEFT;


        try {
            optionalFundTransferCEFT = fundTransferCEFTRepository.getFormFromCSR(signatureDTO.getCustomerTransactionId());

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        if (!optionalFundTransferCEFT.isPresent()){
            responseModel.setMessage("There is no record to update");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        }else {

            int customerTransactionRequestId =  customerTransactionRequest.get().getCustomerTransactionRequestId();

            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();

            String extention = signatureDTO.getFile().getOriginalFilename();
            extention = FilenameUtils.getExtension(extention);

            String location = ("/Fund_transfer_CEFT/signatures/update_record_verifications/" +customerTransactionRequestId);
            String filename = "" + customerTransactionRequestId + "_uuid-" + randomUUIDString + extention;
            String url = fileStorage.fileSaveWithRenaming(signatureDTO.getFile(), location, filename);
            location = "" + location + "/" + filename;
            if (url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload Signature!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {
                FundTransferCEFT fundTransferCEFT=optionalFundTransferCEFT.get();
                fundTransferCEFT.setUrl(location);

                try {
                    fundTransferCEFTRepository.save(fundTransferCEFT);
                    responseModel.setMessage("Transfer CEFT updated successfully!");
                    responseModel.setStatus(true);
                    return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
                }catch (Exception ex){
                    responseModel.setMessage("Something went wrong with the database connection!");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel, HttpStatus.SERVICE_UNAVAILABLE);
                }

            }
        }
    }

    @Override
    public ResponseEntity<?> uploadFilesToFundTransfers(FileDTO fileDTO) throws Exception {
        ResponseModel responseModel=new ResponseModel();
        try {
            Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                    .findById(fileDTO.getCustomerTransactionRequestId());
            if (!customerTransactionRequest.isPresent()) {
                responseModel.setMessage("Invalid Transaction Request!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }

            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_CEFT){
                responseModel.setMessage("Invalid Transaction Request Id!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        Optional<FundTransferCEFT> optional=fundTransferCEFTRepository.getFormFromCSR(fileDTO
                .getCustomerTransactionRequestId());
        FundTransferCEFTFiles fundTransferCEFTFile = new FundTransferCEFTFiles();
        if(!optional.isPresent()){
            responseModel.setMessage("Invalid customer transaction id!");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.NOT_FOUND);
        } else {
            String location =  ("/fund_transfer/file_uploads/" + fileDTO.getCustomerTransactionRequestId()
                    +"/Customer Files");
            String url = fileStorage.fileSave(fileDTO.getFile(),location);
            if(url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload File!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {

                fundTransferCEFTFile.setFundTransferCEFTS(optional.get());
                fundTransferCEFTFile.setFileType(fileDTO.getFileType());
                fundTransferCEFTFile.setFileUrl(url);

                try {
                    fundTransferCEFTFile = fundTransferCEFTFileRepository.save(fundTransferCEFTFile);
                } catch (Exception e){
                    throw new Exception(e.getMessage());
                }
                if(fundTransferCEFTFile == null){
                    responseModel.setMessage("Something went Wrong!");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
                } else {
                    FundTransferCEFT fundTransferCEFT = optional.get();
                    List<FundTransferCEFTFiles> fundTransferFiles = fundTransferCEFT.getFundTransferCEFTFiles();
                    if(fundTransferFiles.isEmpty()){
                        fundTransferFiles = new ArrayList<>();
                        fundTransferFiles.add(fundTransferCEFTFile);
                    } else {
                        fundTransferFiles.add(fundTransferCEFTFile);
                    }
                    fundTransferCEFT.setFundTransferCEFTFiles(fundTransferFiles);
                    try{
                        fundTransferCEFTRepository.save(fundTransferCEFT);
                        responseModel.setMessage("Withdrawal Files uploded successfull!");
                        responseModel.setStatus(true);
                        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                }

            }
        }
    }

/*    @Override
    public ResponseEntity<?> updateFundTransferCEFTService(MultipartFile file, FundTransferCEFTDTO fundTransferCEFTDTO, int customerServiceRequestId, String comment) throws Exception {
        ResponseModel responseModel=new ResponseModel();
        FundTransferCEFT fundTransferCEFT=new FundTransferCEFT();
        Optional<FundTransferCEFT> optional;
        Optional<CustomerTransactionRequest> customerTransactionRequest;
        try {
            customerTransactionRequest = customerTransactionRequestRepository
                    .findById(customerServiceRequestId);
            if (!customerTransactionRequest.isPresent()) {
                responseModel.setMessage("Invalid Transaction Request!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }

            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_CEFT){
                responseModel.setMessage("Invalid Transaction Request Id!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        try {
            optional = fundTransferCEFTRepository.getFormFromCSR(customerServiceRequestId);

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        if (!optional.isPresent()){
            responseModel.setMessage("There is no record to update");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        }

        Optional<Bank> bank = bankRepository.findById(fundTransferCEFTDTO.getBankId());
        if(!bank.isPresent()){
            responseModel.setMessage("Invalid bank details!");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        } else {
            fundTransferCEFT.setBank(bank.get());
        }

        Optional<Branch> branch = branchRepository.findById(fundTransferCEFT.getBranch().getBranch_id());
        if(!branch.isPresent()){
            responseModel.setMessage("Invalid bank branch details!");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }  else {
            fundTransferCEFT.setBranch(branch.get());
        }
        fundTransferCEFT.setReason(fundTransferCEFTDTO.getReason());
        fundTransferCEFT.setAmmount(fundTransferCEFTDTO.getAmmount());
        fundTransferCEFT.setAccountName(fundTransferCEFTDTO.getAccountName());
        fundTransferCEFT.setCustomerTransactionRequest(customerTransactionRequest.get());
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        String extention = file.getOriginalFilename();
        extention = FilenameUtils.getExtension(extention);

        String location =  ("/fund_Transfer_CEFT/signatures/update_record_verifications/" + customerServiceRequestId );
        String filename = ""+customerServiceRequestId + "_uuid-"+ randomUUIDString+extention;
        String url = fileStorage.fileSaveWithRenaming(file,location,filename);
        location = ""+location+"/"+filename;
        if(url.equals("Failed")) {
            responseModel.setMessage(" Failed To Upload Signature");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {

            fundTransferCEFT.setUrl(location);

            fundTransferCEFT = fundTransferCEFTRepository.save(fundTransferCEFT);


            if(fundTransferCEFT == null){
                responseModel.setMessage("Something went wrong with the database connection!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.SERVICE_UNAVAILABLE);
            } else {
                try {
                    cashWithdrawalRepository.save(cashWithdrawal);
                    responseModel.setMessage("Cash withdrawal updated successfully");
                    responseModel.setStatus(true);
                    return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            }
        }

    }*/


}
