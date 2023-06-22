package com.zwen.ipet.wms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zwen.ipet.wms.constant.PurchaseInputOrderStatus;
import com.zwen.ipet.wms.dao.PurchaseInputOrderDAO;
import com.zwen.ipet.wms.domain.PurchaseInputOrderDTO;

/**
 * 更新采购入库单状态的handler
 * @author zwen
 *
 */
@Component
public class UpdatePurchaseInputOrderStatusHandler extends AbstractPurchaseInputOrderHandler {
	
	/**
	 * 采购入库单管理DAO组件
	 */
	@Autowired
	private PurchaseInputOrderDAO purchaseInputOrderDAO;
	
	/**
	 * 执行处理逻辑
	 * @param purchaseInputOrder 采购入库单
	 * @return 处理结果
	 * @throws Exception
	 */
	@Override
	public PurchaseInputOrderHandlerResult doExecute(
			PurchaseInputOrderDTO purchaseInputOrder) throws Exception {
		purchaseInputOrderDAO.updateStatus(purchaseInputOrder.getId(),
				PurchaseInputOrderStatus.FINISH_INPUT);
		return new PurchaseInputOrderHandlerResult(true);  
	}
	
}
