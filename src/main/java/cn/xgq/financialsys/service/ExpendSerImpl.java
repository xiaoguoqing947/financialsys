package cn.xgq.financialsys.service;

import cn.xgq.financialsys.domain.Expend;
import cn.xgq.financialsys.domain.User;
import cn.xgq.financialsys.domain.dto.expend.UpdateExpendForm;
import cn.xgq.financialsys.domain.vo.VoExpPieChart;
import cn.xgq.financialsys.domain.vo.VoExpPrice;
import cn.xgq.financialsys.domain.vo.VoExpStics;
import cn.xgq.financialsys.mapping.ExpendMapper;
import cn.xgq.financialsys.service.inter.ExpendSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExpendSerImpl implements ExpendSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpendSerImpl.class);
    @Resource
    private ExpendMapper expendMapper;

    @Override
    public int findCount(Map<String, Object> searchMap) {
        return expendMapper.findExpendCount(searchMap);
    }

    @Override
    public List<Map<String, Object>> listExpend(Map<String, Object> searchMap) {
        return expendMapper.findExpendMap(searchMap);
    }

    @Override
    public boolean addExpend(Expend expend) {
        int num = 0;
        try {
            num = expendMapper.insertSelective(expend);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public Expend findExpend(String expendId) {
        return expendMapper.selectByPrimaryKey(Integer.parseInt(expendId));
    }

    @Override
    public boolean updateExpend(UpdateExpendForm form, HttpServletRequest request) {
        int num = 0;
        try {
            Expend expend=new Expend();
            expend.setExpendId(Integer.parseInt(form.getUdExpId()));
            expend.setExpendPrice(Double.parseDouble(form.getUdExpPrice()));
            User user= (User) request.getSession().getAttribute("admin");
            expend.setExpendUser(user.getUsername());
            expend.setExpendTypeId(Integer.parseInt(form.getUdExpTypeId()));
            expend.setExpendDate(new Date());
            expend.setExpendDesc(form.getUdExpDesc());
            expend.setExpendUse(form.getUdExpUse());
            expend.setPayAccount(form.getUdPayAccount());
            expend.setPayMethod(form.getUdPayMethod());
            num = expendMapper.updateByPrimaryKeySelective(expend);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public boolean deleteExpend(String id) {
        int num = 0;
        try {
            num = expendMapper.deleteByPrimaryKey(Integer.parseInt(id));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;
    }

    @Override
    public List<VoExpStics> findExpStatistics(Map<String, Object> searchMap) {
        return expendMapper.findExpStatistics(searchMap);
    }

    @Override
    public List<VoExpPieChart> findVoExpPieChartList(Map<String, Object> searchMap) {
        return expendMapper.findVoExpPieChartList(searchMap);
    }

    @Override
    public VoExpPrice findExpPrice(Map<String, Object> searchMap) {
        return expendMapper.findExpPrice(searchMap);
    }
}
