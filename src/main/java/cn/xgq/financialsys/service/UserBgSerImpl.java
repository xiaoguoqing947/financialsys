package cn.xgq.financialsys.service;

import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.domain.UserBudget;
import cn.xgq.financialsys.mapping.UserBudgetMapper;
import cn.xgq.financialsys.service.inter.UserBgSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserBgSerImpl implements UserBgSer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBgSerImpl.class);
    @Resource
    private UserBudgetMapper userBudgetMapper;

    @Override
    public boolean IsExistUser(String username) {
        int num = 0;
        try {
            num = userBudgetMapper.queryUserBgByUsername(username);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public boolean addUserBudget(UserBudget userBudget) {
        int num = 0;
        try {
            num = userBudgetMapper.insertSelective(userBudget);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public UserBudget findUserBg(String username) {
        return userBudgetMapper.findUserBg(username);
    }

    @Override
    public boolean updateUserBg(UserBudget form, HttpServletRequest request) {
        int num = 0;
        try {
            User user = (User) request.getSession().getAttribute("admin");
            int id=userBudgetMapper.selectIdByUsername(user.getUsername());
            form.setId(id);
            num = userBudgetMapper.updateByPrimaryKeySelective(form);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }
}
