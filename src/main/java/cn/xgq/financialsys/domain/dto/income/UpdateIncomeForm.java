package cn.xgq.financialsys.domain.dto.income;

import lombok.Data;

@Data
public class UpdateIncomeForm {
    private String udIncId;
    private String udIncPrice;
    private String udIncSource;
    private String udPayAccount;
    private String udIncDesc;
    private String udIncTypeId;
    private String udPayMethod;
}
