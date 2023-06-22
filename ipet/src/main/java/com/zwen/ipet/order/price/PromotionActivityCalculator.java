package com.zwen.ipet.order.price;

import com.zwen.ipet.order.domain.OrderItemDTO;
import com.zwen.ipet.promotion.domain.PromotionActivityDTO;

/**
 * 促销活动处理组件接口
 * @author zwen
 *
 */
public interface PromotionActivityCalculator {  

	/**
	 * 处理促销活动
	 * @param item 订单条目
	 * @param promotionActivity 促销活动
	 * @return 促销活动计算结果
	 * @throws Exception
	 */
	PromotionActivityResult calculate(OrderItemDTO item, 
			PromotionActivityDTO promotionActivity) throws Exception;
	
}
