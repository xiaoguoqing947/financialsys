package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.domain.Dictionary;
import cn.xgq.financialsys.domain.Expend;
import cn.xgq.financialsys.domain.ExpendType;
import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.domain.dto.expend.AddExpendForm;
import cn.xgq.financialsys.domain.dto.expend.SearchExpendForm;
import cn.xgq.financialsys.domain.dto.expend.UpdateExpendForm;
import cn.xgq.financialsys.service.inter.DicSer;
import cn.xgq.financialsys.service.inter.ExpendSer;
import cn.xgq.financialsys.service.inter.ExpendTypeSer;
import cn.xgq.financialsys.util.ValidateMethod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/expend")
public class ExpCtrl {
    @Autowired
    private ExpendSer expendSer;
    @Autowired
    private ExpendTypeSer expendTypeSer;
    @Autowired
    private DicSer dicSer;

    @ResponseBody
    @PostMapping("/queryListUrl")
    public Map<String, Object> queryListUrl(@RequestBody @Valid SearchExpendForm expForm, BindingResult result, HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> expendList = null;
        if (ValidateMethod.isTokenCheck(req)) {
            resultMap.put("result", "success");
            if (expForm.getCurrentPage() == null || "".equals(expForm.getCurrentPage().trim())) {
                expForm.setCurrentPage("0");
            }
            if (expForm.getPageSize() == null || "".equals(expForm.getPageSize().trim())) {
                expForm.setPageSize("10");
            }
            if (expForm.getMinPrice() == null || "".equals(expForm.getCurrentPage().trim())) {
                expForm.setMinPrice("0");
            }
            if (expForm.getMaxPrice() == null || "".equals(expForm.getPageSize().trim())) {
                expForm.setMaxPrice("1000000");
            }
            expForm.setIndexCount(Integer.parseInt(expForm.getCurrentPage()) * Integer.parseInt(expForm.getPageSize()));
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("currentPage", Integer.parseInt(expForm.getCurrentPage()));
            searchMap.put("pageSize", Integer.parseInt(expForm.getPageSize()));
            searchMap.put("indexCount", expForm.getIndexCount());
            searchMap.put("minPrice", expForm.getMinPrice());
            searchMap.put("maxPrice", expForm.getMaxPrice());
            searchMap.put("expendType", expForm.getExpendType());
            User user = (User) req.getSession().getAttribute("admin");
            searchMap.put("expendUser", user.getUsername());//查询指定用户的账单
            int totalCount = expendSer.findCount(searchMap);
            Map<String, Object> resultPagerMap = new HashMap<String, Object>();
            resultPagerMap.put("page", expForm.getCurrentPage() + 1);
            resultPagerMap.put("recTotal", totalCount);
            resultPagerMap.put("recPerPage", expForm.getPageSize());
            resultMap.put("pager", resultPagerMap);
            if (totalCount > 0) {
                expendList = expendSer.listExpend(searchMap);
                Date currentTime = null;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd");
                for (int i = 0; i < expendList.size(); i++) {
                    currentTime = (Date) expendList.get(i).get("expend_date");
                    expendList.get(i).put("expend_date", formatter.format(currentTime));
                }
                resultMap.put("data", expendList);
            } else {
                resultMap.put("message", "查无数据");
            }
        } else {
            resultMap.put("message", "请上传参数");
        }
//        System.out.println(expendList);
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
            List<ExpendType> expendTypes = expendTypeSer.listExpendType();
            resultMap.put("dataDic", dictionarys);
            resultMap.put("dataExpT", expendTypes);
        }
//        System.out.println(resultMap);
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/add")
    public boolean addExpend(@Valid AddExpendForm form, BindingResult result, HttpServletRequest req) {
        boolean flag = false;
        if (ValidateMethod.isTokenCheck(req)) {
            Expend expend = new Expend();
            BeanUtils.copyProperties(form, expend);
            expend.setExpendTypeId(Integer.parseInt(form.getExpendTypeId()));
            expend.setExpendPrice(Double.parseDouble(form.getExpendPrice()));
            expend.setExpendDate(new Date());
            User user = (User) req.getSession().getAttribute("admin");
            expend.setExpendUser(user.getUsername());
            System.out.println(expend.toString());
            flag = expendSer.addExpend(expend);
        }
        return flag;
    }

    @ResponseBody
    @PostMapping("/initUpdate")
    public Map<String, Object> initUpdate(@RequestParam("expendId") String expendId, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Expend expend = null;
        if (ValidateMethod.isTokenCheck(request)) {
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("dcis_code", "pay_method");
            List<Dictionary> dictionarys = dicSer.queryAll(searchMap);
            List<ExpendType> expendTypes = expendTypeSer.listExpendType();
            resultMap.put("dataDic", dictionarys);
            resultMap.put("dataExpT", expendTypes);
            expend = expendSer.findExpend(expendId);
            resultMap.put("result", "success");
            resultMap.put("expend", expend);
        } else {
            resultMap.put("result", "fail");
        }
        System.out.println(resultMap);
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/update")
    public Map<String, Object> updateMenu(UpdateExpendForm form, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request) && expendSer.updateExpend(form, request)) {
            resultMap.put("success", "1");
        } else {
            resultMap.put("success", "0");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Map<String, Object> deleteMenu(@RequestParam("expendId") String id, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(request) && expendSer.deleteExpend(id)) {
            resultMap.put("success", "1");
        } else {
            resultMap.put("success", "0");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/detail")
    public Map<String, Object> detailMenu(@RequestParam("expendId") String id, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Expend expend = null;
        if (ValidateMethod.isTokenCheck(request)) {
            resultMap.put("success", "1");
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("dcis_code", "pay_method");
            List<Dictionary> dictionarys = dicSer.queryAll(searchMap);
            List<ExpendType> expendTypes = expendTypeSer.listExpendType();
            resultMap.put("dataDic", dictionarys);
            resultMap.put("dataExpT", expendTypes);
            expend = expendSer.findExpend(id);
            resultMap.put("expend", expend);
        } else {
            resultMap.put("success", "0");
        }
        return resultMap;
    }
}
