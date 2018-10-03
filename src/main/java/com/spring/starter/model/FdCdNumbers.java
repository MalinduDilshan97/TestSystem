package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "fd_cd_numbers")
public class FdCdNumbers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int FdCdNumbersId;
    @NotNull
    private String fdCdNumber;


    public FdCdNumbers() {
        super();
    }

    public int getFdCdNumbersId() {
        return FdCdNumbersId;
    }

    public void setFdCdNumbersId(int fdCdNumbersId) {
        FdCdNumbersId = fdCdNumbersId;
    }

    public String getFdCdNumber() {
        return fdCdNumber;
    }

    public void setFdCdNumber(String fdCdNumber) {
        this.fdCdNumber = fdCdNumber;
    }


}
