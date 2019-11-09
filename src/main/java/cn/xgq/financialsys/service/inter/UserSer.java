package cn.xgq.financialsys.service.inter;

import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.domain.dto.UserLoginForm;

import javax.servlet.http.HttpServletRequest;

public interface UserSer {
    /**
     * 登录判断的方法
     * @return
     */
    boolean login(UserLoginForm form);

    void addSession(HttpServletRequest request, UserLoginForm form);//登录成功后添加session

    void destroySession(HttpServletRequest request);//退出登录或者超时之后销毁session

    boolean validateUname(String username);

    boolean addUser(User user);

    int getUserPower(String username);
}
