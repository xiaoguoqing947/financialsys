package cn.xgq.financialsys.mapping;

import cn.xgq.financialsys.domain.Income;
import cn.xgq.financialsys.domain.vo.VoIncStics;

import java.util.List;
import java.util.Map;

public interface IncomeMapper {
    int deleteByPrimaryKey(Integer incomeId);

    int insert(Income record);

    int insertSelective(Income record);

    Income selectByPrimaryKey(Integer incomeId);

    int updateByPrimaryKeySelective(Income record);

    int updateByPrimaryKey(Income record);

    int findIncomeCount(Map<String, Object> searchMap);

    List<Map<String,Object>> findIncomeMap(Map<String, Object> searchMap);

    List<VoIncStics> findIncStatistics(Map<String, Object> searchMap);
}