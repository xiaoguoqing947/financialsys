package cn.xgq.financialsys.service.inter;

import cn.xgq.financialsys.domain.UserBudget;

public interface UserBgSer {
    boolean IsExistUser(String username);

    boolean addUserBudget(UserBudget userBudget);
}
