package com.spring.starter.DTO;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class BankStatementAccountDTO {

    private boolean activateEstatement;

    private boolean cancelEstatement;

    @NotEmpty
    @NotEmpty
    private List<String> bankStatementAccountNo;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email Must be in Correct Format")
    private String email;

    public BankStatementAccountDTO() {
    }

    public BankStatementAccountDTO(boolean activateEstatement, boolean cancelEstatement, List<String> bankStatementAccountNo) {
        this.activateEstatement = activateEstatement;
        this.cancelEstatement = cancelEstatement;
        this.bankStatementAccountNo = bankStatementAccountNo;
    }

    public BankStatementAccountDTO(boolean activateEstatement, boolean cancelEstatement, @NotEmpty @NotEmpty List<String> bankStatementAccountNo, @NotNull @NotBlank @NotEmpty @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email Must be in Correct Format") String email) {
        this.activateEstatement = activateEstatement;
        this.cancelEstatement = cancelEstatement;
        this.bankStatementAccountNo = bankStatementAccountNo;
        this.email = email;
    }

    public boolean isActivateEstatement() {
        return activateEstatement;
    }

    public void setActivateEstatement(boolean activateEstatement) {
        this.activateEstatement = activateEstatement;
    }

    public boolean isCancelEstatement() {
        return cancelEstatement;
    }

    public void setCancelEstatement(boolean cancelEstatement) {
        this.cancelEstatement = cancelEstatement;
    }

    public List<String> getBankStatementAccountNo() {
        return bankStatementAccountNo;
    }

    public void setBankStatementAccountNo(List<String> bankStatementAccountNo) {
        this.bankStatementAccountNo = bankStatementAccountNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
