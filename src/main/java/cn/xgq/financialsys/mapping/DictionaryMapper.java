package cn.xgq.financialsys.mapping;

import cn.xgq.financialsys.domain.Dictionary;

import java.util.List;
import java.util.Map;

public interface DictionaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    Dictionary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);

    List<Dictionary> queryAll(Map<String,Object> searchMap);
}