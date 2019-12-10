package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.domain.BookMark;
import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.service.inter.BookMarkSer;
import cn.xgq.financialsys.util.ValidateMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/bookmark")
public class BookMarkCtrl {
    @Autowired
    private BookMarkSer bookMarkSer;

    @ResponseBody
    @PostMapping("/queryList")
    public Map<String, Object> queryListUrl(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (ValidateMethod.isTokenCheck(req)) {
            User user = (User) req.getSession().getAttribute("admin");
            List<BookMark> bookMarks=bookMarkSer.findList(user.getUsername());
            resultMap.put("bMarkList",bookMarks);
        }
        return resultMap;
    }

    @ResponseBody
    @PostMapping("/add")
    public boolean addMenu(HttpServletRequest req, @RequestParam("title") String title) {
        boolean flag=false;
        if (ValidateMethod.isTokenCheck(req)) {
            BookMark bookMark = new BookMark();
            User user = (User) req.getSession().getAttribute("admin");
            bookMark.setUsername(user.getUsername());
            bookMark.setTitle(title);
            bookMark.setOprTime(new Date());
            flag = bookMarkSer.addBookMark(bookMark);
        }
        return flag;
    }
}
