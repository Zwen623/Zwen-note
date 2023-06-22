package com.zwen.ipet.pay.service;

import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.wms.domain.ReturnGoodsInputOrderDTO;

/**
 * 支付中心接口
 * @author zwen
 *
 */
public interface PayService {

	/**
	 * 获取支付二维码
	 * @param order 订单
	 * @return 支付二维码
	 */
	String getQrCode(OrderInfoDTO order);
	
	/**
	 * 进行退款
	 * @param returnGoodsInputOrder 退货入库单
	 * @return 退款结果
	 */
	Boolean refund(ReturnGoodsInputOrderDTO returnGoodsInputOrder);
	
}
