package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "estatement_facility")
public class EstatementFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int estatementFacilityID;

    private boolean activateEstatement;

    private boolean cancelEstatement;

    @NotNull
    @NotBlank
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email Must be in Correct Format")
    private String email;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "estatementFacilityID")
    private List<BankStatementAccountNo> bankStatementAccountNo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "csrId")
    private CustomerServiceRequest customerServiceRequest;

    public EstatementFacility() {
    }

    public EstatementFacility(boolean activateEstatement, boolean cancelEstatement, @NotNull @NotBlank @NotEmpty @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email Must be in Correct Format") String email, List<BankStatementAccountNo> bankStatementAccountNo, CustomerServiceRequest customerServiceRequest) {
        this.activateEstatement = activateEstatement;
        this.cancelEstatement = cancelEstatement;
        this.email = email;
        this.bankStatementAccountNo = bankStatementAccountNo;
        this.customerServiceRequest = customerServiceRequest;
    }

    public int getEstatementFacilityID() {
        return estatementFacilityID;
    }

    public void setEstatementFacilityID(int estatementFacilityID) {
        this.estatementFacilityID = estatementFacilityID;
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

    public List<BankStatementAccountNo> getBankStatementAccountNo() {
        return bankStatementAccountNo;
    }

    public void setBankStatementAccountNo(List<BankStatementAccountNo> bankStatementAccountNo) {
        this.bankStatementAccountNo = bankStatementAccountNo;
    }

    public CustomerServiceRequest getCustomerServiceRequest() {
        return customerServiceRequest;
    }

    public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
        this.customerServiceRequest = customerServiceRequest;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
