package cn.xgq.financialsys.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
@Data
@AllArgsConstructor
public class VoIncStics {
    private Integer incomeTypeId;
    private Double incomePrice;
    @DateTimeFormat(pattern="yyyy-MM-dd")//页面写入数据库时格式化
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private String incomeDate;
}
