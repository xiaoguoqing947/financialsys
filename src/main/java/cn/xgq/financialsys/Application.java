package cn.xgq.financialsys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.MultipartConfigElement;

@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "cn.xgq.financialsys.mapping")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

/*    @Bean
    MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory=new MultipartConfigFactory();
        factory.setLocation("E:/JAVACODE/financialsys/src/main/resources");
        return factory.createMultipartConfig();
    }*/
}
