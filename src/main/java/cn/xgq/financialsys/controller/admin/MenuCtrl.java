package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.domain.Menu;
import cn.xgq.financialsys.domain.dto.AddMenuForm;
import cn.xgq.financialsys.domain.dto.SearchMenuForm;
import cn.xgq.financialsys.domain.dto.UpdateMenuForm;
import cn.xgq.financialsys.domain.vo.VoMenu;
import cn.xgq.financialsys.service.inter.MenuSer;
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
@RequestMapping(value = "/api/menu")
public class MenuCtrl {
    @Autowired
    private MenuSer menuSer;

    @ResponseBody
    @PostMapping("/menuList")
    public Map<String ,Object> menuList(){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        Map<String,Object> searchMap=new HashMap<String, Object>();
        List<Menu> menuList=menuSer.listMenu(searchMap);
        resultMap.put("data",menuList);
        System.out.println(menuList);
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/queryListUrl")
    public Map<String, Object> queryListUrl(@RequestBody @Valid SearchMenuForm menuForm, BindingResult result, HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Menu> menuList =null;
        if (ValidateMethod.isTokenCheck(req)) {
            resultMap.put("result", "success");
            if (menuForm.getCurrentPage() == null || "".equals(menuForm.getCurrentPage().trim())) {
                menuForm.setCurrentPage("0");
            }
            if (menuForm.getPageSize() == null || "".equals(menuForm.getPageSize().trim())) {
                menuForm.setPageSize("10");
            }
            menuForm.setIndexCount(Integer.parseInt(menuForm.getCurrentPage()) * Integer.parseInt(menuForm.getPageSize()));
            Map<String, Object> searchMap = new HashMap<String, Object>();
            searchMap.put("currentPage", Integer.parseInt(menuForm.getCurrentPage()));
            searchMap.put("pageSize", Integer.parseInt(menuForm.getPageSize()));
            searchMap.put("indexCount", menuForm.getIndexCount());
            searchMap.put("m_code", menuForm.getMenuCode());
            searchMap.put("m_name", menuForm.getMenuNameLike());
            int totalCount = menuSer.findCount(searchMap);
            Map<String, Object> resultPagerMap = new HashMap<String, Object>();
            resultPagerMap.put("page", menuForm.getCurrentPage() + 1);
            resultPagerMap.put("recTotal", totalCount);
            resultPagerMap.put("recPerPage", menuForm.getPageSize());
            resultMap.put("pager", resultPagerMap);
            if (totalCount > 0) {
                menuList = menuSer.listMenu(searchMap);
                List<VoMenu> menus=menuSer.findAllMenuIdAndName();
                resultMap.put("data", menuList);
                resultMap.put("menus", menus);
            }else{
                resultMap.put("message", "查无数据");
            }
        } else {
            resultMap.put("message", "请上传参数");
        }
//        System.out.println(menuList);
        return resultMap;
    }

    @ResponseBody
    @GetMapping("/validMenuName")
    public boolean validMenuName(@RequestParam("name") String name){
        boolean flag=menuSer.validMenuName(name);
        return !flag;
    }

    @ResponseBody
    @GetMapping("/validUpdateName")
    public boolean validUpdateName(@RequestParam("updateName") String updateName,@RequestParam("updateMenuName") String updateMenuName){
        if(updateName.equals(updateMenuName)){
            return true;
        }else{
            return !(menuSer.validMenuName(updateName));
        }
    }

    @ResponseBody
    @PostMapping("/add")
    public boolean addMenu(@Valid  AddMenuForm form, BindingResult result, HttpServletRequest req){
        Menu menu=new Menu();
        BeanUtils.copyProperties(form,menu);
        menu.setCreateTime(new Date());
        menu.setUpdateTime(new Date());
        menu.setIcon("/img/user5.jpg");
        boolean flag=menuSer.addMenu(menu);
        return flag;
    }

    @ResponseBody
    @PostMapping("/initUpdate")
    public Map<String,Object> initUpdate(@RequestParam("menuId") String menuId,HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        List<Menu> menuList =null;
        if(ValidateMethod.isTokenCheck(request)){
            Menu menu=menuSer.findMenu(menuId);
            resultMap.put("result","success");
            resultMap.put("menu",menu);
        }else {
            resultMap.put("result","fail");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/update")
    public Map<String,Object> updateMenu(UpdateMenuForm form,HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(ValidateMethod.isTokenCheck(request) && menuSer.updateMenu(form)){
            resultMap.put("success","1");
        }else{
            resultMap.put("success","0");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Map<String,Object> deleteMenu(@RequestParam("menuId") String id,HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(ValidateMethod.isTokenCheck(request) && menuSer.deleteMenu(id)){
            resultMap.put("success","1");
        }else{
            resultMap.put("success","0");
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/detail")
    public Map<String,Object> detailMenu(@RequestParam("menuId") String id,HttpServletRequest request){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(ValidateMethod.isTokenCheck(request)){
            resultMap.put("success","1");
             Menu menu = menuSer.findMenu(id);
            resultMap.put("menu",menu);
        }else{
            resultMap.put("success","0");
        }
        return resultMap;
    }
}
