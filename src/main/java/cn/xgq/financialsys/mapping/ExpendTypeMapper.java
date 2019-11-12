package cn.xgq.financialsys.mapping;

import cn.xgq.financialsys.domain.ExpendType;

public interface ExpendTypeMapper {
    int deleteByPrimaryKey(Integer expendTypeId);

    int insert(ExpendType record);

    int insertSelective(ExpendType record);

    ExpendType selectByPrimaryKey(Integer expendTypeId);

    int updateByPrimaryKeySelective(ExpendType record);

    int updateByPrimaryKey(ExpendType record);
}