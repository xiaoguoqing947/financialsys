package cn.xgq.financialsys.domain;

public class ExpendType {
    private Integer expendTypeId;

    private String enpendType;

    public Integer getExpendTypeId() {
        return expendTypeId;
    }

    public void setExpendTypeId(Integer expendTypeId) {
        this.expendTypeId = expendTypeId;
    }

    public String getEnpendType() {
        return enpendType;
    }

    public void setEnpendType(String enpendType) {
        this.enpendType = enpendType == null ? null : enpendType.trim();
    }
}