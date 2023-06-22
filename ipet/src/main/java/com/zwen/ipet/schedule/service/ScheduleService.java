package com.zwen.ipet.schedule.service;

import com.zwen.ipet.customer.domain.ReturnGoodsWorksheetDTO;
import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.purchase.domain.PurchaseOrderDTO;
import com.zwen.ipet.schedule.domain.SaleDeliveryScheduleResult;
import com.zwen.ipet.wms.domain.PurchaseInputOrderDTO;
import com.zwen.ipet.wms.domain.ReturnGoodsInputOrderDTO;

/**
 * 调度中心对外提供的接口
 * @author zwen
 *
 */
public interface ScheduleService {

	/**
	 * 通知库存中心，“采购入库完成”事件发生了
	 * @param purchaseInputOrderDTO 采购入库单DTO
	 * @return 处理结果
	 */
	Boolean informPurchaseInputFinished(
			PurchaseInputOrderDTO purchaseInputOrderDTO);
	
	/**
	 * 通知库存中心，“提交订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	Boolean informSubmitOrderEvent(OrderInfoDTO orderDTO);
	
	/**
	 * 通知库存中心，“支付订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	Boolean informPayOrderEvent(OrderInfoDTO orderDTO);
	
	/**
	 * 通知库存中心，“取消订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	Boolean informCancelOrderEvent(OrderInfoDTO orderDTO);
	
	/**
	 * 通知库存中心，“完成退货入库”事件发生了
	 * @param returnGoodsInputOrderDTO 退货入库单DTO
	 * @return 处理结果
	 */
	Boolean informReturnGoodsInputFinished(
			ReturnGoodsInputOrderDTO returnGoodsInputOrderDTO);
	
	/**
	 * 调度采购入库
	 * @param purchaseOrderDTO 采购单DTO
	 * @return 处理结果
	 */
	Boolean schedulePurchaseInput(PurchaseOrderDTO purchaseOrderDTO);

	/**
	 * 调度销售出库
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	Boolean scheduleSaleDelivery(OrderInfoDTO orderDTO);
	
	/**
	 * 调度退货入库
	 * @param orderDTO 订单DTO
	 * @param returnGoodsWorksheetDTO 退货工单DTO
	 * @return 处理结果
	 */
	Boolean scheduleReturnGoodsInput(OrderInfoDTO orderDTO, 
			ReturnGoodsWorksheetDTO returnGoodsWorksheetDTO);
	
	/**
	 * 获取调度结果
	 * @param orderInfoId 订单id
	 * @param goodsSkuId 商品sku id
	 * @return 调度结果
	 */
	SaleDeliveryScheduleResult getScheduleResult(Long orderInfoId, Long goodsSkuId);
	
}
