package cn.xgq.financialsys.service;

import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.domain.dto.user.UserLoginForm;
import cn.xgq.financialsys.mapping.UserMapper;
import cn.xgq.financialsys.service.inter.UserSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public class UserSerImpl implements UserSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserSerImpl.class);
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean login(UserLoginForm form) {
        int num = 0;
        try {
            num = userMapper.queryByLogin(form.getUsername(), form.getPassword());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public void addSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setAttribute("admin", user);
        session.setMaxInactiveInterval(1800);
    }

    @Override
    public void destroySession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.removeAttribute("admin");
        session.removeAttribute("tokenList");
    }

    @Override
    public boolean validateUname(String username) {
        User user = null;
        //对象不存在返回true
        try {
            user = userMapper.queryUserByUsername(username);
//            System.out.println(user.toString());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return user == null;
    }

    @Override
    public boolean addUser(User user) {
        int num = 0;
        try {
            num = userMapper.insertSelective(user);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public int getUserPower(String username) {
        return userMapper.queryUserPower(username);
    }

    @Override
    public boolean updatePwd(UserLoginForm userLoginForm) {
        int num = 0;
        try {
            num = userMapper.updatePwdByUname(userLoginForm.getUsername(),userLoginForm.getPassword());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public int findCount(Map<String, Object> searchMap) {
        return userMapper.findUserCount(searchMap);
    }

    @Override
    public List<User> findList(Map<String, Object> searchMap) {
        return userMapper.findUserList(searchMap);
    }

    @Override
    public boolean deleteUser(String username) {
        int num = 0;
        try {
            num = userMapper.deleteUserByUsername(username);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public User findUser(String username) {
        return userMapper.queryUserByUsername(username);
    }

    @Override
    public boolean updateUser(User user) {
        int num = 0;
        try {
            num = userMapper.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }
}
