package com.spring.starter.Repository;

import com.spring.starter.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency,Integer> {

    @Query("SELECT c FROM Currency c WHERE c.currency =:currency")
    public Optional<Currency> getCurrency(@Param("currency") String currency);

}
