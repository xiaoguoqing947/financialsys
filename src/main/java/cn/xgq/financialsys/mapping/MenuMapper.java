package cn.xgq.financialsys.mapping;

import cn.xgq.financialsys.domain.Menu;
import cn.xgq.financialsys.domain.vo.VoMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> findMenuList(Map<String, Object> searchMap);

    int findMenuCount(Map<String, Object> searchMap);

    int queryMenuByName(@Param("m_name") String name);

    List<VoMenu> findMenuIdAndName();

    List<Menu> findUserPagelistMenu();
}