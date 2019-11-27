package cn.xgq.financialsys.service.inter;

import cn.xgq.financialsys.domain.ExpendType;
import cn.xgq.financialsys.domain.dto.expend.UpdateExpTypeForm;

import java.util.List;

public interface ExpendTypeSer {
    List<ExpendType> listExpendType();

    boolean validIncTypeName(String enpendType);

    boolean addExpType(ExpendType expendType);

    ExpendType findExpendType(String expendTypeId);

    boolean updateExpType(UpdateExpTypeForm form);

    boolean deleteExpType(String id);
}
