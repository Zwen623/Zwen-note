package com.zwen.ipet.wms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zwen.ipet.purchase.service.PurchaseService;
import com.zwen.ipet.wms.domain.PurchaseInputOrderDTO;

/**
 * 通知采购中心的handler
 * @author zwen
 *
 */
@Component
public class InformPurchaseCenterHandler extends AbstractPurchaseInputOrderHandler {
	
	/**
	 * 采购中心接口
	 */
	@Autowired
	private PurchaseService purchaseService;
	
	/**
	 * 执行处理逻辑
	 */
	@Override
	public PurchaseInputOrderHandlerResult doExecute(
			PurchaseInputOrderDTO purchaseInputOrder) throws Exception {
		purchaseService.informFinishedPurchaseInputOrderEvent(
				purchaseInputOrder.getPurchaseOrderId());
		return new PurchaseInputOrderHandlerResult(true); 
	}

}
