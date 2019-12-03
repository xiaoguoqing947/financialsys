package cn.xgq.financialsys.service;

import cn.xgq.financialsys.domain.Income;
import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.domain.dto.income.UpdateIncomeForm;
import cn.xgq.financialsys.domain.vo.VoExpStics;
import cn.xgq.financialsys.domain.vo.VoIncStics;
import cn.xgq.financialsys.mapping.IncomeMapper;
import cn.xgq.financialsys.service.inter.IncomeSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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

    @Override
    public boolean addIncome(Income income) {
        int num = 0;
        try {
            num = incomeMapper.insertSelective(income);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public Income findIncome(String incomeId) {
        return incomeMapper.selectByPrimaryKey(Integer.parseInt(incomeId));
    }

    @Override
    public boolean updateIncome(UpdateIncomeForm form, HttpServletRequest request) {
        int num = 0;
        try {
            Income income=new Income();
            income.setIncomeId(Integer.parseInt(form.getUdIncId()));
            income.setIncomePrice(Double.parseDouble(form.getUdIncPrice()));
            User user= (User) request.getSession().getAttribute("admin");
            income.setIncomeUser(user.getUsername());
            income.setIncomeTypeId(Integer.parseInt(form.getUdIncTypeId()));
            income.setIncomeDate(new Date());
            income.setIncomeDesc(form.getUdIncDesc());
            income.setIncomeSource(form.getUdIncSource());
            income.setPayAccount(form.getUdPayAccount());
            income.setPayMethod(form.getUdPayMethod());
            num = incomeMapper.updateByPrimaryKeySelective(income);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public boolean deleteIncome(String id) {
        int num = 0;
        try {
            num = incomeMapper.deleteByPrimaryKey(Integer.parseInt(id));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public List<VoIncStics> findIncStatistics(Map<String, Object> searchMap) {
        return incomeMapper.findIncStatistics(searchMap);
    }
}
