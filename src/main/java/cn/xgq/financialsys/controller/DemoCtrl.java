package cn.xgq.financialsys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo")
public class DemoCtrl {

    @GetMapping("/testPathVariable/{fri}/{sec}")
    public String testPathVariable(@PathVariable String fri,@PathVariable String sec, Model model){
        model.addAttribute("fri",fri);
        model.addAttribute("sec",sec);
        return "module/lockscreen";
    }
}
