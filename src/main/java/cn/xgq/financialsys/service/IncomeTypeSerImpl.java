package cn.xgq.financialsys.service;

import cn.xgq.financialsys.domain.IncomeType;
import cn.xgq.financialsys.domain.dto.income.UpdateIncTypeForm;
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

    @Override
    public boolean validIncTypeName(String incomeType) {
        int num = 0;
        try {
            num = incomeTypeMapper.queryIncTypeByName(incomeType);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public boolean addIncType(IncomeType incomeType) {
        int num = 0;
        try {
            num = incomeTypeMapper.insertSelective(incomeType);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public IncomeType findIncomeType(String incomeTypeId) {
        return incomeTypeMapper.selectByPrimaryKey(Integer.parseInt(incomeTypeId));
    }

    @Override
    public boolean updateIncType(UpdateIncTypeForm form) {
        int num = 0;
        try {
            IncomeType incomeType=new IncomeType();
            incomeType.setIncomeTypeId(Integer.parseInt(form.getUpdateIncTypeId()));
            incomeType.setIncomeType(form.getUpdateIncType());
            num = incomeTypeMapper.updateByPrimaryKeySelective(incomeType);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public boolean deleteIncType(String id) {
        int num = 0;
        try {
            num = incomeTypeMapper.deleteByPrimaryKey(Integer.parseInt(id));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }
}
