package com.zwen.ipet.order.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zwen.ipet.common.json.JsonExtractor;
import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.promotion.domain.CouponDTO;

/**
 * 现金券抵扣金额计算组件
 * @author zwen
 *
 */
@Component
public class CashCouponCalculator implements CouponCalculator {
	
	@Autowired
	private JsonExtractor jsonExtractor;

	@Override
	public Double calculate(OrderInfoDTO order, CouponDTO coupon) throws Exception {
		JSONObject rule = JSONObject.parseObject(coupon.getRule());
		Double discountAmount = jsonExtractor.getDouble(rule, "discountAmount"); 
		Double payableAmount = order.getPayableAmount();
		
		if(discountAmount > payableAmount) {
			return payableAmount;
		}
		
		return discountAmount;
	}

}
