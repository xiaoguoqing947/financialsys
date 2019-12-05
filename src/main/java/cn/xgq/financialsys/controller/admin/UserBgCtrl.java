package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.domain.UserBudget;
import cn.xgq.financialsys.domain.dto.user.AddUserBudgetForm;
import cn.xgq.financialsys.service.inter.UserBgSer;
import cn.xgq.financialsys.util.ValidateMethod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/userbudget")
public class UserBgCtrl {
    @Autowired
    private UserBgSer userBgSer;

    @ResponseBody
    @PostMapping("/initAdd")
    public Map<String,Object> initAdd(HttpServletRequest req){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        User user= (User) req.getSession().getAttribute("admin");
        if (ValidateMethod.isTokenCheck(req) && userBgSer.IsExistUser(user.getUsername())) {
            resultMap.put("IsHasData","yes");
        }else{
            resultMap.put("IsHasData","no");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/add")
    public boolean addMenu(AddUserBudgetForm form, BindingResult result, HttpServletRequest req) {
        boolean flag=false;
        if (ValidateMethod.isTokenCheck(req)) {
            UserBudget userBudget = new UserBudget();
            userBudget.setmIncTotalPrice(Double.parseDouble(form.getMIncTotalPrice()));
            userBudget.setmExpMaxPrice(Double.parseDouble(form.getMExpMaxPrice()));
            userBudget.setmExpSuitPrice(Double.parseDouble(form.getMExpSuitPrice()));
            userBudget.setmExpJoyPrice(Double.parseDouble(form.getMExpJoyPrice()));
            userBudget.setmExpShopPrice(Double.parseDouble(form.getMExpShopPrice()));
            User user = (User) req.getSession().getAttribute("admin");
            userBudget.setUsername(user.getUsername());
            System.out.println(userBudget.toString());
            flag = userBgSer.addUserBudget(userBudget);
        }
        return flag;
    }

    @ResponseBody
    @PostMapping("/update")
    public Map<String,Object> updateMenu(UserBudget form, HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(ValidateMethod.isTokenCheck(request) && userBgSer.updateUserBg(form,request)){
            resultMap.put("success","1");
        }else{
            resultMap.put("success","0");
        }
        return resultMap;
    }

}
