package cn.xgq.financialsys.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyExceptionHandler {
    @ResponseBody
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Map handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        Map<String ,Object> map=new HashMap<String, Object>();
        map.put("code",500);
        map.put("msg","文件大小超出2MB限制, 请压缩或降低文件质量! ");
        return map;
    }
}
