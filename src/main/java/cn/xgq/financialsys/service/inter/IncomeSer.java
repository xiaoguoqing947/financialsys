package cn.xgq.financialsys.service.inter;

import cn.xgq.financialsys.domain.Income;

import java.util.List;
import java.util.Map;

public interface IncomeSer {
    int findCount(Map<String, Object> searchMap);

    List<Map<String,Object>> listIncome(Map<String, Object> searchMap);
}
