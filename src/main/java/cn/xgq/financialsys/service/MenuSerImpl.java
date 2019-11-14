package cn.xgq.financialsys.service;

import cn.xgq.financialsys.mapping.MenuMapper;
import cn.xgq.financialsys.service.inter.MenuSer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MenuSerImpl implements MenuSer {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Map<String, Object>> listMenu(Map<String, Object> searchMap) {
        return menuMapper.findMenuList(searchMap);
    }
}
