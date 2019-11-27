package cn.xgq.financialsys.service;

import cn.xgq.financialsys.mapping.ExpendMapper;
import cn.xgq.financialsys.service.inter.ExpendSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ExpendSerImpl implements ExpendSer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpendSerImpl.class);
    @Resource
    private ExpendMapper expendMapper;

}
