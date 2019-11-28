package cn.xgq.financialsys.domain.dto.expend;

import lombok.Data;

@Data
public class AddExpendForm {
    private String expendTypeId;
    private String expendPrice;
    private String expendUse;
    private String payAccount;
    private String expendDesc;
    private String payMethod;
}
