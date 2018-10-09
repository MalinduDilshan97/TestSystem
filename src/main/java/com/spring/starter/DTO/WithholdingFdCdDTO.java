package com.spring.starter.DTO;

import com.spring.starter.model.FdCdNumbers;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

public class WithholdingFdCdDTO {

    private Date maturityDate;
    @NotNull
    private List<String> fdCdNumbers;
    @NotNull
    @Pattern(regexp = "^(FD|CD)$", message = "Input must be 'FD' or 'CD'")
    private String accountType;

    public WithholdingFdCdDTO() {
    }

    public WithholdingFdCdDTO(Date maturityDate, @NotNull List<String> fdCdNumbers, @NotNull @Pattern(regexp = "^(FD|CD)$", message = "Input must be 'FD' or 'CD'") String accountType) {
        this.maturityDate = maturityDate;
        this.fdCdNumbers = fdCdNumbers;
        this.accountType = accountType;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public List<String> getFdCdNumbers() {
        return fdCdNumbers;
    }

    public void setFdCdNumbers(List<String> fdCdNumbers) {
        this.fdCdNumbers = fdCdNumbers;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
