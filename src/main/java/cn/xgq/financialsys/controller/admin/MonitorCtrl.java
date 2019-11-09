package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.util.ValidateMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理中监控页面
 * 目前只包括内存占用的监控
 */
@Controller
public class MonitorCtrl {

    @GetMapping("/admin")
    public String admin() {
        return "admin/monitor";
    }

    @GetMapping("/sysadmin")
    public String sysAdmin() {
        return "admin/sysmonitor";
    }

    @GetMapping("/lockscreen")
    public String lockScreen() {
        return "module/lockscreen";
    }

    @ResponseBody
    @PostMapping("/monitor")
    public String monitor(HttpServletRequest request) {
        if(ValidateMethod.isTokenCheck(request)){
            return "345";
        }else{
            return "123";
        }
    }
}
