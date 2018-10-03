package com.spring.starter.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.starter.model.JwtTokens;

public interface JwtTokensRepository extends JpaRepository<JwtTokens, Integer> {

    @Query("SELECT t FROM JwtTokens t WHERE t.token=?1")
    public Optional<JwtTokens> getToken(String token);

    @Query("SELECT t FROM JwtTokens t WHERE t.staffUser.staffId = ?1")
    public Optional<JwtTokens> getLoggedInTokens(int id);

    @Query("SELECT t FROM JwtTokens t WHERE t.token=?1 AND t.isValied = 1")
    public Optional<JwtTokens> getValiedTokens(String token);
}
