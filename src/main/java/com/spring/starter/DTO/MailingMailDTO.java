package com.spring.starter.DTO;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MailingMailDTO {

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z.,\\s])*$")
    private String newMailingAddress;
    @NotNull
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String city;
    @NotNull
    private String postalCode;

    @NotNull
    private String stateOrProvince;

    @NotNull
    private List<String> mailingMailAccountNo;

    public MailingMailDTO() {
        super();
    }

    public MailingMailDTO(@NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z.,\\s])*$") String newMailingAddress,
                          @NotNull @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String city, @NotNull String postalCode,
                          @NotNull String stateOrProvince, @NotNull List<String> mailingMailAccountNo) {
        super();
        this.newMailingAddress = newMailingAddress;
        this.city = city;
        this.postalCode = postalCode;
        this.stateOrProvince = stateOrProvince;
        this.mailingMailAccountNo = mailingMailAccountNo;
    }

    public String getNewMailingAddress() {
        return newMailingAddress;
    }

    public void setNewMailingAddress(String newMailingAddress) {
        this.newMailingAddress = newMailingAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public List<String> getMailingMailAccountNo() {
        return mailingMailAccountNo;
    }

    public void setMailingMailAccountNo(List<String> mailingMailAccountNo) {
        this.mailingMailAccountNo = mailingMailAccountNo;
    }
}
