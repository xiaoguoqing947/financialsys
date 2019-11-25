package cn.xgq.financialsys.service;

import cn.xgq.financialsys.domain.Menu;
import cn.xgq.financialsys.domain.dto.UpdateMenuForm;
import cn.xgq.financialsys.domain.vo.VoMenu;
import cn.xgq.financialsys.mapping.MenuMapper;
import cn.xgq.financialsys.service.inter.MenuSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MenuSerImpl implements MenuSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuSerImpl.class);

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> listMenu(Map<String, Object> searchMap) {
        return menuMapper.findMenuList(searchMap);
    }

    @Override
    public int findCount(Map<String, Object> searchMap) {
        return menuMapper.findMenuCount(searchMap);
    }

    @Override
    public boolean validMenuName(String mname) {
        int num = 0;
        try {
            num = menuMapper.queryMenuByName(mname);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public boolean addMenu(Menu menu) {
        int num = 0;
        try {
            num = menuMapper.insertSelective(menu);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public Menu findMenu(String menuId) {
        return menuMapper.selectByPrimaryKey(Integer.parseInt(menuId));
    }

    @Override
    public boolean updateMenu(UpdateMenuForm form) {
        int num = 0;
        try {
            Menu menu=new Menu();
            menu.setId(form.getUpdateMenuId());
            menu.setName(form.getUpdateName());
            menu.setUrl(form.getUpdateUrl());
            menu.setUpdateTime(new Date());
            num = menuMapper.updateByPrimaryKeySelective(menu);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public boolean deleteMenu(String id) {
        int num = 0;
        try {
            num = menuMapper.deleteByPrimaryKey(Integer.parseInt(id));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public List<VoMenu> findAllMenuIdAndName() {
        return menuMapper.findMenuIdAndName();
    }


}
