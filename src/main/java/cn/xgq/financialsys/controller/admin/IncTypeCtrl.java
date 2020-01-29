package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.domain.IncomeType;
import cn.xgq.financialsys.domain.dto.income.UpdateIncTypeForm;
import cn.xgq.financialsys.service.inter.IncomeTypeSer;
import cn.xgq.financialsys.util.ValidateMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/incometype")
public class IncTypeCtrl {

    @Autowired
    private IncomeTypeSer incomeTypeSer;

    @ResponseBody
    @PostMapping("/queryListUrl")
    public Map<String, Object> queryListUrl(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(req)) {
            List<IncomeType> incTypeList = incomeTypeSer.listIncomeType();
            resultMap.put("data", incTypeList);
            resultMap.put("status", "success");
//            System.out.println(incTypeList);
        }
        return resultMap;
    }

    @ResponseBody
    @GetMapping("/validIncTypeName")
    public boolean validIncTypeName(@RequestParam("incomeType") String incomeType){
        boolean flag=incomeTypeSer.validIncTypeName(incomeType);
        return !flag;
    }

    @ResponseBody
    @PostMapping("/add")
    public boolean addIncomeType(IncomeType incomeType,HttpServletRequest req){
        boolean flag=incomeTypeSer.addIncType(incomeType);
        return flag;
    }

    @ResponseBody
    @PostMapping("/initUpdate")
    public Map<String,Object> initUpdate(@RequestParam("incomeTypeId") String incomeTypeId,HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(ValidateMethod.isTokenCheck(request)){
            IncomeType incomeType=incomeTypeSer.findIncomeType(incomeTypeId);
            resultMap.put("result","success");
            resultMap.put("incomeType",incomeType);
        }else {
            resultMap.put("result","fail");
        }
        return resultMap;
    }

    @ResponseBody
    @GetMapping("/validUpdateName")
    public boolean validUpdateName(@RequestParam("updateIncType") String updateIncType,@RequestParam("oldIncType") String oldIncType){
        if(updateIncType.equals(oldIncType)){
            return true;
        }else{
            return !(incomeTypeSer.validIncTypeName(updateIncType));
        }
    }

    @ResponseBody
    @PostMapping("/update")
    public Map<String,Object> updateIncType(UpdateIncTypeForm form, HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(ValidateMethod.isTokenCheck(request) && incomeTypeSer.updateIncType(form)){
            resultMap.put("success","1");
        }else{
            resultMap.put("success","0");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Map<String,Object> deleteMenu(@RequestParam("incomeTypeId") String id,HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(ValidateMethod.isTokenCheck(request) && incomeTypeSer.deleteIncType(id)){
            resultMap.put("success","1");
        }else{
            resultMap.put("success","0");
        }
        return resultMap;
    }
}
