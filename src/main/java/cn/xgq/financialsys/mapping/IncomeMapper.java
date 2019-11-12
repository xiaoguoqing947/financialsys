package cn.xgq.financialsys.mapping;

import cn.xgq.financialsys.domain.Income;

public interface IncomeMapper {
    int deleteByPrimaryKey(Integer incomeId);

    int insert(Income record);

    int insertSelective(Income record);

    Income selectByPrimaryKey(Integer incomeId);

    int updateByPrimaryKeySelective(Income record);

    int updateByPrimaryKey(Income record);
}