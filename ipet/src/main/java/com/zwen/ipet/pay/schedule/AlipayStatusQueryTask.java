package com.zwen.ipet.pay.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zwen.ipet.common.constant.CollectionSize;
import com.zwen.ipet.order.service.OrderService;
import com.zwen.ipet.pay.api.PayApi;
import com.zwen.ipet.pay.api.QueryPayStatusResponse;
import com.zwen.ipet.pay.constant.PayTransactionStatus;
import com.zwen.ipet.pay.constant.PayType;
import com.zwen.ipet.pay.dao.PayTransactionDAO;
import com.zwen.ipet.pay.domain.PayTransactionDO;

/**
 * 支付宝支付状态查询的后台任务
 * @author zwen
 *
 */
@Component
public class AlipayStatusQueryTask {

	private static final Logger logger = LoggerFactory.getLogger(AlipayStatusQueryTask.class);
	
	/**
	 * 支付交易流水管理DAO组件
	 */
	@Autowired
	private PayTransactionDAO payTransactionDAO;
	/**
	 * 支付接口
	 */
	@Autowired
	private PayApi payApi;
	/**
	 * 订单中心接口
	 */
	@Autowired
	private OrderService orderService;
	
	/**
	 * 执行任务
	 */
	@Scheduled(fixedRate = 10 * 1000)
	public void execute() {
		try {
			List<PayTransactionDO> payTransactions = listUnpayedAlipayTransactions();
			
			for(PayTransactionDO payTransaction : payTransactions) {
				// 此处会调用支付宝代理接口，去查询支付交易的状态
				QueryPayStatusResponse response = payApi.queryPayStatus(
						payTransaction.getTransactionChannel(), payTransaction.getOrderNo());
				
				if(!PayTransactionStatus.UNPAYED.equals(response.getPayTransactionStatus())) {
					payTransaction.setUserPayAccount(response.getUserPayAccount()); 
					payTransaction.setTransactionNumber(response.getTransactionNumber()); 
					payTransaction.setFinishPayTime(response.getFinishPayTime()); 
					payTransaction.setResponseCode(response.getResponseCode()); 
					payTransaction.setStatus(response.getPayTransactionStatus()); 
					payTransactionDAO.update(payTransaction); 
					
					if(PayTransactionStatus.SUCCESS.equals(response.getPayTransactionStatus())) {
						orderService.informPayOrderSuccessed(payTransaction.getOrderInfoId());
					}
				}
			}
		} catch (Exception e) {
			logger.error("error", e); 
		}
	}
	
	/**
	 * 查询未支付的支付宝交易流水
	 * @return 交易流水
	 */ 
	private List<PayTransactionDO> listUnpayedAlipayTransactions() throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>(CollectionSize.DEFAULT);
		parameters.put("transactionChannel", PayType.ALIPAY);
		parameters.put("status", PayTransactionStatus.UNPAYED);
		
		return payTransactionDAO.listByCondition(parameters);
	}
	
}
