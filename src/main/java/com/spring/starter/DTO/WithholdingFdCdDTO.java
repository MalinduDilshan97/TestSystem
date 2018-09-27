package com.spring.starter.DTO;

import com.spring.starter.model.FdCdNumbers;

import java.util.Date;
import java.util.List;

public class WithholdingFdCdDTO {
    private Date maturityDate;
    private List<String> fdCdNumbers;

    public WithholdingFdCdDTO() {
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
}
