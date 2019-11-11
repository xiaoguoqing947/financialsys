package cn.xgq.financialsys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "cn.xgq.financialsys.mapping")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
