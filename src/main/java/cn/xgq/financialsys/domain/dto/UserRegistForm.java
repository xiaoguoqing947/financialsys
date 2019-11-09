package cn.xgq.financialsys.domain.dto;

import com.mysql.cj.protocol.x.XMessage;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRegistForm {
    @NotBlank(message = "名称不能为空")
    private String username;
    @Email(message = "邮箱格式错误")
    private String email;
    @NotBlank(message = "名称不能为空")
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registDate;
}
