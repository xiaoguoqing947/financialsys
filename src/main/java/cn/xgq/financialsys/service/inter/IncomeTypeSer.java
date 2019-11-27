package cn.xgq.financialsys.service.inter;

import cn.xgq.financialsys.domain.IncomeType;
import cn.xgq.financialsys.domain.dto.income.UpdateIncTypeForm;

import java.util.List;

public interface IncomeTypeSer {
    List<IncomeType> listIncomeType();

    boolean validIncTypeName(String incomeType);

    boolean addIncType(IncomeType incomeType);

    IncomeType findIncomeType(String incomeTypeId);

    boolean updateIncType(UpdateIncTypeForm form);

    boolean deleteIncType(String id);
}
