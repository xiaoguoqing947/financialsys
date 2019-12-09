package cn.xgq.financialsys.controller.admin;

import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.service.inter.UserSer;
import cn.xgq.financialsys.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/api/file")
public class FileCtrl {
    @Autowired
    private UserSer userSer;

    @ResponseBody
    @PostMapping("/upload/uploadimg")
    public Map<String, Object> uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request, @RequestParam Map param) {
        User user = (User) request.getSession().getAttribute("admin");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        System.err.println("传过来的值" + file);
        if (file.isEmpty()) {
            System.err.println("上传文件不可为空");
        }
        String fileName = file.getOriginalFilename();//得到文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));//得到后缀名
        System.err.println("suffixName:" + suffixName);
        String filepath = "E:/JAVACODE/financialsys/src/main/resources/static/img/uploadfile/";//指定图片上传到哪个文件夹的路径
        fileName = UUID.randomUUID() + suffixName;//重新命名图片，变成随机的名字
        System.err.println("fileName:" + fileName);
        File dest = new File(filepath + fileName);//在上传的文件夹处创建文件
        try {
            file.transferTo(dest);//把上传的图片写入磁盘中
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setPic("/img/uploadfile/"+fileName);
        user.setOprDate(new Date());
        user.setName((String) param.get("inputName"));
        user.setAddress((String) param.get("inputAddress"));
        if("女".equals((String) param.get("inputSex"))){
            user.setSex("1");
        }else if("男".equals((String) param.get("inputSex"))){
            user.setSex("0");
        }else{
            user.setSex("#");
        }
        List<Integer> list = DateUtils.getNumberInStr((String) param.get("inputBorn"));
        String date = list.get(0) + "-" + list.get(1) + "-" + list.get(2);
        user.setBorn(DateUtils.strToDate(date));
        user.setContactNumber((String) param.get("inputNumber"));
        if (userSer.updateUser(user)) {
            resultMap.put("code", 200);
            resultMap.put("path", fileName);
        }
        return resultMap;
    }
}
