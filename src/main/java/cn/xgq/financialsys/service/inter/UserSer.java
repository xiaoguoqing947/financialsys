package cn.xgq.financialsys.service.inter;

import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.domain.dto.user.UserLoginForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserSer {
    /**
     * 登录判断的方法
     * @return
     */
    boolean login(UserLoginForm form);

    void addSession(HttpServletRequest request, User user);//登录成功后添加session

    void destroySession(HttpServletRequest request);//退出登录或者超时之后销毁session

    boolean validateUname(String username);

    boolean addUser(User user);

    int getUserPower(String username);

    boolean updatePwd(User userLoginForm);

    int findCount(Map<String, Object> searchMap);

    List<User> findList(Map<String, Object> searchMap);

    boolean deleteUser(String username);

    User findUser(String username);

    boolean updateUser(User user);
}
