package cn.xgq.financialsys.util;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class ValidateMethod {
    public static boolean isTokenCheck(HttpServletRequest request){
        ArrayList tokenSession =(ArrayList) request.getSession().getAttribute("tokenList");
        String tokenRequest = request.getHeader("token");
        if (tokenSession == null) {
            return false;
        } else if (!(tokenSession.contains(tokenRequest))) {
            return false;
        }else{
             return true;
        }
    }
}
