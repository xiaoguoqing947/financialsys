package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.domain.Dictionary;
import cn.xgq.financialsys.domain.IncomeType;
import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.domain.dto.income.SearchIncomeForm;
import cn.xgq.financialsys.service.inter.DicSer;
import cn.xgq.financialsys.service.inter.IncomeSer;
import cn.xgq.financialsys.service.inter.IncomeTypeSer;
import cn.xgq.financialsys.util.ValidateMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
        List<Map<String,Object>> incomeList =null;
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
            User user= (User) req.getSession().getAttribute("admin");
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
            }else{
                resultMap.put("message", "查无数据");
            }
        } else {
            resultMap.put("message", "请上传参数");
        }
        System.out.println(incomeList);
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/initAdd")
    public Map<String,Object> initAdd(HttpServletRequest req){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(req)) {
            //TODO数字字典Ser
            Map<String,Object> searchMap=new HashMap<String, Object>();
            searchMap.put("dcis_code","pay_method");
            List<Dictionary> dictionarys=dicSer.queryAll(searchMap);
            List<IncomeType> incomeTypes=incomeTypeSer.listIncomeType();
            resultMap.put("dataDic",dictionarys);
            resultMap.put("dataIncT",incomeTypes);
        }
        System.out.println(resultMap);
        return resultMap;
    }
}
