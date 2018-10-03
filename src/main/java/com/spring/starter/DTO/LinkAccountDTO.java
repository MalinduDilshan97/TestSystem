package com.spring.starter.DTO;

import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class LinkAccountDTO {

    @NotNull
    private String existingBankingUserId;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "internetBankingId")
    @NotNull
    @NotEmpty
    private List<String> accountNumbers;

    public LinkAccountDTO() {
        super();
    }

    public LinkAccountDTO(@NotNull String existingBankingUserId, @NotNull @NotEmpty List<String> accountNumbers) {
        super();
        this.existingBankingUserId = existingBankingUserId;
        this.accountNumbers = accountNumbers;
    }

    public String getExistingBankingUserId() {
        return existingBankingUserId;
    }

    public void setExistingBankingUserId(String existingBankingUserId) {
        this.existingBankingUserId = existingBankingUserId;
    }

    public List<String> getAccountNumbers() {
        return accountNumbers;
    }

    public void setAccountNumbers(List<String> accountNumbers) {
        this.accountNumbers = accountNumbers;
    }

}
