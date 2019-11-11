package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.util.ValidateMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理中监控页面
 * 目前只包括内存占用的监控
 */
@Controller
@RequestMapping("/api")
public class MonitorCtrl {
    @GetMapping("/lockscreen")
    public String lockScreen() {
        return "module/lockscreen";
    }

    @ResponseBody
    @PostMapping("/monitor")
    public String monitor(HttpServletRequest request) {
        if(ValidateMethod.isTokenCheck(request)){
            return "data";
        }else{
            return "123";
        }
    }
}
