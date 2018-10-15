package com.spring.starter.Repository;

import com.spring.starter.model.ServiceRequestTif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


public interface ServiceRequestTifRepository extends JpaRepository<ServiceRequestTif , Integer> {

    @Query("SELECT srf FROM ServiceRequestTif srf WHERE date(srf.date) =?1")
    List<ServiceRequestTif> getTifsOfADate(Date date);

}
