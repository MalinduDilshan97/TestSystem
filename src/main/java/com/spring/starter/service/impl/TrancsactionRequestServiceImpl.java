package com.spring.starter.service.impl;

import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.service.TrancsactionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrancsactionRequestServiceImpl implements TrancsactionRequestService {

    @Autowired
    TrancsactionRequestServiceRepository trancsactionRequestServiceRepository;

    @Autowired
    TransactionCustomerRepository transactionCustomerRepository;

    @Autowired
    CustomerTransactionRequestRepository customerTransactionRequestRepository;

    @Autowired
    CashdepositRepositiry cashdepositRepositiry;

    @Autowired
    CashWithdrawalRepository cashWithdrawalRepository;

    @Autowired
    BillPaymentRepository billPaymentRepository;

    @Autowired
    FundTransferCEFTRepository fundTransferCEFTRepository;

    @Autowired
    FundTransferSLIPRepository fundTransferSLIPRepository;

    @Autowired
    FundTransferWithinNDBRepository fundTransferWithinNDBRepository;

    @Autowired
    StaffUserRepository staffUserRepository;


    @Override
    public ResponseEntity<?> addNewServiceRequest(TransactionRequest transactionRequest) {

        ResponseModel responseModel = new ResponseModel();
        try{
            transactionRequest.setDate(java.util.Calendar.getInstance().getTime());
            transactionRequest = trancsactionRequestServiceRepository.save(transactionRequest);
        } catch (Exception e) {
            responseModel.setMessage(e.getMessage());
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.SERVICE_UNAVAILABLE);
        }
        responseModel.setMessage("Transaction Service Created Successfully");
        responseModel.setStatus(true);

        return new ResponseEntity<>(transactionRequest,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> viewTransactionRequest (int customerTransactionRequest) {
        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> optionalCustomerTransactionRequest = customerTransactionRequestRepository
                .findById(customerTransactionRequest);
        if(!optionalCustomerTransactionRequest.isPresent()){
            responseModel.setMessage("There is No such service Available");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.NO_CONTENT);
        }
        int transactionRequrstId = optionalCustomerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(transactionRequrstId == TransactionIdConfig.DEPOSITS) {
            Optional<CashDeposit> cashDeposit = cashdepositRepositiry.getFormFromCSR(customerTransactionRequest);
            if(!cashDeposit.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(cashDeposit,HttpStatus.OK);
            }
        } else if (transactionRequrstId == TransactionIdConfig.WITHDRAWALS){
            Optional<CashWithdrawal> cashWithdrawal = cashWithdrawalRepository
                    .getFormFromCSR(customerTransactionRequest);
            if(!cashWithdrawal.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(cashWithdrawal,HttpStatus.OK);
            }
        } else if (transactionRequrstId == TransactionIdConfig.BILLPAYMENT){
            Optional<BillPayment> billPayment = billPaymentRepository.getFormFromCSR(customerTransactionRequest);
            if(!billPayment.isPresent()) {
                return returnResponse();
            } else {
                return new ResponseEntity<>(billPayment,HttpStatus.OK);
            }
        } else if(transactionRequrstId == TransactionIdConfig.FUND_TRANSFER_WITHIN_NDB) {
            Optional<FundTransferWithinNDB> fundTransferWithinNDB = fundTransferWithinNDBRepository
                    .getFormFromCSR(customerTransactionRequest);
            if(!fundTransferWithinNDB.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(fundTransferWithinNDB,HttpStatus.OK);
            }
        } else if(transactionRequrstId == TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_CEFT) {
            Optional<FundTransferCEFT> optionalTransferCEFT = fundTransferCEFTRepository
                    .getFormFromCSR(customerTransactionRequest);
            if(!optionalTransferCEFT.isPresent()) {
                return returnResponse();
            } else {
                return new ResponseEntity<>(optionalTransferCEFT,HttpStatus.OK);
            }
        } else if(transactionRequrstId == TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_SLIP){
            Optional<FundTransferSLIPS> optionalTransferSLIPS = fundTransferSLIPRepository
                    .getFormFromCSR(customerTransactionRequest);
            if(!optionalTransferSLIPS.isPresent()){
                return returnResponse();
            } else {
                return new ResponseEntity<>(optionalTransferSLIPS,HttpStatus.OK);
            }
        }
        responseModel.setMessage("Invalied Request");
        responseModel.setStatus(false);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    private ResponseEntity<?> returnResponse(){
        ResponseModel responsemodel = new ResponseModel();
        responsemodel.setMessage("Customer Havent fill the form yet");
        responsemodel.setStatus(false);
        return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
    }


    @Override
    public ResponseEntity<?> getBankServices() {
        ResponseModel responseModel = new ResponseModel();
        try {
            List<TransactionRequest> transactionRequests = trancsactionRequestServiceRepository.findAll();
            if(transactionRequests.isEmpty()){
                responseModel.setMessage("No Bank Transctional services added yet");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(transactionRequests,HttpStatus.OK);
            }
        }catch (Exception e){
            responseModel.setMessage("Something Went Wrong with the DB Connection");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.SERVICE_UNAVAILABLE);
        }

    }
    @Override
    public ResponseEntity<?> addCustomer(TransactionCustomer transactionCustomer){
        ResponseModel responseModel = new ResponseModel();
        String num =  transactionCustomer.getMobile();
         if(num.length() == 10){
            num = num.substring(1,10);
            char a_char = num.charAt(0);
            if(a_char != '7'){
                responseModel.setMessage("Please Insert A Correct Mobile number");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
            transactionCustomer.setMobile(num);
        } else if(num.length() == 9){
             char a_char = num.charAt(0);
             if(a_char != '7'){
                 responseModel.setMessage("Please Insert A Correct Mobile number");
                 responseModel.setStatus(false);
                 return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
             }
         }
        try{
            transactionCustomer.setDate(java.util.Calendar.getInstance().getTime());
            transactionCustomer =transactionCustomerRepository.save(transactionCustomer);
            return new ResponseEntity<>(transactionCustomer,HttpStatus.CREATED);
        } catch (Exception e) {
            responseModel.setMessage("Something Went Wrong with the DB Connection");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Override
    public ResponseEntity<?> updateTransactionCustomerDetails(TransactionCustomer transactionCustomer , int recordId){
        ResponseModel responseModel = new ResponseModel();
        Optional<TransactionCustomer> optionalCustomer = transactionCustomerRepository.findById(recordId);
        if(!optionalCustomer.isPresent()){
            responseModel.setMessage("There is No such record in the database");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            transactionCustomer.setTransactionCustomerId(optionalCustomer.get().getTransactionCustomerId());
            try{
                transactionCustomer = transactionCustomerRepository.save(transactionCustomer);
                return new ResponseEntity<>(transactionCustomer,HttpStatus.CREATED);
            } catch (Exception e){
                responseModel.setMessage("There is problem with the database connection");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.SERVICE_UNAVAILABLE);
            }
        }
    }

    @Override
    public ResponseEntity<?> getAllTransactionCustomerDetails(){
        ResponseModel responseModel = new ResponseModel();
        List<TransactionCustomer> transactionCustomers = transactionCustomerRepository.findAll();
        if(transactionCustomers.isEmpty()){
            responseModel.setMessage("No Records yet");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(transactionCustomers,HttpStatus.OK);
        }
    }
    @Override
    public ResponseEntity<?> getOneCustomerDetails(int id){
        ResponseModel responseModel = new ResponseModel();
        Optional<TransactionCustomer> transactionCustomer = transactionCustomerRepository.findById(id);
        if(!transactionCustomer.isPresent()){
            responseModel.setMessage("There Is No Such Record In The Database");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(transactionCustomer,HttpStatus.OK);
        }
    }
    @Override
    public ResponseEntity<?> getCustomerDetailsFormIndentity(String identity){
        ResponseModel responseModel = new ResponseModel();
        List<TransactionCustomer> transactionCustomer = transactionCustomerRepository.getRecordFromIdentity(identity);
        if(transactionCustomer.isEmpty()){
            responseModel.setMessage("There Is No Such Record In The Database.");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(transactionCustomer,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getCustomerDetailsOfAIdentityFilterByDate(String identity, String date){
        ResponseModel responsemodel = new ResponseModel();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDate;
        List<TransactionCustomer> transactionalCustomers = new ArrayList<>();
        try {
            requestDate = df.parse(date);
            transactionalCustomers = transactionCustomerRepository.getTransactionCustomerFilterBydate(identity,requestDate);
            if(transactionalCustomers.isEmpty()){
                responsemodel.setMessage("There is No Data For that Identity");
                responsemodel.setStatus(false);
                return new ResponseEntity<>(responsemodel,HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(transactionalCustomers,HttpStatus.OK);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        responsemodel.setMessage("Something Went Wrong");
        responsemodel.setStatus(false);
        return new ResponseEntity<>(responsemodel,HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Override
    public ResponseEntity<?> getTransactionCustomerDetailsFilterByDate(String date){
        ResponseModel responsemodel = new ResponseModel();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDate;
        List<TransactionCustomer> transactionalCustomers = new ArrayList<>();
        try {
            requestDate = df.parse(date);
            transactionalCustomers = transactionCustomerRepository.getTransactionsOfadate(requestDate);
            if(transactionalCustomers.isEmpty()){
                responsemodel.setMessage("There is No Data For that Identity");
                responsemodel.setStatus(false);
                return new ResponseEntity<>(responsemodel,HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(transactionalCustomers,HttpStatus.OK);
        }catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        responsemodel.setMessage("Something Went Wrong");
        responsemodel.setStatus(false);
        return new ResponseEntity<>(responsemodel,HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Override
    public ResponseEntity<?> addCustomerToATransactionRequest(int transactionCustomerId , int trasactionRequestId){

        ResponseModel responseModel = new ResponseModel();
        Optional<TransactionCustomer> optionalCustomer = transactionCustomerRepository.findById(transactionCustomerId);
        Optional<TransactionRequest> optionalTransactionRequest = trancsactionRequestServiceRepository
                                                                                        .findById(trasactionRequestId);
        if(!optionalCustomer.isPresent()){
            responseModel.setStatus(false);
            responseModel.setMessage("There is no such transaction customer detail exists");
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else if(!optionalTransactionRequest.isPresent()) {
            responseModel.setStatus(false);
            responseModel.setMessage("Invalied Transaction Service Details");
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        }
        CustomerTransactionRequest customerTransactionRequest = new CustomerTransactionRequest();
        customerTransactionRequest.setTransactionRequest(optionalTransactionRequest.get());
        customerTransactionRequest.setTransactionCustomer(optionalCustomer.get());
        customerTransactionRequest.setRequestDate(java.util.Calendar.getInstance().getTime());
        customerTransactionRequest =  customerTransactionRequestRepository.save(customerTransactionRequest);

        return new ResponseEntity<>(customerTransactionRequest,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getAllCustomerTransactionRequests(int customerId){
        ResponseModel responseModel = new ResponseModel();
        Optional<TransactionCustomer> optionalCustomer =transactionCustomerRepository.findById(customerId);
        List<CustomerTransactionRequest> customerTransactionRequests  =customerTransactionRequestRepository
                .getAllTransactionCustomerRequest(customerId);
        if(!optionalCustomer.isPresent()){
            responseModel.setStatus(false);
            responseModel.setMessage("There is such customer record for transaction request");
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else if (customerTransactionRequests.isEmpty()){
            responseModel.setStatus(false);
            responseModel.setMessage("There is such transaction requests for "+optionalCustomer.get().getIdentification());
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(customerTransactionRequests,HttpStatus.OK);
        }
    }

    public ResponseEntity<?> completeTransactionRequest(int transactionCustomerRequest, Principal principal) {
        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(transactionCustomerRequest);

        if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("There is no that kinda request available");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }

        int transactionRequrstId = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();

        if(transactionRequrstId == TransactionIdConfig.DEPOSITS) {
            Optional<CashDeposit> cashDeposit = cashdepositRepositiry.getFormFromCSR(transactionCustomerRequest);
            if(!cashDeposit.isPresent()){
                return returnResponse();
            } else {
                CashDeposit cashDeposit1 = cashDeposit.get();
                if(cashDeposit1.getSignatureUrl() == null || cashDeposit1.getSignatureUrl().isEmpty()) {
                            return withoutProvidingSignature();
                } else {
                    Optional<StaffUser> staffUserOpt = staffUserRepository.findById(Integer.parseInt(principal.getName()));
                    if(!staffUserOpt.isPresent()){
                            return identifiedAsUnauthorizedLogin();
                    } else {

                        CustomerTransactionRequest customerTransactionRequest1 = getCustomerTransactionRequest(
                                customerTransactionRequest,staffUserOpt);

                        cashDeposit1.setStatus(true);
                        cashDeposit1.setCustomerTransactionRequest(customerTransactionRequest1);
                        cashDeposit1.setRequestCompleteDate(java.util.Calendar.getInstance().getTime());



                        cashDeposit1 = cashdepositRepositiry.save(cashDeposit1);

                        return new ResponseEntity<>(cashDeposit1,HttpStatus.OK);
                    }
                }
            }
        } else if (transactionRequrstId == TransactionIdConfig.WITHDRAWALS){
            Optional<CashWithdrawal> cashWithdrawalOpt = cashWithdrawalRepository.getFormFromCSR(transactionCustomerRequest);
            if(!cashWithdrawalOpt.isPresent()){
                return returnResponse();
            } else {
                CashWithdrawal cashWithdrawal = cashWithdrawalOpt.get();
                if(cashWithdrawal.getSignatureUrl() == null || cashWithdrawal.getSignatureUrl().isEmpty()){
                        return withoutProvidingSignature();
                } else {
                    Optional<StaffUser> staffUser = staffUserRepository.findById(Integer.parseInt(principal.getName()));
                    if(!staffUser.isPresent()){
                        return identifiedAsUnauthorizedLogin();
                    } else {
                        CustomerTransactionRequest customerTransactionRequest1 = getCustomerTransactionRequest(
                                customerTransactionRequest,staffUser);

                        cashWithdrawal.setRequestCompleteDate(java.util.Calendar.getInstance().getTime());
                        cashWithdrawal.setCustomerTransactionRequest(customerTransactionRequest1);
                        cashWithdrawal.setStatus(true);

                        cashWithdrawal = cashWithdrawalRepository.save(cashWithdrawal);

                        return new ResponseEntity<>(cashWithdrawal,HttpStatus.OK);
                    }
                }
            }

        } else if (transactionRequrstId == TransactionIdConfig.BILLPAYMENT){
            Optional<BillPayment> billPaymentOpt = billPaymentRepository.getFormFromCSR(transactionCustomerRequest);
            if(!billPaymentOpt.isPresent()){
                return returnResponse();
            } else {
                BillPayment billPayment = billPaymentOpt.get();
                if(billPayment.getSignatureUrl() == null || billPayment.getSignatureUrl().isEmpty()) {
                    return withoutProvidingSignature();
                } else {
                    Optional<StaffUser> staffUserOpt = staffUserRepository.findById(Integer.parseInt(principal.getName()));
                    if(!staffUserOpt.isPresent()){
                        return identifiedAsUnauthorizedLogin();
                    } else {
                        CustomerTransactionRequest customerTransactionRequest1 = getCustomerTransactionRequest(
                                customerTransactionRequest,staffUserOpt);

                        billPayment.setRequestCompleteDate(java.util.Calendar.getInstance().getTime());
                        billPayment.setStatus(false);
                        billPayment.setStatus(true);
                        billPayment.setCustomerTransactionRequest(customerTransactionRequest1);
                        billPayment = billPaymentRepository.save(billPayment);

                        return new ResponseEntity<>(billPayment,HttpStatus.OK);
                    }
                }
            }

        } else if(transactionRequrstId == TransactionIdConfig.FUND_TRANSFER_WITHIN_NDB) {
            Optional<FundTransferWithinNDB> fundTransferWithinNDBOpt = fundTransferWithinNDBRepository.getFormFromCSR
                    (transactionCustomerRequest);
            if(!fundTransferWithinNDBOpt.isPresent()){
                return returnResponse();
            } else {
                FundTransferWithinNDB fundTransferWithinNDB = fundTransferWithinNDBOpt.get();
                if(fundTransferWithinNDB.getSignatureUrl() == null || fundTransferWithinNDB.getSignatureUrl().isEmpty()){
                    return withoutProvidingSignature();
                } else {
                    Optional<StaffUser> staffUser = staffUserRepository.findById(Integer.parseInt(principal.getName()));
                    if(!staffUser.isPresent()){
                        return identifiedAsUnauthorizedLogin();
                    } else {

                        CustomerTransactionRequest customerTransactionRequest1 = getCustomerTransactionRequest
                                                                                 (customerTransactionRequest,staffUser);

                        fundTransferWithinNDB.setRequestCompleteDate(java.util.Calendar.getInstance().getTime());
                        fundTransferWithinNDB.setStatus(true);
                        fundTransferWithinNDB.setCustomerTransactionRequest(customerTransactionRequest1);
                        fundTransferWithinNDB = fundTransferWithinNDBRepository.save(fundTransferWithinNDB);

                        return new ResponseEntity<>(fundTransferWithinNDB,HttpStatus.OK);
                    }
                }
            }

        } else if(transactionRequrstId == TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_CEFT) {
            Optional<FundTransferCEFT> optionalTransferCEFT = fundTransferCEFTRepository.getFormFromCSR(transactionRequrstId);
            if(!optionalTransferCEFT.isPresent()){
                return returnResponse();
            } else {
                FundTransferCEFT fundTransferCEFT = optionalTransferCEFT.get();
                if(fundTransferCEFT.getUrl() == null || fundTransferCEFT.getUrl().isEmpty())
                {
                    return withoutProvidingSignature();
                } else {
                    Optional<StaffUser> staffUser = staffUserRepository.findById(Integer.parseInt(principal.getName()));
                    if(!staffUser.isPresent()){
                        return identifiedAsUnauthorizedLogin();
                    } else {
                        CustomerTransactionRequest customerTransactionRequest1 = getCustomerTransactionRequest
                                (customerTransactionRequest,staffUser);

                        fundTransferCEFT.setStatus(true);
                        fundTransferCEFT.setCustomerTransactionRequest(customerTransactionRequest1);
                        fundTransferCEFT.setRequestCompleteDate(java.util.Calendar.getInstance().getTime());
                        fundTransferCEFT.setStatus(true);

                        fundTransferCEFT = fundTransferCEFTRepository.save(fundTransferCEFT);

                        return new ResponseEntity<>(fundTransferCEFT,HttpStatus.OK);
                    }
                }
            }


        } else if(transactionRequrstId == TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_SLIP){
            Optional<FundTransferSLIPS> optionalTransferSLIPS = fundTransferSLIPRepository.getFormFromCSR(transactionRequrstId);
            if(!optionalTransferSLIPS.isPresent()){
                return returnResponse();
            } else {
                FundTransferSLIPS fundTransferSLIPS = optionalTransferSLIPS.get();
                if(fundTransferSLIPS.getUrl() == null || fundTransferSLIPS.getUrl().isEmpty()) {
                    return withoutProvidingSignature();
                } else {
                    Optional<StaffUser> staffUserOpt = staffUserRepository.findById(Integer.parseInt(principal.getName()));
                    if(!staffUserOpt.isPresent()){
                        return identifiedAsUnauthorizedLogin();
                    } else {
                        CustomerTransactionRequest customerTransactionRequest1 = getCustomerTransactionRequest
                                (customerTransactionRequest,staffUserOpt);

                        fundTransferSLIPS.setStatus(true);
                        fundTransferSLIPS.setRequestCompleteDate(java.util.Calendar.getInstance().getTime());
                        fundTransferSLIPS.setCustomerTransactionRequest(customerTransactionRequest1);

                        fundTransferSLIPS = fundTransferSLIPRepository.save(fundTransferSLIPS);

                        return new  ResponseEntity<>(fundTransferSLIPS,HttpStatus.OK);
                    }
                }
            }

        } else {
            responseModel.setMessage("Invalid request detected");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }

    }

    private ResponseEntity<?> identifiedAsUnauthorizedLogin(){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("Identified as a unauthorized action");
        responseModel.setStatus(false);
        return new ResponseEntity<>(responseModel,HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<?> withoutProvidingSignature(){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("Without providing the signature you cannot complete the request");
        responseModel.setStatus(false);
        return new ResponseEntity<>(responseModel,HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    private CustomerTransactionRequest getCustomerTransactionRequest(Optional<CustomerTransactionRequest> customerTransactionRequest, Optional<StaffUser> staffUser){
        CustomerTransactionRequest customerTransactionRequest1 = customerTransactionRequest.get();
        customerTransactionRequest1.setRequestCompleteDate(java.util.Calendar.getInstance().getTime());
        customerTransactionRequest1.setStatus(true);

        List<StaffUser> staffUsers = new ArrayList<>();

        if(!customerTransactionRequest1.getStaffUser().isEmpty()){
            staffUsers = customerTransactionRequest1.getStaffUser();
        }
        staffUsers.add(staffUser.get());
        customerTransactionRequest1.setStaffUser(staffUsers);
        customerTransactionRequest1 = customerTransactionRequestRepository.save(customerTransactionRequest1);
        return customerTransactionRequest1;
    }

    @Override
    public ResponseEntity<?> getAllUncompleteRequests(String date) {
        ResponseModel responsemodel = new ResponseModel();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDate;
        try {
            requestDate = df.parse(date);
            List<CustomerTransactionRequest> list=customerTransactionRequestRepository.getAllUncompleteRequests(requestDate);
            if (list.isEmpty()){
                responsemodel.setMessage("There is no data present in the database");
                responsemodel.setStatus(false);
                return new ResponseEntity<>(responsemodel, HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(list,HttpStatus.OK);
            }
        } catch (Exception e) {
            throw new CustomException("invalid date format");
        }

    }

    @Override
    public ResponseEntity<?> getAllCustomerTransactionRequestsFilterByDate(int customerId, String date){
        ResponseModel responsemodel = new ResponseModel();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDate;
        List<CustomerServiceRequest> customerServiceRequests = new ArrayList<>();
        try {
            requestDate = df.parse(date);
            List<CustomerTransactionRequest> transactionRequests = customerTransactionRequestRepository.getAllTransactionCustomerRequestsFilterBudate(customerId,requestDate);
            if(transactionRequests.isEmpty()){
                responsemodel.setMessage("There is no data present in the database");
                responsemodel.setStatus(false);
                return new ResponseEntity<>(responsemodel, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(transactionRequests,HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException("invalied date format");
        }
    }

}
