package cn.xgq.financialsys.domain;

import java.util.Date;

public class Expend {
    private Integer expendId;

    private Integer expendTypeId;

    private String expendUse;

    private String payMethod;

    private String payAccount;

    private Double expendPrice;

    private Date expendDate;

    private String expendUser;

    private String expendDesc;

    public Integer getExpendId() {
        return expendId;
    }

    public void setExpendId(Integer expendId) {
        this.expendId = expendId;
    }

    public Integer getExpendTypeId() {
        return expendTypeId;
    }

    public void setExpendTypeId(Integer expendTypeId) {
        this.expendTypeId = expendTypeId;
    }

    public String getExpendUse() {
        return expendUse;
    }

    public void setExpendUse(String expendUse) {
        this.expendUse = expendUse == null ? null : expendUse.trim();
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

    public Double getExpendPrice() {
        return expendPrice;
    }

    public void setExpendPrice(Double expendPrice) {
        this.expendPrice = expendPrice;
    }

    public Date getExpendDate() {
        return expendDate;
    }

    public void setExpendDate(Date expendDate) {
        this.expendDate = expendDate;
    }

    public String getExpendUser() {
        return expendUser;
    }

    public void setExpendUser(String expendUser) {
        this.expendUser = expendUser == null ? null : expendUser.trim();
    }

    public String getExpendDesc() {
        return expendDesc;
    }

    public void setExpendDesc(String expendDesc) {
        this.expendDesc = expendDesc == null ? null : expendDesc.trim();
    }
}