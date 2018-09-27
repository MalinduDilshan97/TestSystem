package com.spring.starter.Repository;

import com.spring.starter.model.Loginlogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface LoginlogsRepository extends JpaRepository<Loginlogs, Integer> {


    /**
     * Deletes  Old  Login Logs Lesser Than given month
     */
    @Modifying
    @Query(value = "DELETE FROM login_logs WHERE   login_time < DATE(NOW() - INTERVAL 3 MONTH);", nativeQuery = true)
    void RemoveOldLoginLogs();

}
