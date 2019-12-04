package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.domain.vo.PublicChartsInfo;
import cn.xgq.financialsys.domain.vo.VoExpStics;
import cn.xgq.financialsys.domain.vo.VoIncStics;
import cn.xgq.financialsys.service.inter.ExpendSer;
import cn.xgq.financialsys.service.inter.IncomeSer;
import cn.xgq.financialsys.util.DateUtils;
import cn.xgq.financialsys.util.PersonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/api/Statistics")
public class StatisticsCtrl {
    @Autowired
    private ExpendSer expendSer;
    @Autowired
    private IncomeSer incomeSer;

    @PostMapping("/expend")
    @ResponseBody
    public Map<String, Object> expendStics(@RequestParam(value = "mouth") String mouth, @RequestParam(value = "year") String year, HttpServletRequest request) {
        if( mouth.trim() == ""  && year.trim() == "" ){
            mouth=DateUtils.getDateStr(new Date());//设置默认值  数据统计时的默认值
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> searchMap = new HashMap<String, Object>();
        User user = (User) request.getSession().getAttribute("admin");
        searchMap.put("expendUser", user.getUsername());//查询指定用户的账单
        PublicChartsInfo publicChartsInfo = null;
        if (mouth != null && mouth.trim() != "") {
            List<Integer> list = DateUtils.getNumberInStr(mouth);
            int days = DateUtils.getDays(list.get(0), list.get(1));
            publicChartsInfo = new PublicChartsInfo(days);
            //数据库查询
            searchMap.put("year", list.get(0));
            searchMap.put("mouth", list.get(1));
            List<VoExpStics> lists = expendSer.findExpStatistics(searchMap);
            publicChartsInfo.setChartData(PersonUtils.getExpChartData(lists,days,2));
            publicChartsInfo.setSeriesName("消费支出");
            resultMap.put("lineChart", publicChartsInfo);
        }
        if (year != null && year.trim() != "") {
            List<Integer> list = DateUtils.getNumberInStr(year);
            //数据库查询
            searchMap.put("year", list.get(0));
            publicChartsInfo = new PublicChartsInfo(String.valueOf(list.get(0)));
            List<VoExpStics> lists = expendSer.findExpStatistics(searchMap);
            publicChartsInfo.setChartData(PersonUtils.getExpChartData(lists,12,1));
            publicChartsInfo.setSeriesName("消费支出");
            resultMap.put("lineChart", publicChartsInfo);
        }
        return resultMap;
    }

    @PostMapping("/income")
    @ResponseBody
    public Map<String, Object> incomeStics(@RequestParam(value = "mouth") String mouth, @RequestParam(value = "year") String year, HttpServletRequest request) {
        if( mouth.trim() == ""  && year.trim() == "" ){
            mouth=DateUtils.getDateStr(new Date());//设置默认值  数据统计时的默认值 为当前月份
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> searchMap = new HashMap<String, Object>();
        User user = (User) request.getSession().getAttribute("admin");
        searchMap.put("incomeUser", user.getUsername());//查询指定用户的账单
        PublicChartsInfo publicChartsInfo = null;
        if (mouth != null && mouth.trim() != "") {
            List<Integer> list = DateUtils.getNumberInStr(mouth);
            int days = DateUtils.getDays(list.get(0), list.get(1));
            publicChartsInfo = new PublicChartsInfo(days);
            //数据库查询
            searchMap.put("year", list.get(0));
            searchMap.put("mouth", list.get(1));
            List<VoIncStics> lists = incomeSer.findIncStatistics(searchMap);
            publicChartsInfo.setChartData(PersonUtils.getIncChartData(lists,days,2));
            publicChartsInfo.setSeriesName("收入");
            resultMap.put("lineChart", publicChartsInfo);
        }
        if (year != null && year.trim() != "") {
            List<Integer> list = DateUtils.getNumberInStr(year);
            //数据库查询
            searchMap.put("year", list.get(0));
            publicChartsInfo = new PublicChartsInfo(String.valueOf(list.get(0)));
            List<VoIncStics> lists = incomeSer.findIncStatistics(searchMap);
            publicChartsInfo.setChartData(PersonUtils.getIncChartData(lists,12,1));
            publicChartsInfo.setSeriesName("收入");
            resultMap.put("lineChart", publicChartsInfo);
        }
        return resultMap;
    }
}
