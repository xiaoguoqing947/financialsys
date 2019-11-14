package cn.xgq.financialsys.mapping;

import cn.xgq.financialsys.domain.Menu;

import java.util.List;
import java.util.Map;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Map<String, Object>> findMenuList(Map<String, Object> searchMap);
}