package com.spring.starter.components;

import com.spring.starter.Repository.LoginlogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ScheduledTask {

    @Autowired
    LoginlogsRepository loginlogsRepository;





/*    @Transactional
    @Scheduled(fixedRate = ScheduleConfig.FIVE_HOUR_INTERVAL)
    public void removeDeactivatedAccounts2() {

        try {

            List<ServiceRequestRepository>

        } catch (Exception e) {

            e.printStackTrace();
        }
    }*/


    @Transactional
    @Scheduled(cron = "0 0 9 * * ?")
    public void removeDeactivatedAccounts() {

        try {
            // loginlogsRepository.RemoveOldLoginLogs();
            System.out.println("Scheduled Running");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
