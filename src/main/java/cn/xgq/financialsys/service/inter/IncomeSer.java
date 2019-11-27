package cn.xgq.financialsys.service.inter;

import cn.xgq.financialsys.domain.Income;
import cn.xgq.financialsys.domain.dto.income.UpdateIncomeForm;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IncomeSer {
    int findCount(Map<String, Object> searchMap);

    List<Map<String,Object>> listIncome(Map<String, Object> searchMap);

    boolean addIncome(Income income);

    Income findIncome(String incomeId);

    boolean updateIncome(UpdateIncomeForm form, HttpServletRequest request);

    boolean deleteIncome(String id);
}
