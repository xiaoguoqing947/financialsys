package cn.xgq.financialsys.mapping;

import cn.xgq.financialsys.domain.Expend;
import cn.xgq.financialsys.domain.vo.VoExpPieChart;
import cn.xgq.financialsys.domain.vo.VoExpPrice;
import cn.xgq.financialsys.domain.vo.VoExpStics;

import java.util.List;
import java.util.Map;

public interface ExpendMapper {
    int deleteByPrimaryKey(Integer expendId);

    int insert(Expend record);

    int insertSelective(Expend record);

    Expend selectByPrimaryKey(Integer expendId);

    int updateByPrimaryKeySelective(Expend record);

    int updateByPrimaryKey(Expend record);

    int findExpendCount(Map<String, Object> searchMap);

    List<Map<String, Object>> findExpendMap(Map<String, Object> searchMap);

    List<VoExpStics> findExpStatistics(Map<String, Object> searchMap);

    List<VoExpPieChart> findVoExpPieChartList(Map<String, Object> searchMap);

    VoExpPrice findExpPrice(Map<String, Object> searchMap);
}