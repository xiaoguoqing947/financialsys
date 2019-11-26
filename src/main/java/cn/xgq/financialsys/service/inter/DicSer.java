package cn.xgq.financialsys.service.inter;

import cn.xgq.financialsys.domain.Dictionary;

import java.util.List;
import java.util.Map;

public interface DicSer {
    List<Dictionary> queryAll(Map<String,Object> searchMap);
}
