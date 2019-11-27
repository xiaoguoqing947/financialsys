package cn.xgq.financialsys.mapping;

import cn.xgq.financialsys.domain.ExpendType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpendTypeMapper {
    int deleteByPrimaryKey(Integer expendTypeId);

    int insert(ExpendType record);

    int insertSelective(ExpendType record);

    ExpendType selectByPrimaryKey(Integer expendTypeId);

    int updateByPrimaryKeySelective(ExpendType record);

    int updateByPrimaryKey(ExpendType record);

    List<ExpendType> findList();

    int queryExpTypeByName(@Param("name") String enpendType);
}