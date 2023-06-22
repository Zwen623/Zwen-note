package com.zwen.ipet.wms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zwen.ipet.finance.service.FinanceService;
import com.zwen.ipet.wms.domain.PurchaseInputOrderDTO;

/**
 * 通知财务中心的handler
 * @author zwen
 *
 */
@Component
public class InformFinanceCenterHandler extends AbstractPurchaseInputOrderHandler {

	/**
	 * 财务中心接口
	 */
	@Autowired
	private FinanceService financeService;
	
	/**
	 * 执行处理结果
	 */
	@Override
	public PurchaseInputOrderHandlerResult doExecute(
			PurchaseInputOrderDTO purchaseInputOrder) throws Exception {
		financeService.createPurchaseSettlementOrder(purchaseInputOrder);
		return new PurchaseInputOrderHandlerResult(true); 
	}

}
