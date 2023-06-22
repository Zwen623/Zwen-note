package com.zwen.ipet.common.listener;

import com.zwen.ipet.common.bean.SpringApplicationContext;
import com.zwen.ipet.schedule.stock.ScheduleStockUpdateMessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 系统初始化监听器
 * @author zwen
 *
 */
@WebListener
public class SystemInitContextListener implements ServletContextListener {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemInitContextListener.class);
	
	/**
	 * spring容器
	 */
	@Autowired
	public SpringApplicationContext context;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("【系统启动】系统已经完成系统。。。。。。");   
		
		ScheduleStockUpdateMessageConsumer stockUpdateMessageConsumer = 
				context.getBean(ScheduleStockUpdateMessageConsumer.class);
		stockUpdateMessageConsumer.start();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}
  
}