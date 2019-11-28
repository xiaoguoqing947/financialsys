package cn.xgq.financialsys.domain.dto.expend;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class SearchExpendForm {
    private String minPrice;
    private String maxPrice;
    private String expendType;
    @NotNull
    private String pageSize;
    @NotNull
    private String currentPage;
    @NotNull
    private int indexCount;
}
