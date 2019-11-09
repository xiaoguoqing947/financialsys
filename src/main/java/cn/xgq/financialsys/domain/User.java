package cn.xgq.financialsys.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User implements java.io.Serializable{
    private String username;

    private String password;

    private String name;

    private String sex;

    private Date born;

    private String pic;

    private String contactNumber;

    private String email;

    private String address;

    private Date registDate;

    private Date oprDate;
}