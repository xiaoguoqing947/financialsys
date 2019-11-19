package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.annotation.SystemControllerLog;
import cn.xgq.financialsys.domain.Menu;
import cn.xgq.financialsys.enums.MessageMeta;
import cn.xgq.financialsys.service.inter.MenuSer;
import cn.xgq.financialsys.util.ValidateMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class MonitorCtrl {

    @Autowired
    private MenuSer menuSer;

    @GetMapping("/lockscreen")
    public String lockScreen() {
        return "module/lockscreen";
    }

    @ResponseBody
    @PostMapping("/monitor")
    public Map<String, Object> monitor(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request)) {
            resultMap.put("status", MessageMeta.sendSuccess.getMsg());
            Map<String, Object> searchMap = new HashMap<String, Object>();
            List<Map<String, Object>> menuList = null;
            menuList = menuSer.listMenu(searchMap);
            resultMap.put("menuList", menuList);
        } else {
            resultMap.put("status", MessageMeta.sendError.getMsg());
        }
        System.err.println(resultMap);
        return resultMap;
    }
}
