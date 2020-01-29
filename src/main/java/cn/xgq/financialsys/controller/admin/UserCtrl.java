package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.annotation.SystemControllerLog;
import cn.xgq.financialsys.domain.Menu;
import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.domain.dto.menu.SearchMenuForm;
import cn.xgq.financialsys.domain.dto.user.SearchUserForm;
import cn.xgq.financialsys.domain.vo.VoMenu;
import cn.xgq.financialsys.service.inter.UserSer;
import cn.xgq.financialsys.util.ValidateMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/user")
public class UserCtrl {
    @Autowired
    private UserSer userSer;
    @ResponseBody
    @PostMapping("/queryListUrl")
    public Map<String, Object> queryListUrl(@RequestBody @Valid SearchUserForm userForm, BindingResult result, HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<User> userList=null;
        if (ValidateMethod.isTokenCheck(req)) {
            resultMap.put("result", "success");
            if (userForm.getCurrentPage() == null || "".equals(userForm.getCurrentPage().trim())) {
                userForm.setCurrentPage("0");
            }
            if (userForm.getPageSize() == null || "".equals(userForm.getPageSize().trim())) {
                userForm.setPageSize("10");
            }
            userForm.setIndexCount(Integer.parseInt(userForm.getCurrentPage()) * Integer.parseInt(userForm.getPageSize()));
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("currentPage", Integer.parseInt(userForm.getCurrentPage()));
            searchMap.put("pageSize", Integer.parseInt(userForm.getPageSize()));
            searchMap.put("indexCount", userForm.getIndexCount());
            searchMap.put("u_code", userForm.getUserCode());
            searchMap.put("u_name", userForm.getUsername());
            int totalCount = userSer.findCount(searchMap);
            Map<String, Object> resultPagerMap = new HashMap<String, Object>();
            resultPagerMap.put("page", userForm.getCurrentPage() + 1);
            resultPagerMap.put("recTotal", totalCount);
            resultPagerMap.put("recPerPage", userForm.getPageSize());
            resultMap.put("pager", resultPagerMap);
            if (totalCount > 0) {
                userList = userSer.findList(searchMap);
                resultMap.put("data", userList);
            }else{
                resultMap.put("message", "查无数据");
            }
        } else {
            resultMap.put("message", "请上传参数");
        }
//        System.out.println(userList);
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/delete")
    @SystemControllerLog(description = "删除用户")
    public Map<String,Object> deleteMenu(@RequestParam("username") String username, HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(ValidateMethod.isTokenCheck(request) && userSer.deleteUser(username)){
            resultMap.put("success","1");
        }else{
            resultMap.put("success","0");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/detail")
    public Map<String,Object> detailMenu(@RequestParam("username") String username,HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(ValidateMethod.isTokenCheck(request)){
            resultMap.put("success","1");
            User user = userSer.findUser(username);
//            System.out.println(user);
            resultMap.put("user",user);
        }else{
            resultMap.put("success","0");
        }
        return resultMap;
    }
}
