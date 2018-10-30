package com.spring.starter.service.impl;

import com.spring.starter.DTO.CSRQueueDTO;
import com.spring.starter.Repository.CSRQueueRepository;
import com.spring.starter.Repository.CustomerRepository;
import com.spring.starter.model.CSRQueue;
import com.spring.starter.model.Customer;
import com.spring.starter.model.ResponseModel;
import com.spring.starter.service.CSRQueueService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CSRQueueServiceImpl implements CSRQueueService {

    @Autowired
    private CSRQueueRepository csrQueueRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ResponseEntity<?> genarateNormalQueuNumber(int customerServiceRequestId){

        ResponseModel responseModel = new ResponseModel();

        Optional<Customer> customerOptional = customerRepository.findById(customerServiceRequestId);

        if(!customerOptional.isPresent()){
            responseModel.setMessage("Invalid customer id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.OK);
        }

        Optional<CSRQueue> csrQueueOptional = csrQueueRepository.checkTokenStatus(customerOptional.get().getCustomerId());
        if(csrQueueOptional.isPresent()){
            CSRQueueDTO csrQueueDTO = new CSRQueueDTO();
            csrQueueDTO.setMessage("You have already taken a token");
            csrQueueDTO.setCsrQueue(csrQueueOptional.get());
            return new ResponseEntity<>(csrQueueDTO,HttpStatus.OK);
        }

        int currentQueueNumber = csrQueueRepository.getCurrentListCount();
        currentQueueNumber = currentQueueNumber +1;

        CSRQueue csrQueue = new CSRQueue();
        csrQueue.setQueueNumber(Integer.toString(currentQueueNumber)+" - C");
        csrQueue.setQueueStartDate(java.util.Calendar.getInstance().getTime());
        csrQueue.setQueuePending(true);
        csrQueue.setQueueNumIdentification(currentQueueNumber);
        csrQueue.setCustomer(customerOptional.get());

        try {
            csrQueue = csrQueueRepository.save(csrQueue);
            return new ResponseEntity<>(csrQueue,HttpStatus.OK);
        } catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(returnQueueNumber(currentQueueNumber+1,customerOptional),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(returnQueueNumber(currentQueueNumber+1,customerOptional),HttpStatus.OK);
        }
    }


    @Override
    public ResponseEntity<?> getCSRQueueList()
    {
        ResponseModel responseModel = new ResponseModel();
        List<CSRQueue> csrQueues  = csrQueueRepository.getAllQueueNumbers();
        if(csrQueues.isEmpty()){
            responseModel.setMessage("There are no queue numbers issued yet");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(csrQueues,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> holdAQueueNumber(String queueNumber){
        Optional<CSRQueue> csrQueueOptional = csrQueueRepository.getCSRQueueById(queueNumber);
        if(!csrQueueOptional.isPresent()){
            return returnResponse();
        }
        CSRQueue csrQueue = csrQueueOptional.get();
        csrQueue.setHold(true);
        csrQueue.setQueuePending(false);
        csrQueue = csrQueueRepository.save(csrQueue);
        return new ResponseEntity<>(csrQueue,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getHoldQueueList(){
        ResponseModel responseModel = new ResponseModel();

        List<CSRQueue> csrQueues = csrQueueRepository.getholdQueue();
        if(csrQueues.isEmpty()){
            responseModel.setMessage("There is no hold queue");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(csrQueues,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getPendingQueue(){
        ResponseModel responseModel = new ResponseModel();

        List<CSRQueue> csrQueues = csrQueueRepository.getpendingQueue();
        if(csrQueues.isEmpty()){
            responseModel.setMessage("There is no pending queue");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(csrQueues,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> completedQueueList(){
        ResponseModel responseModel = new ResponseModel();
        List<CSRQueue> csrQueues = csrQueueRepository.getCompletedQueue();
        if(csrQueues.isEmpty()){
            responseModel.setMessage("There is no complete queue");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(csrQueues,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> continueAHoldQueueNumber(String queueNumber) {

        Optional<CSRQueue> csrQueueOptional = csrQueueRepository.getCSRQueueById(queueNumber);
        if(!csrQueueOptional.isPresent()){
            return returnResponse();
        } else {
            CSRQueue csrQueue = csrQueueOptional.get();
            csrQueue.setHold(false);
            csrQueue.setQueuePending(true);

            csrQueue = csrQueueRepository.save(csrQueue);
            return new ResponseEntity<>(csrQueue,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> issueAPriorityToken(int customerServiceRequestId){
        ResponseModel responseModel = new ResponseModel();
        List<CSRQueue> csrQueues = csrQueueRepository.getpendingQueue();

        Optional<Customer> customerOptional = customerRepository.findById(customerServiceRequestId);

        if(!customerOptional.isPresent()){
            responseModel.setMessage("Invalid customer id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.OK);
        }

        Optional<CSRQueue> csrQueueOptional = csrQueueRepository.checkTokenStatus(customerOptional.get().getCustomerId());
        if(csrQueueOptional.isPresent()){
            CSRQueueDTO csrQueueDTO = new CSRQueueDTO();
            csrQueueDTO.setMessage("You have already taken a token.");
            csrQueueDTO.setCsrQueue(csrQueueOptional.get());
            return new ResponseEntity<>(csrQueueDTO,HttpStatus.OK);
        }

        double number = csrQueues.get(1).getQueueNumIdentification();
        int decimal = (int) number;
        double fraction = number - decimal;
        if(fraction > 0.0){
            for(int i = 2 ; i < csrQueues.size();i++){
                number = csrQueues.get(i).getQueueNumIdentification();
                decimal = (int) number;
                fraction = number - decimal;
                double track = fraction;
                if(fraction == 0.0){
                    number = csrQueues.get(i-1).getQueueNumIdentification();
                    decimal = (int) number;
                    fraction = number - decimal;
                    break;
                } else if (track == 0.19){
                    for(int a = i+1 ; a < csrQueues.size();a++){
                        number = csrQueues.get(i).getQueueNumIdentification();
                        decimal = (int) number;
                        fraction = number - decimal;
                        if(fraction == 0.0){
                            break;
                        }
                    }
                    break;
                }
            }
        }
        if(fraction == 0.0){
            fraction= 0.1;
            number = csrQueues.get(0).getQueueNumIdentification()+fraction;
        } else {
            number = csrQueues.get(0).getQueueNumIdentification()+fraction+0.01;
        }

        CSRQueue csrQueue = new CSRQueue();
        csrQueue.setQueueNumber(Double.toString(number)+" - C");
        csrQueue.setQueueStartDate(java.util.Calendar.getInstance().getTime());
        csrQueue.setQueuePending(true);
        csrQueue.setQueueNumIdentification(number);
        csrQueue.setCustomer(customerOptional.get());

        try {
            csrQueue = csrQueueRepository.save(csrQueue);
            return new ResponseEntity<>(csrQueue, HttpStatus.OK);
        } catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(returnprorityQueueNumber(number+0.01,customerOptional),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(returnprorityQueueNumber(number+0.01,customerOptional),HttpStatus.OK);
        }
    }

    public ResponseEntity<?> issueARedundentToken(int customerServiceRequestId){
        ResponseModel responseModel = new ResponseModel();
        List<CSRQueue> csrQueues = csrQueueRepository.getpendingQueue();

        Optional<Customer> customerOptional = customerRepository.findById(customerServiceRequestId);
        if(!customerOptional.isPresent()){
            responseModel.setMessage("Invalid customer id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.OK);
        }

        double number = csrQueues.get(0).getQueueNumIdentification();
        number = number+0.2;

        CSRQueue csrQueue = new CSRQueue();
        csrQueue.setQueueNumber(Double.toString(number)+" - C");
        csrQueue.setQueueStartDate(java.util.Calendar.getInstance().getTime());
        csrQueue.setQueuePending(true);
        csrQueue.setQueueNumIdentification(number);
        csrQueue.setCustomer(customerOptional.get());


        try {
            csrQueue = csrQueueRepository.save(csrQueue);
            return new ResponseEntity<>(csrQueue, HttpStatus.OK);
        } catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(returnprorityQueueNumber(number+0.02,customerOptional),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(returnprorityQueueNumber(number+0.02,customerOptional),HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> completeAQeueueNumber(String queueNumber){
        Optional<CSRQueue> csrQueueOptional = csrQueueRepository.getCSRQueueById(queueNumber);
        if(!csrQueueOptional.isPresent()) {
            return returnResponse();
        } else {
            CSRQueue csrQueue = csrQueueOptional.get();
            if(csrQueue.isHold()){
                return returnResponseHold();
            } else if(csrQueue.isComplete()) {
                return returnResponseComplete();
            } else if(csrQueue.isQueueReject()){
                return returnResponseReject();
            } else {
                csrQueue.setComplete(true);
                csrQueue.setQueuePending(false);
                csrQueue.setQueueEndDate(java.util.Calendar.getInstance().getTime());
                return new ResponseEntity<>(csrQueue,HttpStatus.OK);
            }
        }
    }

    private ResponseEntity<?> returnResponse(){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("There is no that kinda queue number");
        responseModel.setStatus(false);
        return new ResponseEntity<>(responseModel,HttpStatus.OK);
    }

    private ResponseEntity<?> returnResponseHold(){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("You cant complete a holded queue number ");
        responseModel.setStatus(false);
        return new ResponseEntity<>(responseModel,HttpStatus.OK);
    }

    private ResponseEntity<?> returnResponseComplete(){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("You cant complete an already completed request");
        responseModel.setStatus(false);
        return new ResponseEntity<>(responseModel,HttpStatus.OK);
    }

    private ResponseEntity<?> returnResponseReject(){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("You cant complete a rejected a queue number");
        responseModel.setStatus(false);
        return new ResponseEntity<>(responseModel,HttpStatus.OK);
    }

    private ResponseEntity<?> returnQueueNumber(int currentQueueNumber,Optional<Customer> customerOptional){
        CSRQueue csrQueue = new CSRQueue();
        csrQueue.setQueueNumber(Integer.toString(currentQueueNumber)+" - C");
        csrQueue.setQueueStartDate(java.util.Calendar.getInstance().getTime());
        csrQueue.setQueuePending(true);
        csrQueue.setQueuePending(true);
        csrQueue.setQueueNumIdentification(currentQueueNumber);
        csrQueue.setCustomer(customerOptional.get());

        csrQueue = csrQueueRepository.save(csrQueue);
        return new ResponseEntity<>(csrQueue,HttpStatus.CREATED);
    }

    private ResponseEntity<?> returnprorityQueueNumber(double currentQueueNumber,Optional<Customer> customerOptional){
        CSRQueue csrQueue = new CSRQueue();
        csrQueue.setQueueNumber(Double.toString(currentQueueNumber)+" - C");
        csrQueue.setQueueStartDate(java.util.Calendar.getInstance().getTime());
        csrQueue.setQueuePending(true);
        csrQueue.setQueueNumIdentification(currentQueueNumber);
        csrQueue.setCustomer(customerOptional.get());

        csrQueue = csrQueueRepository.save(csrQueue);
        return new ResponseEntity<>(csrQueue,HttpStatus.CREATED);
    }

    public static void main(String[] args){

        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);

        for (int i = 1 ; i < numbers.size();i++){
            System.out.println(numbers.get(i));
        }

    }

}
