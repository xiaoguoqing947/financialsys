package cn.xgq.financialsys.service;

import cn.xgq.financialsys.domain.Log;
import cn.xgq.financialsys.mapping.LogMapper;
import cn.xgq.financialsys.service.inter.LogSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LogSerImpl implements LogSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogSerImpl.class);

    @Resource
    private LogMapper logMapper;
    @Override
    public boolean addLog(Log log) {
        int num = 0;
        try {
            num = logMapper.insertSelective(log);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return num > 0;

    }
}
