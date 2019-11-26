package cn.xgq.financialsys.domain.dto.income;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SearchIncomeForm {
    private String minPrice;
    private String maxPrice;
    private String incomeType;
    @NotNull
    private String pageSize;
    @NotNull
    private String currentPage;
    @NotNull
    private int indexCount;
}
