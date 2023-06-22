package com.zwen.ipet.wms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zwen.ipet.schedule.service.ScheduleService;
import com.zwen.ipet.wms.domain.PurchaseInputOrderDTO;

/**
 * 通知调度中心的handler
 * @author zwen
 *
 */
@Component
public class InformScheduleCenterHandler extends AbstractPurchaseInputOrderHandler {

	/**
	 * 调度中心接口
	 */
	@Autowired
	private ScheduleService scheduleService;
	
	/**
	 * 执行处理逻辑
	 */
	@Override
	public PurchaseInputOrderHandlerResult doExecute(
			PurchaseInputOrderDTO purchaseInputOrder) throws Exception {
		scheduleService.informPurchaseInputFinished(purchaseInputOrder);
		return new PurchaseInputOrderHandlerResult(true); 
	}

}
