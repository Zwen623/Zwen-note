package com.zwen.ipet.order.price;

import org.springframework.stereotype.Component;

import com.zwen.ipet.order.domain.OrderItemDTO;

/**
 * 总金额默认计算器
 * @author zwen
 *
 */
@Component
public class DefaultTotalPriceCalculator implements TotalPriceCalculator {
	
	/**
	 * 计算商品的总金额
	 */
	@Override
	public Double calculate(OrderItemDTO item) {
		return item.getPurchasePrice() * item.getPurchaseQuantity();  
	}
	
}
