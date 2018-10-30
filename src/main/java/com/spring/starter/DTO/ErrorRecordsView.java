package com.spring.starter.DTO;

public class ErrorRecordsView {

    private String valueType;
    private String value;

    public ErrorRecordsView() {
    }

    public ErrorRecordsView(String valueType, String value) {
        this.valueType = valueType;
        this.value = value;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
