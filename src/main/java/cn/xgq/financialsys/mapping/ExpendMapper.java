package cn.xgq.financialsys.mapping;

import cn.xgq.financialsys.domain.Expend;

public interface ExpendMapper {
    int deleteByPrimaryKey(Integer expendId);

    int insert(Expend record);

    int insertSelective(Expend record);

    Expend selectByPrimaryKey(Integer expendId);

    int updateByPrimaryKeySelective(Expend record);

    int updateByPrimaryKey(Expend record);
}