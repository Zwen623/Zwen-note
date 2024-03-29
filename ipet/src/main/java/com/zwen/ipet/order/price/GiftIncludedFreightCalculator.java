package com.zwen.ipet.order.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zwen.ipet.logistics.service.LogisticsService;
import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.order.domain.OrderItemDTO;

/**
 * 含赠品的运费计算组件
 * @author zwen
 *
 */
@Component
public class GiftIncludedFreightCalculator implements FreightCalculator {
	
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
		Double freight = 0.0;
		
		freight += logisticsService.calculateFreight(order, orderItem);
		
		for(OrderItemDTO giftItem : result.getOrderItems()) {
			freight += logisticsService.calculateFreight(order, giftItem);
		}
		
		return freight;
	}

}
