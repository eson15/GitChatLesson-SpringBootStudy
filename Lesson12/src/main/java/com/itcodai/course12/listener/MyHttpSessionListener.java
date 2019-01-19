package com.itcodai.course12.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

/**
 * 使用HttpSessionListener统计在线用户数的监听器
 * @author shengwu ni
 * @date 2018/07/05
 */
@Component
public class MyHttpSessionListener implements HttpSessionListener {

    private static final Logger logger = LoggerFactory.getLogger(MyHttpSessionListener.class);
    private Long beginTime = 0L;

    /**
     * 记录在线的用户数量
     */
    public Integer count = 0;

    @Override
    public synchronized void sessionCreated(HttpSessionEvent httpSessionEvent) {
        logger.info("新用户上线了");
        count++;
        httpSessionEvent.getSession().getServletContext().setAttribute("count", count);
        httpSessionEvent.getSession().setMaxInactiveInterval(180);
        logger.info("==beginTime:{}", System.currentTimeMillis());
        beginTime = System.currentTimeMillis();
    }

    @Override
    public synchronized void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.info("用户下线了");
        count--;
        httpSessionEvent.getSession().getServletContext().setAttribute("count", count);
        Long endTime = System.currentTimeMillis();
        logger.info("==endTime:{}", System.currentTimeMillis());
        logger.info("时间差为：{}", endTime - beginTime);
    }
}
