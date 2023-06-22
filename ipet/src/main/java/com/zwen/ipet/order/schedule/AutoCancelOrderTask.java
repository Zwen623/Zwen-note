package com.zwen.ipet.order.schedule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zwen.ipet.inventory.service.InventoryService;
import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.common.util.ObjectUtils;
import com.zwen.ipet.order.dao.OrderInfoDAO;
import com.zwen.ipet.order.dao.OrderItemDAO;
import com.zwen.ipet.order.domain.OrderInfoDO;
import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.order.domain.OrderItemDTO;
import com.zwen.ipet.order.state.LoggedOrderStateManager;

/**
 * 自动取消订单任务
 * @author zwen
 *
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class AutoCancelOrderTask {
	
	private static final Logger logger = LoggerFactory.getLogger(AutoCancelOrderTask.class);
	
	/**
	 * 订单管理DAO组件
	 */
	@Autowired
	private OrderInfoDAO orderInfoDAO;
	/**
	 * 订单条目管理DAO组件
	 */
	@Autowired
	private OrderItemDAO orderItemDAO;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	/**
	 * 订单状态管理组件
	 */
	@Autowired
	private LoggedOrderStateManager orderStateManager;
	/**
	 * 库存中心接口
	 */
	@Autowired
	private InventoryService inventoryService;
	
	/**
	 * 执行任务逻辑
	 */
	 @Scheduled(fixedRate = 1 * 60 * 1000)
	 public void execute() {
		 try {
			 for(OrderInfoDO orderInfoDO : orderInfoDAO.listAllUnpayed()) { 
				 OrderInfoDTO order = getOrderInfoDTO(orderInfoDO);
				 
				 if(dateProvider.getCurrentTime().getTime() - 
						 order.getGmtCreate().getTime() <= 24 * 60 * 60 * 1000) {
					 continue;
				 }
				 
				 orderStateManager.cancel(order);
				 inventoryService.informCancelOrderEvent(order);
			 }
		} catch (Exception e) {
			logger.error("error", e); 
		}
	 }
	 
	 /**
	  * 将订单DO转换为订单DTO
	  * @param orderInfoDO 订单DO 
	  * @return 订单DTO
	  * @throws Exception
	  */
	 private OrderInfoDTO getOrderInfoDTO(OrderInfoDO orderInfoDO) throws Exception {
		 OrderInfoDTO orderInfoDTO = orderInfoDO.clone(OrderInfoDTO.class);
		 List<OrderItemDTO> orderItems = ObjectUtils.convertList(
				orderItemDAO.listByOrderInfoId(orderInfoDTO.getId()), OrderItemDTO.class);
		 orderInfoDTO.setOrderItems(orderItems);
		return orderInfoDTO;	
	 }
	
}
