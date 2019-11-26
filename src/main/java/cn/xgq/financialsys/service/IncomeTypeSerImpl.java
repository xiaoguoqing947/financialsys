package cn.xgq.financialsys.service;

import cn.xgq.financialsys.domain.IncomeType;
import cn.xgq.financialsys.mapping.IncomeTypeMapper;
import cn.xgq.financialsys.service.inter.IncomeTypeSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IncomeTypeSerImpl implements IncomeTypeSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(IncomeTypeSerImpl.class);
    @Resource
    private IncomeTypeMapper incomeTypeMapper;

    @Override
    public List<IncomeType> listIncomeType() {
        return incomeTypeMapper.findList();
    }
}
