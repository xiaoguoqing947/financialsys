package cn.xgq.financialsys.mapping;

import cn.xgq.financialsys.domain.IncomeType;

import java.util.List;

public interface IncomeTypeMapper {
    int deleteByPrimaryKey(Integer incomeTypeId);

    int insert(IncomeType record);

    int insertSelective(IncomeType record);

    IncomeType selectByPrimaryKey(Integer incomeTypeId);

    int updateByPrimaryKeySelective(IncomeType record);

    int updateByPrimaryKey(IncomeType record);

    List<IncomeType> findList();
}