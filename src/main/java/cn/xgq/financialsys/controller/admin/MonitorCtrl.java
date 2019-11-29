package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.domain.Menu;
import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.enums.MessageMeta;
import cn.xgq.financialsys.service.inter.MenuSer;
import cn.xgq.financialsys.service.inter.UserSer;
import cn.xgq.financialsys.util.ValidateMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class MonitorCtrl {

    @Autowired
    private MenuSer menuSer;
    @Autowired
    private UserSer userSer;

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
            List<Menu> menuList = null;
            menuList = menuSer.listMenu(searchMap);
            resultMap.put("menuList", menuList);
        } else {
            resultMap.put("status", MessageMeta.sendError.getMsg());
        }
        System.err.println(resultMap);
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/sysmonitor/queryUserList")
    public Map<String, Object> queryUserList(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request)) {
            Map<String, Object> searchMap = new HashMap<String, Object>();
            List<User> userList = userSer.findList(searchMap);
            resultMap.put("userList", userList);
            resultMap.put("status", "success");
        }else{
            resultMap.put("status", "fail");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/monitor/queryCurrentUser")
    public Map<String, Object> queryCurrentUser(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request)) {
            User user= (User) request.getSession().getAttribute("admin");
            User currentUser = userSer.findUser(user.getUsername());
            resultMap.put("currentUser", currentUser);
            resultMap.put("status", "success");
        }else{
            resultMap.put("status", "fail");
        }
        return resultMap;
    }
}
