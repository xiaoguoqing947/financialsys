package cn.xgq.financialsys.service;

import cn.xgq.financialsys.domain.Dictionary;
import cn.xgq.financialsys.mapping.DictionaryMapper;
import cn.xgq.financialsys.service.inter.DicSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DicSerImpl implements DicSer {

    @Resource
    private DictionaryMapper dictionaryMapper;

    @Override
    public List<Dictionary> queryAll(Map<String,Object> searchMap) {
        return dictionaryMapper.queryAll(searchMap);
    }
}
