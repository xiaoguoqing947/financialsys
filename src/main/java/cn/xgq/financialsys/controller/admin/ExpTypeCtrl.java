package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.domain.ExpendType;
import cn.xgq.financialsys.domain.dto.expend.UpdateExpTypeForm;
import cn.xgq.financialsys.service.inter.ExpendTypeSer;
import cn.xgq.financialsys.util.ValidateMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/expendtype")
public class ExpTypeCtrl {
    @Autowired
    private ExpendTypeSer expendTypeSer;

    @ResponseBody
    @PostMapping("/queryListUrl")
    public Map<String, Object> queryListUrl(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(req)) {
            List<ExpendType> expendTypeList = expendTypeSer.listExpendType();
            resultMap.put("data", expendTypeList);
            resultMap.put("status", "success");
            System.out.println(expendTypeList);
        }
        return resultMap;
    }

    @ResponseBody
    @GetMapping("/validExpTypeName")
    public boolean validIncTypeName(@RequestParam("enpendType") String enpendType){
        boolean flag=expendTypeSer.validIncTypeName(enpendType);
        return !flag;
    }

    @ResponseBody
    @PostMapping("/add")
    public boolean addIncomeType(ExpendType expendType,HttpServletRequest req){
        boolean flag=expendTypeSer.addExpType(expendType);
        return flag;
    }

    @ResponseBody
    @PostMapping("/initUpdate")
    public Map<String,Object> initUpdate(@RequestParam("expendTypeId") String expendTypeId,HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(ValidateMethod.isTokenCheck(request)){
            ExpendType expendType=expendTypeSer.findExpendType(expendTypeId);
            resultMap.put("result","success");
            resultMap.put("expendType",expendType);
        }else {
            resultMap.put("result","fail");
        }
        return resultMap;
    }

    @ResponseBody
    @GetMapping("/validUpdateName")
    public boolean validUpdateName(@RequestParam("updateExpType") String updateExpType,@RequestParam("oldExpType") String oldExpType){
        if(updateExpType.equals(oldExpType)){
            return true;
        }else{
            return !(expendTypeSer.validIncTypeName(updateExpType));
        }
    }

    @ResponseBody
    @PostMapping("/update")
    public Map<String,Object> updateIncType(UpdateExpTypeForm form, HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(ValidateMethod.isTokenCheck(request) && expendTypeSer.updateExpType(form)){
            resultMap.put("success","1");
        }else{
            resultMap.put("success","0");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Map<String,Object> deleteMenu(@RequestParam("expendTypeId") String id,HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(ValidateMethod.isTokenCheck(request) && expendTypeSer.deleteExpType(id)){
            resultMap.put("success","1");
        }else{
            resultMap.put("success","0");
        }
        return resultMap;
    }
}
