package com.zwen.ipet.logistics.service;

import java.util.Date;

import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.order.domain.OrderItemDTO;
import com.zwen.ipet.wms.domain.LogisticOrderDTO;

/**
 * 物流中心对外提供的接口
 * @author zwen
 *
 */
public interface LogisticsService {

	/**
	 * 计算商品sku的运费
	 * @param order 订单
	 * @param orderItem 订单条目
	 * @return 商品sku的运费
	 */
	Double calculateFreight(OrderInfoDTO order, OrderItemDTO orderItem);
	
	/**
	 * 申请物流单
	 * @param order 订单
	 * @return 物流单
	 */
	LogisticOrderDTO applyLogisticOrder(OrderInfoDTO order);
	
	/**
	 * 获取订单的签收时间
	 * @param orderId 订单id
	 * @param orderNo 订单编号
	 * @return 签收时间
	 */
	Date getSignedTime(Long orderId, String orderNo);
	
}
