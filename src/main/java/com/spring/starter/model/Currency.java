package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int currency_id;
    @NotNull
    @Column(unique = true)
    private String currency;

    public Currency() {
    }

    public Currency(@NotNull String currency) {
        this.currency = currency;
    }

    public int getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(int currency_id) {
        this.currency_id = currency_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
