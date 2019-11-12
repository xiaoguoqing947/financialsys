package cn.xgq.financialsys.domain;

import java.util.Date;

public class Dictionary {
    private Integer id;

    private String dcisCode;

    private String dcisName;

    private String keyValue;

    private String keyName;

    private String remarks;

    private Date oprTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDcisCode() {
        return dcisCode;
    }

    public void setDcisCode(String dcisCode) {
        this.dcisCode = dcisCode == null ? null : dcisCode.trim();
    }

    public String getDcisName() {
        return dcisName;
    }

    public void setDcisName(String dcisName) {
        this.dcisName = dcisName == null ? null : dcisName.trim();
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue == null ? null : keyValue.trim();
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName == null ? null : keyName.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Date getOprTime() {
        return oprTime;
    }

    public void setOprTime(Date oprTime) {
        this.oprTime = oprTime;
    }
}