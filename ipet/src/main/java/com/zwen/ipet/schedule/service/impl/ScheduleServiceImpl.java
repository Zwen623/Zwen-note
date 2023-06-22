package com.zwen.ipet.schedule.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zwen.ipet.inventory.service.InventoryService;
import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.common.util.ObjectUtils;
import com.zwen.ipet.customer.domain.ReturnGoodsWorksheetDTO;
import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.order.domain.OrderItemDTO;
import com.zwen.ipet.order.service.OrderService;
import com.zwen.ipet.purchase.domain.PurchaseOrderDTO;
import com.zwen.ipet.purchase.domain.PurchaseOrderItemDTO;
import com.zwen.ipet.schedule.constant.StockUpdateEvent;
import com.zwen.ipet.schedule.dao.ScheduleOrderPickingItemDAO;
import com.zwen.ipet.schedule.dao.ScheduleOrderSendOutDetailDAO;
import com.zwen.ipet.schedule.domain.SaleDeliveryScheduleResult;
import com.zwen.ipet.schedule.domain.ScheduleOrderPickingItemDO;
import com.zwen.ipet.schedule.domain.ScheduleOrderSendOutDetailDO;
import com.zwen.ipet.schedule.service.SaleDeliveryOrderBuilder;
import com.zwen.ipet.schedule.service.SaleDeliveryScheduler;
import com.zwen.ipet.schedule.service.ScheduleService;
import com.zwen.ipet.schedule.stock.ScheduleStockUpdater;
import com.zwen.ipet.schedule.stock.ScheduleStockUpdaterFactory;
import com.zwen.ipet.wms.constant.PurchaseInputOrderStatus;
import com.zwen.ipet.wms.domain.PurchaseInputOrderDTO;
import com.zwen.ipet.wms.domain.PurchaseInputOrderItemDTO;
import com.zwen.ipet.wms.domain.ReturnGoodsInputOrderDTO;
import com.zwen.ipet.wms.domain.ReturnGoodsInputOrderItemDTO;
import com.zwen.ipet.wms.domain.SaleDeliveryOrderDTO;
import com.zwen.ipet.wms.service.WmsService;

/**
 * 调度中心对外接口service组件
 * @author zwen
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ScheduleServiceImpl implements ScheduleService {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);
	
	/**
	 * wms中心对外接口service组件
	 */
	@Autowired
	private WmsService wmsService;
	/**
	 * 销售出库单构建器工厂
	 */
	@Autowired
	private SaleDeliveryOrderBuilderFactory saleDeliveryOrderBuilderFactory;
	/**
	 * 库存中心
	 */
	@Autowired
	private InventoryService inventoryService;
	/**
	 * 销售出库调度器
	 */
	@Autowired
	private SaleDeliveryScheduler saleDeliveryScheduler;
	/**
	 * 拣货条目管理DAO组件
	 */
	@Autowired
	private ScheduleOrderPickingItemDAO pickingItemDAO;
	/**
	 * 发货明细管理DAO组件
	 */
	@Autowired
	private ScheduleOrderSendOutDetailDAO sendOutDetailDAO;
	/**
	 * 库存更新组件工厂
	 */
	@Autowired
	private ScheduleStockUpdaterFactory stockUpdaterFactory;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	/**
	 * 订单中心接口
	 */
	@Autowired
	private OrderService orderService;
	
	/**
	 * 通知库存中心，“采购入库完成”事件发生了
	 * @param purchaseInputOrderDTO 采购入库单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informPurchaseInputFinished(
			PurchaseInputOrderDTO purchaseInputOrder) {
		try {
			ScheduleStockUpdater stockUpdater = stockUpdaterFactory.create(
					StockUpdateEvent.PURCHASE_INPUT, purchaseInputOrder);
			stockUpdater.update();
			
			inventoryService.informPurchaseInputFinished(purchaseInputOrder);
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 通知库存中心，“完成退货入库”事件发生了
	 * @param returnGoodsInputOrderDTO 退货入库单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informReturnGoodsInputFinished(
			ReturnGoodsInputOrderDTO returnGoodsInputOrder) {
		try {
			ScheduleStockUpdater stockUpdater = stockUpdaterFactory.create(
					StockUpdateEvent.RETURN_GOODS_INPUT, returnGoodsInputOrder);
			stockUpdater.update();
			
			inventoryService.informReturnGoodsInputFinished(returnGoodsInputOrder);
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 通知库存中心，“提交订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informSubmitOrderEvent(OrderInfoDTO order) {
		try {
			for(OrderItemDTO orderItem : order.getOrderItems()) {
				SaleDeliveryScheduleResult scheduleResult = 
						saleDeliveryScheduler.schedule(orderItem);
				
				List<ScheduleOrderPickingItemDO> pickingItems = ObjectUtils.convertList(
						scheduleResult.getPickingItems(), ScheduleOrderPickingItemDO.class);
				List<ScheduleOrderSendOutDetailDO> sendOutDetails = ObjectUtils.convertList(
						scheduleResult.getSendOutDetails(), ScheduleOrderSendOutDetailDO.class);
				
				pickingItemDAO.batchSave(orderItem.getOrderInfoId(), orderItem.getId(), pickingItems); 
				sendOutDetailDAO.batchSave(orderItem.getOrderInfoId(), orderItem.getId(), sendOutDetails);
				
				ScheduleStockUpdater stockUpdater = stockUpdaterFactory.create(
						StockUpdateEvent.SUBMIT_ORDER, scheduleResult);
				stockUpdater.update();
				
				wmsService.informSubmitOrderEvent(scheduleResult);
			}

			return true;
		} catch (Exception e) {
			logger.error("error", e);
			return false;
		}
	}
	
	/**
	 * 通知库存中心，“取消订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informCancelOrderEvent(OrderInfoDTO order) {
		try {
			for(OrderItemDTO orderItem : order.getOrderItems()) {
				SaleDeliveryScheduleResult scheduleResult = saleDeliveryScheduler
						.getScheduleResult(orderItem);
				
				ScheduleStockUpdater stockUpdater = stockUpdaterFactory.create(
						StockUpdateEvent.CANCEL_ORDER, scheduleResult);
				stockUpdater.update();
				
				pickingItemDAO.removeByOrderItemId(orderItem.getOrderInfoId(), orderItem.getId()); 
				sendOutDetailDAO.removeByOrderItemId(orderItem.getOrderInfoId(), orderItem.getId()); 
				
				wmsService.informCancelOrderEvent(scheduleResult);
			}
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 通知库存中心，“支付订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informPayOrderEvent(OrderInfoDTO order) {
		try {
			for(OrderItemDTO orderItem : order.getOrderItems()) {
				SaleDeliveryScheduleResult scheduleResult = saleDeliveryScheduler
						.getScheduleResult(orderItem);
				
				ScheduleStockUpdater stockUpdater = stockUpdaterFactory.create(
						StockUpdateEvent.PAY_ORDER, scheduleResult);
				stockUpdater.update();
				
				wmsService.informPayOrderEvent(scheduleResult);
			}
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 调度采购入库
	 * @param purchaseOrderDTO 采购单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean schedulePurchaseInput(PurchaseOrderDTO purchaseOrder) {
		try {
			PurchaseInputOrderDTO purchaseInputOrder = 
					createPurchaseInputOrder(purchaseOrder);
			
			List<PurchaseInputOrderItemDTO> purchaseInputOrderItems = 
					new ArrayList<PurchaseInputOrderItemDTO>();
			for(PurchaseOrderItemDTO purchaseOrderItem : purchaseOrder.getItems()) {
				purchaseInputOrderItems.add(createPurchaseInputOrderItem(purchaseOrderItem)); 
			}
			purchaseInputOrder.setItems(purchaseInputOrderItems);  
			
			wmsService.createPurchaseInputOrder(purchaseInputOrder); 
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
		return true;
	}
	
	/**
	 * 创建一个采购入库单
	 * @param purchaseOrder 采购单
	 * @return 采购入库单
	 * @throws Exception
	 */
	private PurchaseInputOrderDTO createPurchaseInputOrder(
			PurchaseOrderDTO purchaseOrder) throws Exception {
		PurchaseInputOrderDTO purchaseInputOrder = 
				purchaseOrder.clone(PurchaseInputOrderDTO.class);
		
		purchaseInputOrder.setId(null); 
		purchaseInputOrder.setPurchaseOrderId(purchaseOrder.getId()); 
		purchaseInputOrder.setGmtCreate(dateProvider.getCurrentTime());
		purchaseInputOrder.setGmtModified(dateProvider.getCurrentTime()); 
		purchaseInputOrder.setStatus(PurchaseInputOrderStatus.EDITING); 
		purchaseInputOrder.setPurchaseContactor(purchaseOrder.getContactor());
		purchaseInputOrder.setPurchaseContactorPhoneNumber(
				purchaseOrder.getContactorPhoneNumber()); 
		purchaseInputOrder.setPurchaseContactorEmail(purchaseOrder.getContactorEmail()); 
		purchaseInputOrder.setPurchaseOrderRemark(purchaseOrder.getRemark()); 
		
		return purchaseInputOrder;
	}
	
	/**
	 * 创建采购入库单条目
	 * @param purchaseOrderItem
	 * @return
	 * @throws Exception
	 */
	private PurchaseInputOrderItemDTO createPurchaseInputOrderItem(
			PurchaseOrderItemDTO purchaseOrderItem) throws Exception {
		PurchaseInputOrderItemDTO purchaseInputOrderItem = 
				purchaseOrderItem.clone(PurchaseInputOrderItemDTO.class);
		purchaseInputOrderItem.setId(null); 
		purchaseInputOrderItem.setGmtCreate(dateProvider.getCurrentTime()); 
		purchaseInputOrderItem.setGmtModified(dateProvider.getCurrentTime());  
		return purchaseInputOrderItem;
	}

	/**
	 * 调度销售出库
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean scheduleSaleDelivery(OrderInfoDTO order) {
		try {
			SaleDeliveryOrderBuilder saleDeliveryOrderBuilder = 
					saleDeliveryOrderBuilderFactory.get();
			
			SaleDeliveryOrderDTO saleDeliveryOrder = saleDeliveryOrderBuilder
					.setOrderRelatedData(order)
					.createSaleDeliveryOrderItems(order.getOrderItems())
					.createSendOutOrder(order)
					.createLogisticOrder(order)
					.initStatus()
					.initTimes()
					.create();
			
			wmsService.createSaleDeliveryOrder(saleDeliveryOrder);
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
	/**
	 * 调度退货入库
	 * @param orderDTO 订单DTO
	 * @param returnGoodsWorksheetDTO 退货工单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean scheduleReturnGoodsInput(OrderInfoDTO order, 
			ReturnGoodsWorksheetDTO returnGoodsWorksheet) {
		try {
			ReturnGoodsInputOrderDTO returnGoodsInputOrder = order.clone(ReturnGoodsInputOrderDTO.class);
			returnGoodsInputOrder.setId(null); 
			returnGoodsInputOrder.setReturnGoodsWorksheetId(returnGoodsWorksheet.getId()); 
			returnGoodsInputOrder.setOrderId(order.getId());
			returnGoodsInputOrder.setReturnGoodsReason(returnGoodsWorksheet.getReturnGoodsReason()); 
			returnGoodsInputOrder.setReturnGoodsRemark(returnGoodsWorksheet.getReturnGoodsRemark()); 
			returnGoodsInputOrder.setGmtCreate(dateProvider.getCurrentTime()); 
			returnGoodsInputOrder.setGmtModified(dateProvider.getCurrentTime()); 
			
			List<ReturnGoodsInputOrderItemDTO> returnGoodsInputOrderItems = ObjectUtils.convertList(
					order.getOrderItems(), ReturnGoodsInputOrderItemDTO.class);
			for(ReturnGoodsInputOrderItemDTO returnGoodsInputOrderItem : returnGoodsInputOrderItems) {
				returnGoodsInputOrderItem.setId(null);  
				returnGoodsInputOrderItem.setGmtCreate(dateProvider.getCurrentTime()); 
				returnGoodsInputOrderItem.setGmtModified(dateProvider.getCurrentTime()); 
			}
			returnGoodsInputOrder.setItems(returnGoodsInputOrderItems); 
			
			wmsService.createReturnGoodsInputOrder(returnGoodsInputOrder);
			
			return true;
		} catch (Exception e) {
			logger.error("error", e);  
			return false;
		}
	}
	
	/**
	 * 获取调度结果
	 * @param orderInfoId 订单id
	 * @param goodsSkuId 商品sku id
	 * @return 调度结果
	 */
	@Override
	public SaleDeliveryScheduleResult getScheduleResult(Long orderInfoId, Long goodsSkuId) {
		try {
			OrderInfoDTO order = orderService.getOrderById(orderInfoId);
			OrderItemDTO targetOrderItem = null;
			
			for(OrderItemDTO orderItem : order.getOrderItems()) {
				if(orderItem.getGoodsSkuId().equals(goodsSkuId)) {
					targetOrderItem = orderItem;
					break;
				}
			}
			
			return saleDeliveryScheduler.getScheduleResult(targetOrderItem); 
		} catch (Exception e) {
			logger.error("error", e); 
			return null;
		}
	}
	
}
