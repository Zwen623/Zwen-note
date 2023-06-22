package com.zwen.ipet.schedule.stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zwen.ipet.order.domain.OrderItemDTO;
import com.zwen.ipet.schedule.dao.ScheduleGoodsAllocationStockDAO;
import com.zwen.ipet.schedule.dao.ScheduleGoodsAllocationStockDetailDAO;
import com.zwen.ipet.schedule.dao.ScheduleGoodsStockDAO;
import com.zwen.ipet.schedule.domain.SaleDeliveryScheduleResult;
import com.zwen.ipet.schedule.domain.ScheduleGoodsAllocationStockDO;
import com.zwen.ipet.schedule.domain.ScheduleGoodsAllocationStockDetailDO;
import com.zwen.ipet.schedule.domain.ScheduleGoodsStockDO;
import com.zwen.ipet.schedule.domain.ScheduleOrderPickingItemDTO;
import com.zwen.ipet.schedule.domain.ScheduleOrderSendOutDetailDTO;

/**
 * 支付订单库存更新组件
 * @author zwen
 *
 */
@Component
@Scope("prototype")  
@Transactional(rollbackFor = Exception.class)
public class PayOrderScheduleStockUpdater extends AbstractScheduleStockUpdater { 

	/**
	 * 商品库存管理的DAO组件
	 */
	@Autowired
	private ScheduleGoodsStockDAO goodsStockDAO;
	/**
	 * 货位库存管理的DAO组件
	 */
	@Autowired
	private ScheduleGoodsAllocationStockDAO goodsAllocationStockDAO;
	/**
	 * 货位库存明细管理的DAO组件
	 */
	@Autowired
	private ScheduleGoodsAllocationStockDetailDAO stockDetailDAO;
	
	/**
	 * 销售出库调度结果
	 */
	private SaleDeliveryScheduleResult scheduleResult;
	
	/**
	 * 更新商品库存
	 */
	@Override
	protected void updateGoodsStock() throws Exception {
		OrderItemDTO orderItem = scheduleResult.getOrderItem();
		ScheduleGoodsStockDO goodsStock = goodsStockDAO.getBySkuId(orderItem.getGoodsSkuId());
		goodsStock.setLockedStockQuantity(goodsStock.getLockedStockQuantity()  
				- orderItem.getPurchaseQuantity()); 
		goodsStock.setOutputStockQuantity(goodsStock.getOutputStockQuantity()
				+ orderItem.getPurchaseQuantity()); 
		goodsStockDAO.update(goodsStock); 
	}
	
	/**
	 * 更新货位库存
	 */
	@Override
	protected void updateGoodsAllocationStock() throws Exception {
		List<ScheduleOrderPickingItemDTO> pickingItems = scheduleResult.getPickingItems();
		for(ScheduleOrderPickingItemDTO pickingItem : pickingItems) {
			ScheduleGoodsAllocationStockDO goodsAllocationStock = goodsAllocationStockDAO.getBySkuId(
					pickingItem.getGoodsAllocationId(), pickingItem.getGoodsSkuId());   
			goodsAllocationStock.setLockedStockQuantity(goodsAllocationStock.getLockedStockQuantity()
					- pickingItem.getPickingCount()); 
			goodsAllocationStock.setOutputStockQuantity(goodsAllocationStock.getOutputStockQuantity()
					+ pickingItem.getPickingCount());  
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
			ScheduleGoodsAllocationStockDetailDO stockDetail = stockDetailDAO.getById(
					sendOutDetail.getGoodsAllocationStockDetailId());
			stockDetail.setLockedStockQuantity(stockDetail.getLockedStockQuantity()
					- sendOutDetail.getSendOutCount());
			stockDetailDAO.update(stockDetail); 
			
			// 上架数量
			// 当前数量
			// 锁定数量
			// 上架数量 - 当前数量 - 锁定数量 = 这个库存明细已经被发掉的库存数量
		}
	}
	
	/**
	 * 设置库存更新组件需要的参数
	 */
	@Override
	public void setParameter(Object parameter) {
		this.scheduleResult = (SaleDeliveryScheduleResult) parameter;
	}
	
}
