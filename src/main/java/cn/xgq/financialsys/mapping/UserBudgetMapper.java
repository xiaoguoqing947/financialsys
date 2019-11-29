package cn.xgq.financialsys.mapping;

import cn.xgq.financialsys.domain.UserBudget;
import org.apache.ibatis.annotations.Param;

public interface UserBudgetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBudget record);

    int insertSelective(UserBudget record);

    UserBudget selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBudget record);

    int updateByPrimaryKey(UserBudget record);

    int queryUserBgByUsername(@Param("un") String username);
}