package com.zwen.ipet.wms.stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zwen.ipet.order.domain.OrderItemDTO;
import com.zwen.ipet.schedule.domain.SaleDeliveryScheduleResult;
import com.zwen.ipet.schedule.domain.ScheduleOrderPickingItemDTO;
import com.zwen.ipet.schedule.domain.ScheduleOrderSendOutDetailDTO;
import com.zwen.ipet.wms.dao.GoodsAllocationStockDetailDAO;
import com.zwen.ipet.wms.dao.WmsGoodsAllocationStockDAO;
import com.zwen.ipet.wms.dao.WmsGoodsStockDAO;
import com.zwen.ipet.wms.domain.GoodsAllocationStockDetailDO;
import com.zwen.ipet.wms.domain.WmsGoodsAllocationStockDO;
import com.zwen.ipet.wms.domain.WmsGoodsStockDO;

/**
 * 取消订单库存更新组件
 * @author zwen
 *
 */
@Component
@Scope("prototype") 
public class CancelOrderWmsStockUpdater extends AbstractWmsStockUpdater {

	/**
	 * 调度结果
	 */
	private SaleDeliveryScheduleResult scheduleResult;
	
	/**
	 * 商品库存管理的DAO组件
	 */
	@Autowired
	private WmsGoodsStockDAO goodsStockDAO;
	/**
	 * 货位库存管理的DAO组件
	 */
	@Autowired
	private WmsGoodsAllocationStockDAO goodsAllocationStockDAO;
	/**
	 * 货位库存明细管理的DAO组件
	 */
	@Autowired
	private GoodsAllocationStockDetailDAO stockDetailDAO;
	
	/**
	 * 更新商品库存
	 */
	@Override
	protected void updateGoodsStock() throws Exception {
		OrderItemDTO orderItem = scheduleResult.getOrderItem();
		
		WmsGoodsStockDO goodsStock = goodsStockDAO.getBySkuId(orderItem.getGoodsSkuId());

		Long availableStockQuantity = goodsStock.getAvailableStockQuantity() 
				+ orderItem.getPurchaseQuantity();
		goodsStock.setAvailableStockQuantity(availableStockQuantity);
		
		Long lockedStockQuantity = goodsStock.getLockedStockQuantity()
				- orderItem.getPurchaseQuantity();
		goodsStock.setLockedStockQuantity(lockedStockQuantity); 
		
		goodsStockDAO.update(goodsStock); 
	}

	/**
	 * 更新货位库存
	 */
	@Override
	protected void updateGoodsAllocationStock() throws Exception {
		List<ScheduleOrderPickingItemDTO> pickingItems = scheduleResult.getPickingItems();
		
		for(ScheduleOrderPickingItemDTO pickingItem : pickingItems) {
			WmsGoodsAllocationStockDO goodsAllocationStock = goodsAllocationStockDAO
					.getBySkuId(pickingItem.getGoodsAllocationId(), pickingItem.getGoodsSkuId());
			
			Long availableStockQuantity = goodsAllocationStock.getAvailableStockQuantity() 
					+ pickingItem.getPickingCount();
			goodsAllocationStock.setAvailableStockQuantity(availableStockQuantity);
			
			Long lockedStockQuantity = goodsAllocationStock.getLockedStockQuantity()
					- pickingItem.getPickingCount();
			goodsAllocationStock.setLockedStockQuantity(lockedStockQuantity); 
			
			goodsAllocationStockDAO.update(goodsAllocationStock); 
		}
	}
	
	/**
	 * 更新货位库存明细
	 */
	@Override
	protected void updateGoodsAllocationStockDetail() throws Exception {
		List<ScheduleOrderSendOutDetailDTO> sendOutDetails = scheduleResult.getSendOutDetails();
		
		for(ScheduleOrderSendOutDetailDTO sendOutDetail : sendOutDetails) {
			GoodsAllocationStockDetailDO stockDetail = stockDetailDAO.getById(
					sendOutDetail.getGoodsAllocationStockDetailId());
			
			Long currentStockQuantity = stockDetail.getCurrentStockQuantity() 
					+ sendOutDetail.getSendOutCount();
			stockDetail.setCurrentStockQuantity(currentStockQuantity); 
			
			Long lockedStockQuantity = stockDetail.getLockedStockQuantity()
					- sendOutDetail.getSendOutCount();
			stockDetail.setLockedStockQuantity(lockedStockQuantity); 
			
			stockDetailDAO.update(stockDetail); 
		} 
 	}
	
	/**
	 * 设置需要的参数
	 */
	@Override
	public void setParameter(Object parameter) {
		this.scheduleResult = (SaleDeliveryScheduleResult) parameter; 
	}

}
