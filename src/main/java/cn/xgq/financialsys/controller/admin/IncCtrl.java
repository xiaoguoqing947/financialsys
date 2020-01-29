package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.domain.Dictionary;
import cn.xgq.financialsys.domain.Income;
import cn.xgq.financialsys.domain.IncomeType;
import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.domain.dto.income.AddIncomeForm;
import cn.xgq.financialsys.domain.dto.income.SearchIncomeForm;
import cn.xgq.financialsys.domain.dto.income.UpdateIncomeForm;
import cn.xgq.financialsys.service.inter.DicSer;
import cn.xgq.financialsys.service.inter.IncomeSer;
import cn.xgq.financialsys.service.inter.IncomeTypeSer;
import cn.xgq.financialsys.util.ValidateMethod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/income")
public class IncCtrl {
    @Autowired
    private IncomeSer incomeSer;
    @Autowired
    private IncomeTypeSer incomeTypeSer;
    @Autowired
    private DicSer dicSer;

    @ResponseBody
    @PostMapping("/queryListUrl")
    public Map<String, Object> queryListUrl(@RequestBody @Valid SearchIncomeForm incForm, BindingResult result, HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> incomeList = null;
        if (ValidateMethod.isTokenCheck(req)) {
            resultMap.put("result", "success");
            if (incForm.getCurrentPage() == null || "".equals(incForm.getCurrentPage().trim())) {
                incForm.setCurrentPage("0");
            }
            if (incForm.getPageSize() == null || "".equals(incForm.getPageSize().trim())) {
                incForm.setPageSize("10");
            }
            if (incForm.getMinPrice() == null || "".equals(incForm.getCurrentPage().trim())) {
                incForm.setMinPrice("0");
            }
            if (incForm.getMaxPrice() == null || "".equals(incForm.getPageSize().trim())) {
                incForm.setMaxPrice("1000000");
            }
            incForm.setIndexCount(Integer.parseInt(incForm.getCurrentPage()) * Integer.parseInt(incForm.getPageSize()));
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("currentPage", Integer.parseInt(incForm.getCurrentPage()));
            searchMap.put("pageSize", Integer.parseInt(incForm.getPageSize()));
            searchMap.put("indexCount", incForm.getIndexCount());
            searchMap.put("minPrice", incForm.getMinPrice());
            searchMap.put("maxPrice", incForm.getMaxPrice());
            searchMap.put("incomeType", incForm.getIncomeType());
            User user = (User) req.getSession().getAttribute("admin");
            searchMap.put("incomeUser", user.getUsername());//查询指定用户的账单
            int totalCount = incomeSer.findCount(searchMap);
            Map<String, Object> resultPagerMap = new HashMap<String, Object>();
            resultPagerMap.put("page", incForm.getCurrentPage() + 1);
            resultPagerMap.put("recTotal", totalCount);
            resultPagerMap.put("recPerPage", incForm.getPageSize());
            resultMap.put("pager", resultPagerMap);
            if (totalCount > 0) {
                incomeList = incomeSer.listIncome(searchMap);
                resultMap.put("data", incomeList);
            } else {
                resultMap.put("message", "查无数据");
            }
        } else {
            resultMap.put("message", "请上传参数");
        }
//        System.out.println(incomeList);
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/initAdd")
    public Map<String, Object> initAdd(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(req)) {
            //TODO数字字典Ser
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("dcis_code", "pay_method");
            List<Dictionary> dictionarys = dicSer.queryAll(searchMap);
            List<IncomeType> incomeTypes = incomeTypeSer.listIncomeType();
            resultMap.put("dataDic", dictionarys);
            resultMap.put("dataIncT", incomeTypes);
        }
//        System.out.println(resultMap);
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/add")
    public boolean addMenu(@Valid AddIncomeForm form, BindingResult result, HttpServletRequest req) {
        boolean flag=false;
        if (ValidateMethod.isTokenCheck(req)) {
            Income income = new Income();
            BeanUtils.copyProperties(form, income);
            income.setIncomeTypeId(Integer.parseInt(form.getIncomeTypeId()));
            income.setIncomePrice(Double.parseDouble(form.getIncomePrice()));
            income.setIncomeDate(new Date());
            User user = (User) req.getSession().getAttribute("admin");
            income.setIncomeUser(user.getUsername());
            System.out.println(income.toString());
            flag = incomeSer.addIncome(income);
        }
        return flag;
    }

    @ResponseBody
    @PostMapping("/initUpdate")
    public Map<String,Object> initUpdate(@RequestParam("incomeId") String incomeId, HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
       Income income=null;
        if(ValidateMethod.isTokenCheck(request)){
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("dcis_code", "pay_method");
            List<Dictionary> dictionarys = dicSer.queryAll(searchMap);
            List<IncomeType> incomeTypes = incomeTypeSer.listIncomeType();
            resultMap.put("dataDic", dictionarys);
            resultMap.put("dataIncT", incomeTypes);
            income=incomeSer.findIncome(incomeId);
            resultMap.put("result","success");
            resultMap.put("income",income);
        }else {
            resultMap.put("result","fail");
        }
//        System.out.println(resultMap);
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/update")
    public Map<String,Object> updateMenu(UpdateIncomeForm form, HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(ValidateMethod.isTokenCheck(request) && incomeSer.updateIncome(form,request)){
            resultMap.put("success","1");
        }else{
            resultMap.put("success","0");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Map<String,Object> deleteMenu(@RequestParam("incomeId") String id,HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(ValidateMethod.isTokenCheck(request) && incomeSer.deleteIncome(id)){
            resultMap.put("success","1");
        }else{
            resultMap.put("success","0");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/detail")
    public Map<String,Object> detailMenu(@RequestParam("incomeId") String id,HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        Income income=null;
        if(ValidateMethod.isTokenCheck(request)){
            resultMap.put("success","1");
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("dcis_code", "pay_method");
            List<Dictionary> dictionarys = dicSer.queryAll(searchMap);
            List<IncomeType> incomeTypes = incomeTypeSer.listIncomeType();
            resultMap.put("dataDic", dictionarys);
            resultMap.put("dataIncT", incomeTypes);
            income=incomeSer.findIncome(id);
            resultMap.put("income",income);
        }else{
            resultMap.put("success","0");
        }
        return resultMap;
    }
}
