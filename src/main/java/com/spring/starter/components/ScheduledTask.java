package com.spring.starter.components;

import com.spring.starter.Repository.LoginlogsRepository;
import com.spring.starter.configuration.ScheduleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ScheduledTask {

    @Autowired
    LoginlogsRepository loginlogsRepository;

/*    @Transactional
    @Scheduled(fixedRate = ScheduleConfig.FIVE_SEC_INTERVAL)
    public void removeDeactivatedAccounts() {

        try {

           // loginlogsRepository.RemoveOldLoginLogs();
            System.out.println("Scheduled Running");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
