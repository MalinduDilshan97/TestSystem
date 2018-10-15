package com.spring.starter.model;

import javax.persistence.*;

@Entity
@Table(name = "bill_payment_referance")
public class BillPaymentReferance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billPaymentReferanceId;

    private String referanceType;

    private String collectionAccountNo;

    private String billerNickname;

    private String billerSubCatogory;

    private String location;

    private String billerType;

    private String collectionAccountType;

    private String collectionAccountBankAndBranch;

    private double minumumAmmountCanPay;

    private double maximumAmmountCanPay;

    private String billerContactEmail;

    private String billerContactPhoneNumber;

    private String inputField_1;

    private String validationRule_1;

    private String inputField_2;

    private String validationRule_2;

    private String inputField_3;

    private String validationRule_3;

    private String inputField_4;

    private String validationRule_4;

    private String inputField_5;

    private String validationRule_5;

    private String comment;

    public BillPaymentReferance() {
    }

    public BillPaymentReferance(String referanceType, String collectionAccountNo, String billerNickname, String billerSubCatogory, String location, String billerType, String collectionAccountType, String collectionAccountBankAndBranch, double minumumAmmountCanPay, double maximumAmmountCanPay, String billerContactEmail, String billerContactPhoneNumber, String inputField_1, String validationRule_1, String inputField_2, String validationRule_2, String inputField_3, String validationRule_3, String inputField_4, String validationRule_4, String inputField_5, String validationRule_5, String comment) {
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

    public int getBillPaymentReferanceId() {
        return billPaymentReferanceId;
    }

    public void setBillPaymentReferanceId(int billPaymentReferanceId) {
        this.billPaymentReferanceId = billPaymentReferanceId;
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
