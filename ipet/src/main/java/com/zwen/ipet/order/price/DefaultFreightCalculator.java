package com.zwen.ipet.order.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zwen.ipet.logistics.service.LogisticsService;
import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.order.domain.OrderItemDTO;

/**
 * 默认的运费计算组件
 * @author zwen
 *
 */
@Component
public class DefaultFreightCalculator implements FreightCalculator {

	/**
	 * 物流中心接口
	 */
	@Autowired
	private LogisticsService logisticsService;
	
	/**
	 * 计算运费
	 */
	@Override
	public Double calculate(OrderInfoDTO order, OrderItemDTO orderItem, 
			PromotionActivityResult result) {
		return logisticsService.calculateFreight(order, orderItem);
	}

}
