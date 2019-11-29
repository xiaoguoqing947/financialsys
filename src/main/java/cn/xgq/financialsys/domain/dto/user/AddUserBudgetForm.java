package cn.xgq.financialsys.domain.dto.user;

import lombok.Data;

@Data
public class AddUserBudgetForm {
    private String mIncTotalPrice;
    private String mExpMaxPrice;
    private String mExpSuitPrice;
    private String mExpJoyPrice;
    private String mExpShopPrice;
}
