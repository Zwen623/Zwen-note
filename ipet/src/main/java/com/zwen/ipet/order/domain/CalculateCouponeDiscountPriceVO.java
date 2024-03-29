package com.zwen.ipet.order.domain;

import com.zwen.ipet.common.util.AbstractObject;
import com.zwen.ipet.promotion.domain.CouponVO;

/**
 * 计算优惠券抵扣金额的VO类
 * @author zwen
 *
 */
public class CalculateCouponeDiscountPriceVO extends AbstractObject {

	/**
	 * 订单
	 */
	private OrderInfoVO order;
	/**
	 * 优惠券
	 */
	private CouponVO coupon;
	
	public OrderInfoVO getOrder() {
		return order;
	}
	public void setOrder(OrderInfoVO order) {
		this.order = order;
	}
	public CouponVO getCoupon() {
		return coupon;
	}
	public void setCoupon(CouponVO coupon) {
		this.coupon = coupon;
	}
	
	@Override
	public String toString() {
		return "CalculateCouponeDiscountPriceVO [order=" + order + ", coupon=" + coupon + "]";
	}
	
}
