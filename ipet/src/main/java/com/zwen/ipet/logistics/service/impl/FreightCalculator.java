package com.zwen.ipet.logistics.service.impl;

import com.zwen.ipet.logistics.domain.FreightTemplateDTO;
import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.order.domain.OrderItemDTO;

/**
 * 运费计算器接口
 * @author zwen
 *
 */
public interface FreightCalculator {

	/**
	 * 计算订单条目的运费
	 * @param freightTemplate 运费模板
	 * @param order 订单
	 * @param orderItem 订单条目
	 * @return 运费
	 * @throws Exception
	 */
	Double calculate(FreightTemplateDTO freightTemplate, 
			OrderInfoDTO order, OrderItemDTO orderItem) throws Exception;
	
}
