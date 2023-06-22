package com.zwen.ipet.pay.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.pay.api.PayApi;
import com.zwen.ipet.pay.constant.PayTransactionStatus;
import com.zwen.ipet.pay.domain.PayTransactionBuilder;
import com.zwen.ipet.pay.domain.PayTransactionDTO;
import com.zwen.ipet.pay.service.PayService;
import com.zwen.ipet.pay.service.PayTransactionService;
import com.zwen.ipet.wms.domain.ReturnGoodsInputOrderDTO;

/**
 * 支付中心接口
 * @author zwen
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PayServiceImpl implements PayService {
	
	private static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

	/**
	 * 支付接口
	 */
	@Autowired
	private PayApi payApi;
	/**
	 * 支付交易流水管理service组件
	 */
	@Autowired
	private PayTransactionService payTransactionService;
	
	/**
	 * 获取支付二维码
	 * @param order 订单
	 * @return 支付二维码
	 */
	@Override
	public String getQrCode(OrderInfoDTO order) {
		try {
			String qrCode = payApi.getQrCode(order.getPayType(), 
					order.getOrderNo(), order.getPayableAmount()); ;
			
			PayTransactionDTO payTransaction = PayTransactionBuilder.get()
					.buildOrderRelatedData(order) 
					.initStatus()
					.create();
			payTransactionService.save(payTransaction); 
			
			return qrCode;
		} catch (Exception e) {
			logger.error("error", e); 
		}
		return null;
	}
	
	/**
	 * 进行退款
	 * @param returnGoodsInputOrder 退货入库单
	 * @return 退款结果
	 */
	@Override
	public Boolean refund(ReturnGoodsInputOrderDTO returnGoodsInputOrder) {
		try {
			PayTransactionDTO payTransaction = payTransactionService.getByOrderNo(
					returnGoodsInputOrder.getOrderNo());
			
			payTransaction.setStatus(PayTransactionStatus.REFUND); 
			payTransactionService.update(payTransaction); 
			
			Integer transactionChannel = payTransaction.getTransactionChannel();
			String orderNo = returnGoodsInputOrder.getOrderNo();
			Double refundAmount = returnGoodsInputOrder.getPayableAmount();
			
			return payApi.refund(transactionChannel, orderNo, refundAmount);
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
}
