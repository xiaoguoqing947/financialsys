package cn.xgq.financialsys.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class Dictionary {
    private Integer id;

    private String dcisCode;

    private String dcisName;

    private String keyValue;

    private String keyName;

    private String remarks;
    @DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")//页面写入数据库时格式化
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy/MM/dd HH:mm:ss")
    private Date oprTime;
}