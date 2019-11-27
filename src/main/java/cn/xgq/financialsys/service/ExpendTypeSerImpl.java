package cn.xgq.financialsys.service;

import cn.xgq.financialsys.domain.ExpendType;
import cn.xgq.financialsys.domain.dto.expend.UpdateExpTypeForm;
import cn.xgq.financialsys.mapping.ExpendTypeMapper;
import cn.xgq.financialsys.service.inter.ExpendTypeSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExpendTypeSerImpl implements ExpendTypeSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpendTypeSerImpl.class);
    @Resource
    private ExpendTypeMapper expendTypeMapper;

    @Override
    public List<ExpendType> listExpendType() {
        return expendTypeMapper.findList();
    }

    @Override
    public boolean validIncTypeName(String enpendType) {
        int num = 0;
        try {
            num = expendTypeMapper.queryExpTypeByName(enpendType);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public boolean addExpType(ExpendType expendType) {
        int num = 0;
        try {
            num = expendTypeMapper.insertSelective(expendType);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public ExpendType findExpendType(String expendTypeId) {
        return  expendTypeMapper.selectByPrimaryKey(Integer.parseInt(expendTypeId));
    }

    @Override
    public boolean updateExpType(UpdateExpTypeForm form) {
        int num = 0;
        try {
            ExpendType expendType=new ExpendType();
            expendType.setExpendTypeId(Integer.parseInt(form.getUpdateExpTypeId()));
            expendType.setEnpendType(form.getUpdateExpType());
            num = expendTypeMapper.updateByPrimaryKeySelective(expendType);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public boolean deleteExpType(String id) {
        int num = 0;
        try {
            num = expendTypeMapper.deleteByPrimaryKey(Integer.parseInt(id));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }
}
