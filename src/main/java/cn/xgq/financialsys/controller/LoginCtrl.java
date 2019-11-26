package cn.xgq.financialsys.controller;


import cn.xgq.financialsys.annotation.SystemControllerLog;
import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.domain.dto.user.UserLoginForm;
import cn.xgq.financialsys.domain.dto.user.UserRegistForm;
import cn.xgq.financialsys.enums.MessageMeta;
import cn.xgq.financialsys.service.UserSerImpl;
import cn.xgq.financialsys.service.inter.UserSer;
import cn.xgq.financialsys.util.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginCtrl {
    @Autowired
    private UserSer userSer;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserSerImpl.class);

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        String result = request.getParameter("result");
        if (result != null && result.equals("fail")) {
            model.addAttribute("success", 0);
        }
        return "login";
    }

    @ResponseBody
    @PostMapping("/login.action")
    public Map<String, Object> doLogin(@RequestBody UserLoginForm form, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        boolean result = userSer.login(form);
        if (result) {
            resultMap.put("status", MessageMeta.sendSuccess.getMsg());
            User user=userSer.findUser(form.getUsername());
            userSer.addSession(request, user);
            resultMap.put("power", user.getPower());
            String token = Token.getTokenString(request.getSession());
            resultMap.put("token", token);
        } else {
            resultMap.put("status", MessageMeta.sendError.getMsg());
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/api/unlock.action")
    public boolean unLockSceen(@RequestBody UserLoginForm form) {
        boolean result = false;
        try {
            result = userSer.login(form);
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return result;
    }

    @GetMapping("/api/logout")
    @SystemControllerLog(description = "退出登录")
    public String logout(HttpServletRequest request) {
        userSer.destroySession(request);
        return "redirect:/login";
    }

    @ResponseBody
    @PostMapping("/register.action")
    public Map<String, Object> doRegister(@RequestBody @Valid UserRegistForm form) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        User user = new User();
        BeanUtils.copyProperties(form, user);
        if (userSer.addUser(user)) {
            resultMap.put("status", MessageMeta.sendSuccess.getMsg());
        } else {
            resultMap.put("status", MessageMeta.sendError.getMsg());
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/validateUname.action")
    public boolean validateUname(@RequestParam String username) {
        boolean flag = userSer.validateUname(username);
        return flag;
    }

    @ResponseBody
    @PostMapping("/api/recoverPwd")
    public Map<String,Object> recoverPwd(@RequestParam String pwd, HttpServletRequest request) {
        Map<String,Object> resultMap=new HashMap<String ,Object>();
        UserLoginForm userLoginForm = (UserLoginForm) request.getSession().getAttribute("admin");
        userLoginForm.setPassword(pwd);
        if(userSer.updatePwd(userLoginForm)){
            resultMap.put("status","success");
        }else{
            resultMap.put("status","fail");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/api/validPwd")
    public boolean validPwd(@RequestParam String oldPwd, HttpServletRequest request) {
//        System.out.println(oldPwd);
        UserLoginForm userLoginForm = (UserLoginForm) request.getSession().getAttribute("admin");
        userLoginForm.setPassword(oldPwd);
        boolean flag=userSer.login(userLoginForm);
        return flag;
    }

}
