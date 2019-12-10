package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.domain.BookMark;
import cn.xgq.financialsys.service.inter.BookMarkSer;
import cn.xgq.financialsys.util.ValidateMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/bookmark")
public class BookMarkCtrl {
    @Autowired
    private BookMarkSer bookMarkSer;

    @ResponseBody
    @PostMapping("/queryListUrl")
    public Map<String, Object> queryListUrl(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(req)) {
            List<BookMark> bookMarks=bookMarkSer.findList();
            resultMap.put("bMarkList",bookMarks);
        }
        System.out.println(123);
        return resultMap;
    }
}
