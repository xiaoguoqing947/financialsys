package cn.xgq.financialsys.service;

import cn.xgq.financialsys.domain.Menu;
import cn.xgq.financialsys.domain.dto.menu.UpdateMenuForm;
import cn.xgq.financialsys.domain.vo.VoMenu;
import cn.xgq.financialsys.mapping.MenuMapper;
import cn.xgq.financialsys.redis.RedisService;
import cn.xgq.financialsys.service.inter.MenuSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class MenuSerImpl implements MenuSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuSerImpl.class);

    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RedisService redisService;
    @Resource
    private ValueOperations<String, Object> valueOperations;

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
    @Transactional/*事务的回滚*/
    public boolean addMenu(Menu menu) {
        redisService.deleteKey("listMenu");
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
        redisService.deleteKey("listMenu");
        int num = 0;
        try {
            Menu menu = new Menu();
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
        redisService.deleteKey("listMenu");
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

    @Override
    public List<Menu> findUserPagelistMenu() {
        /*redis缓存的使用*/
        List<Menu> list = null;
        if (redisService.existsKey("listMenu") && valueOperations.get("listMenu") != null) {
            list = (List<Menu>) valueOperations.get("listMenu");
        } else {
            list = menuMapper.findUserPagelistMenu();
            valueOperations.set("listMenu", list, RedisService.DEFAULT_EXPIRE, TimeUnit.SECONDS);
        }
//        System.out.println(list);
        return list;
    }


}
