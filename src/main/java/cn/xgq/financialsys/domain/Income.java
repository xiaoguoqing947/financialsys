package cn.xgq.financialsys.domain;

import java.util.Date;

public class Income {
    private Integer incomeId;

    private Integer incomeTypeId;

    private String incomeSource;

    private String payMethod;

    private String payAccount;

    private Double incomePrice;

    private Date incomeDate;

    private String incomeUser;

    private String incomeDesc;

    public Integer getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Integer incomeId) {
        this.incomeId = incomeId;
    }

    public Integer getIncomeTypeId() {
        return incomeTypeId;
    }

    public void setIncomeTypeId(Integer incomeTypeId) {
        this.incomeTypeId = incomeTypeId;
    }

    public String getIncomeSource() {
        return incomeSource;
    }

    public void setIncomeSource(String incomeSource) {
        this.incomeSource = incomeSource == null ? null : incomeSource.trim();
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod == null ? null : payMethod.trim();
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount == null ? null : payAccount.trim();
    }

    public Double getIncomePrice() {
        return incomePrice;
    }

    public void setIncomePrice(Double incomePrice) {
        this.incomePrice = incomePrice;
    }

    public Date getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }

    public String getIncomeUser() {
        return incomeUser;
    }

    public void setIncomeUser(String incomeUser) {
        this.incomeUser = incomeUser == null ? null : incomeUser.trim();
    }

    public String getIncomeDesc() {
        return incomeDesc;
    }

    public void setIncomeDesc(String incomeDesc) {
        this.incomeDesc = incomeDesc == null ? null : incomeDesc.trim();
    }
}