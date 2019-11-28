package cn.xgq.financialsys.domain.dto.expend;

import lombok.Data;

@Data
public class UpdateExpendForm {
    private String udExpId;
    private String udExpPrice;
    private String udExpUse;
    private String udPayAccount;
    private String udExpDesc;
    private String udExpTypeId;
    private String udPayMethod;
}
