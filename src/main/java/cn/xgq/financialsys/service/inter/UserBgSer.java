package cn.xgq.financialsys.service.inter;

import cn.xgq.financialsys.domain.UserBudget;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserBgSer {
    boolean IsExistUser(String username);

    boolean addUserBudget(UserBudget userBudget);

    UserBudget findUserBg(String username);

    boolean updateUserBg(UserBudget form, HttpServletRequest request);
}
