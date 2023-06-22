package com.zwen.ipet.order.price;

import org.springframework.stereotype.Component;

import com.zwen.ipet.order.domain.OrderItemDTO;
import com.zwen.ipet.promotion.domain.PromotionActivityDTO;

/**
 * 没有促销活动的计算组件
 * @author zwen
 *
 */
@Component
public class DefaultPromotionActivityCalculator implements PromotionActivityCalculator {
	
	/**
	 * 计算促销活动减免的金额
	 */
	@Override
	public PromotionActivityResult calculate(OrderItemDTO item, 
			PromotionActivityDTO promotionActivity) {
		return new PromotionActivityResult(); 
	}

}
