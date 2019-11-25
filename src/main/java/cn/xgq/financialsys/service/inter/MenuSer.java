package cn.xgq.financialsys.service.inter;

import cn.xgq.financialsys.domain.Menu;
import cn.xgq.financialsys.domain.dto.UpdateMenuForm;
import cn.xgq.financialsys.domain.vo.VoMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MenuSer {
    /**
     * 获取菜单列表
     * @param searchMap
     * @return
     */
    List<Menu> listMenu(Map<String, Object> searchMap);

    /**
     * 获取菜单数量
     */
    int findCount (Map<String,Object> searchMap);

    /**
     * 验证菜单名
     */
    boolean validMenuName(String mname);

    /**
     * 添加菜单
     * @param menu
     * @return
     */
    boolean addMenu(Menu menu);

    /**
     * 查找菜单
     * @param menuId
     * @return
     */
    Menu findMenu(String menuId);

    /**
     * 更新菜单
     * @param form
     * @return
     */
    boolean updateMenu(UpdateMenuForm form);

    /**
     * 删除菜单
     * @param id
     * @return
     */
    boolean deleteMenu(String id);

    /**
     * 查找所有菜单的id和name
     * @return
     */
    List<VoMenu> findAllMenuIdAndName();
}
