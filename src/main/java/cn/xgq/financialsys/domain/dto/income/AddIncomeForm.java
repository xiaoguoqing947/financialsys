package cn.xgq.financialsys.domain.dto.income;

import lombok.Data;

@Data
public class AddIncomeForm {
    private String incomeTypeId;
    private String incomePrice;
    private String incomeSource;
    private String payAccount;
    private String incomeDesc;
    private String payMethod;
}
