package cn.xgq.financialsys.service.inter;

import java.util.List;
import java.util.Map;

public interface MenuSer {

    List<Map<String, Object>> listMenu(Map<String, Object> searchMap);
}
