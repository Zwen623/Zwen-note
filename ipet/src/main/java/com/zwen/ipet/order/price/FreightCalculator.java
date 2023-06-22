package com.zwen.ipet.order.price;

import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.order.domain.OrderItemDTO;

/**
 * 运费计算组件接口
 * @author zwen
 *
 */
public interface FreightCalculator {
	
	/**
	 * 计算运费
	 * @param order 订单
	 * @param orderItem 订单条目
	 * @param result 促销活动计算结果
	 * @return 运费
	 */
	Double calculate(OrderInfoDTO order, OrderItemDTO orderItem, 
			PromotionActivityResult result); 

}
