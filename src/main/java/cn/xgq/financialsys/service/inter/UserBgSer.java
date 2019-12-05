package cn.xgq.financialsys.service.inter;

import cn.xgq.financialsys.domain.UserBudget;

import java.util.Map;

public interface UserBgSer {
    boolean IsExistUser(String username);

    boolean addUserBudget(UserBudget userBudget);

    UserBudget findUserBg(String username);
}
