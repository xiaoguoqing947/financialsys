package cn.xgq.financialsys.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class MyHttpSessionListener implements HttpSessionListener {
    private static final Logger logger = LoggerFactory.getLogger(MyHttpSessionListener.class);

    @Override
    public synchronized void sessionCreated(HttpSessionEvent se) {
        logger.debug(se.getSession().getAttributeNames()+"session创建");
    }

    @Override
    public synchronized void sessionDestroyed(HttpSessionEvent se) {
        logger.debug(se.getSession().getAttributeNames()+"session回收");
    }
}
