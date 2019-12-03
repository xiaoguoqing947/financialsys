package cn.xgq.financialsys.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
public class VoExpStics {
    private Integer expendTypeId;
    private Double expendPrice;
    @DateTimeFormat(pattern="yyyy-MM-dd")//页面写入数据库时格式化
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private String expendDate;
}
