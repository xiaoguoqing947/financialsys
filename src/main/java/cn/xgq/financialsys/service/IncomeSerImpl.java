package cn.xgq.financialsys.service;

import cn.xgq.financialsys.domain.Income;
import cn.xgq.financialsys.mapping.IncomeMapper;
import cn.xgq.financialsys.service.inter.IncomeSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class IncomeSerImpl implements IncomeSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(IncomeSerImpl.class);

    @Resource
    private IncomeMapper incomeMapper;

    @Override
    public int findCount(Map<String, Object> searchMap) {
        return incomeMapper.findIncomeCount(searchMap);
    }

    @Override
    public List<Map<String,Object>> listIncome(Map<String, Object> searchMap) {
        return incomeMapper.findIncomeMap(searchMap);
    }
}
