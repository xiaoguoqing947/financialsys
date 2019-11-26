package cn.xgq.financialsys.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class User{
    private  int power;

    private String username;

    private String password;

    private String name;

    private String sex;

    private Date born;

    private String pic;

    private String contactNumber;

    private String email;

    private String address;

    @DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")//页面写入数据库时格式化
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy/MM/dd HH:mm:ss")
    private Date registData;

    @DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")//页面写入数据库时格式化
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy/MM/dd HH:mm:ss")
    private Date oprDate;
}