package com.spring.starter.DTO;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerDTO {

    @NotNull
    @NotEmpty
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String name;

    @NotNull
    @NotEmpty
    private String identification;

    @NotNull
    @NotEmpty
    @Size(min = 10)
    @Pattern(regexp = "^([+0-9])*$")
    private String mobileNo;

    @NotNull
    @NotEmpty
    private List<String> accountNos;

    public CustomerDTO() {
        super();
    }

    public CustomerDTO(@NotNull @NotEmpty String name, @NotNull @NotEmpty String identification,
                       @NotNull @NotEmpty String mobileNo, @NotNull @NotEmpty List<String> accountNos) {
        super();
        this.name = name;
        this.identification = identification;
        this.mobileNo = mobileNo;
        this.accountNos = accountNos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public List<String> getAccountNos() {
        return accountNos;
    }

    public void setAccountNos(List<String> accountNos) {
        this.accountNos = accountNos;
    }

}
