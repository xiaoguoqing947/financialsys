package cn.xgq.financialsys.service.inter;

import cn.xgq.financialsys.domain.Expend;
import cn.xgq.financialsys.domain.dto.expend.UpdateExpendForm;
import cn.xgq.financialsys.domain.vo.VoExpPieChart;
import cn.xgq.financialsys.domain.vo.VoExpPrice;
import cn.xgq.financialsys.domain.vo.VoExpStics;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ExpendSer {
    int findCount(Map<String, Object> searchMap);

    List<Map<String, Object>> listExpend(Map<String, Object> searchMap);

    boolean addExpend(Expend expend);

    Expend findExpend(String expendId);

    boolean updateExpend(UpdateExpendForm form, HttpServletRequest request);

    boolean deleteExpend(String id);

    List<VoExpStics> findExpStatistics(Map<String, Object> searchMap);

    List<VoExpPieChart> findVoExpPieChartList(Map<String, Object> searchMap);

    VoExpPrice findExpPrice(Map<String, Object> searchMap);
}
