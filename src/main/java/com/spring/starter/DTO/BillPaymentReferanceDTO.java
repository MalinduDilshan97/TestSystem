package com.spring.starter.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class BillPaymentReferanceDTO {

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String referanceType;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String collectionAccountNo;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String billerNickname;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String billerSubCatogory;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String location;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String billerType;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String collectionAccountType;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String collectionAccountBankAndBranch;

    @NotNull
    private double minumumAmmountCanPay;

    @NotNull
    private double maximumAmmountCanPay;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String billerContactEmail;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String billerContactPhoneNumber;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String inputField_1;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String validationRule_1;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String inputField_2;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String validationRule_2;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String inputField_3;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String validationRule_3;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String inputField_4;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String validationRule_4;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String inputField_5;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String validationRule_5;

    @NotNull
    @Size(min = 2)
    @Pattern(regexp = "^([A-Za-z0-9_\\s])*$")
    private String comment;

    public BillPaymentReferanceDTO() {
    }

    public BillPaymentReferanceDTO(@NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String referanceType,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String collectionAccountNo,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String billerNickname,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String billerSubCatogory,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String location,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String billerType,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String collectionAccountType,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String collectionAccountBankAndBranch,
                                   @NotNull double minumumAmmountCanPay, @NotNull double maximumAmmountCanPay,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String billerContactEmail,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String billerContactPhoneNumber,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String inputField_1,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String validationRule_1,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String inputField_2,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String validationRule_2,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String inputField_3,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String validationRule_3,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String inputField_4,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String validationRule_4,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String inputField_5,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String validationRule_5,
                                   @NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_\\s])*$") String comment) {
        this.referanceType = referanceType;
        this.collectionAccountNo = collectionAccountNo;
        this.billerNickname = billerNickname;
        this.billerSubCatogory = billerSubCatogory;
        this.location = location;
        this.billerType = billerType;
        this.collectionAccountType = collectionAccountType;
        this.collectionAccountBankAndBranch = collectionAccountBankAndBranch;
        this.minumumAmmountCanPay = minumumAmmountCanPay;
        this.maximumAmmountCanPay = maximumAmmountCanPay;
        this.billerContactEmail = billerContactEmail;
        this.billerContactPhoneNumber = billerContactPhoneNumber;
        this.inputField_1 = inputField_1;
        this.validationRule_1 = validationRule_1;
        this.inputField_2 = inputField_2;
        this.validationRule_2 = validationRule_2;
        this.inputField_3 = inputField_3;
        this.validationRule_3 = validationRule_3;
        this.inputField_4 = inputField_4;
        this.validationRule_4 = validationRule_4;
        this.inputField_5 = inputField_5;
        this.validationRule_5 = validationRule_5;
        this.comment = comment;
    }

    public String getReferanceType() {
        return referanceType;
    }

    public void setReferanceType(String referanceType) {
        this.referanceType = referanceType;
    }

    public String getCollectionAccountNo() {
        return collectionAccountNo;
    }

    public void setCollectionAccountNo(String collectionAccountNo) {
        this.collectionAccountNo = collectionAccountNo;
    }

    public String getBillerNickname() {
        return billerNickname;
    }

    public void setBillerNickname(String billerNickname) {
        this.billerNickname = billerNickname;
    }

    public String getBillerSubCatogory() {
        return billerSubCatogory;
    }

    public void setBillerSubCatogory(String billerSubCatogory) {
        this.billerSubCatogory = billerSubCatogory;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBillerType() {
        return billerType;
    }

    public void setBillerType(String billerType) {
        this.billerType = billerType;
    }

    public String getCollectionAccountType() {
        return collectionAccountType;
    }

    public void setCollectionAccountType(String collectionAccountType) {
        this.collectionAccountType = collectionAccountType;
    }

    public String getCollectionAccountBankAndBranch() {
        return collectionAccountBankAndBranch;
    }

    public void setCollectionAccountBankAndBranch(String collectionAccountBankAndBranch) {
        this.collectionAccountBankAndBranch = collectionAccountBankAndBranch;
    }

    public double getMinumumAmmountCanPay() {
        return minumumAmmountCanPay;
    }

    public void setMinumumAmmountCanPay(double minumumAmmountCanPay) {
        this.minumumAmmountCanPay = minumumAmmountCanPay;
    }

    public double getMaximumAmmountCanPay() {
        return maximumAmmountCanPay;
    }

    public void setMaximumAmmountCanPay(double maximumAmmountCanPay) {
        this.maximumAmmountCanPay = maximumAmmountCanPay;
    }

    public String getBillerContactEmail() {
        return billerContactEmail;
    }

    public void setBillerContactEmail(String billerContactEmail) {
        this.billerContactEmail = billerContactEmail;
    }

    public String getBillerContactPhoneNumber() {
        return billerContactPhoneNumber;
    }

    public void setBillerContactPhoneNumber(String billerContactPhoneNumber) {
        this.billerContactPhoneNumber = billerContactPhoneNumber;
    }

    public String getInputField_1() {
        return inputField_1;
    }

    public void setInputField_1(String inputField_1) {
        this.inputField_1 = inputField_1;
    }

    public String getValidationRule_1() {
        return validationRule_1;
    }

    public void setValidationRule_1(String validationRule_1) {
        this.validationRule_1 = validationRule_1;
    }

    public String getInputField_2() {
        return inputField_2;
    }

    public void setInputField_2(String inputField_2) {
        this.inputField_2 = inputField_2;
    }

    public String getValidationRule_2() {
        return validationRule_2;
    }

    public void setValidationRule_2(String validationRule_2) {
        this.validationRule_2 = validationRule_2;
    }

    public String getInputField_3() {
        return inputField_3;
    }

    public void setInputField_3(String inputField_3) {
        this.inputField_3 = inputField_3;
    }

    public String getValidationRule_3() {
        return validationRule_3;
    }

    public void setValidationRule_3(String validationRule_3) {
        this.validationRule_3 = validationRule_3;
    }

    public String getInputField_4() {
        return inputField_4;
    }

    public void setInputField_4(String inputField_4) {
        this.inputField_4 = inputField_4;
    }

    public String getValidationRule_4() {
        return validationRule_4;
    }

    public void setValidationRule_4(String validationRule_4) {
        this.validationRule_4 = validationRule_4;
    }

    public String getInputField_5() {
        return inputField_5;
    }

    public void setInputField_5(String inputField_5) {
        this.inputField_5 = inputField_5;
    }

    public String getValidationRule_5() {
        return validationRule_5;
    }

    public void setValidationRule_5(String validationRule_5) {
        this.validationRule_5 = validationRule_5;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
