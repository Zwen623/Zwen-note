package com.zwen.ipet.wms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zwen.ipet.wms.constant.WmsStockUpdateEvent;
import com.zwen.ipet.wms.domain.PurchaseInputOrderDTO;
import com.zwen.ipet.wms.stock.WmsStockUpdater;
import com.zwen.ipet.wms.stock.WmsStockUpdaterFactory;

/**
 * 更新本地库存的handler
 * @author zwen
 *
 */
@Component
public class UpdateStockHandler extends AbstractPurchaseInputOrderHandler {

	/**
	 * 库存更新组件工厂
	 */
	@Autowired
	private WmsStockUpdaterFactory stockUpdaterFactory;
	
	/**
	 * 执行处理逻辑
	 */
	@Override
	public PurchaseInputOrderHandlerResult doExecute(PurchaseInputOrderDTO purchaseInputOrder) throws Exception {
		WmsStockUpdater stockUpdater = stockUpdaterFactory.create(
				WmsStockUpdateEvent.PURCHASE_INPUT, purchaseInputOrder);
		stockUpdater.update();
		return new PurchaseInputOrderHandlerResult(true); 
	}

}
