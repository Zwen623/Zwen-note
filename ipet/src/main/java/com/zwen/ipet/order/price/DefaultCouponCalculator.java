package com.zwen.ipet.order.price;

import org.springframework.stereotype.Component;

import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.promotion.domain.CouponDTO;

/**
 * 默认的优惠券计算组件
 * @author zwen
 *
 */
@Component
public class DefaultCouponCalculator implements CouponCalculator {

	@Override
	public Double calculate(OrderInfoDTO order, CouponDTO coupon) {
		return 0.0;
	}

}
