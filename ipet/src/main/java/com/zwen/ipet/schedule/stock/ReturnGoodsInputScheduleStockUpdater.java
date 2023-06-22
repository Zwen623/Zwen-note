package com.zwen.ipet.schedule.stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.order.domain.OrderItemDTO;
import com.zwen.ipet.order.service.OrderService;
import com.zwen.ipet.schedule.dao.ScheduleGoodsAllocationStockDAO;
import com.zwen.ipet.schedule.dao.ScheduleGoodsAllocationStockDetailDAO;
import com.zwen.ipet.schedule.dao.ScheduleGoodsStockDAO;
import com.zwen.ipet.schedule.domain.SaleDeliveryScheduleResult;
import com.zwen.ipet.schedule.domain.ScheduleGoodsAllocationStockDO;
import com.zwen.ipet.schedule.domain.ScheduleGoodsAllocationStockDetailDO;
import com.zwen.ipet.schedule.domain.ScheduleGoodsStockDO;
import com.zwen.ipet.schedule.domain.ScheduleOrderPickingItemDTO;
import com.zwen.ipet.schedule.service.SaleDeliveryScheduler;
import com.zwen.ipet.wms.domain.GoodsAllocationStockDetailDTO;
import com.zwen.ipet.wms.domain.ReturnGoodsInputOrderDTO;
import com.zwen.ipet.wms.domain.ReturnGoodsInputOrderItemDTO;

/**
 * 退货入库
 * @author zwen
 *
 */
@Component
@Scope("prototype")  
public class ReturnGoodsInputScheduleStockUpdater extends AbstractScheduleStockUpdater {

	/**
	 * 退货入库单
	 */
	private ReturnGoodsInputOrderDTO returnGoodsInputOrder;
	
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
	 * 销售出库调度器
	 */
	@Autowired
	private SaleDeliveryScheduler saleDeliveryScheduler;
	/**
	 * 订单中心接口
	 */
	@Autowired
	private OrderService orderService;
	
	/**
	 * 更新商品库存
	 */
	@Override
	protected void updateGoodsStock() throws Exception {
		List<ReturnGoodsInputOrderItemDTO> returnGoodsInputOrderItems = 
				returnGoodsInputOrder.getItems();
		for(ReturnGoodsInputOrderItemDTO returnGoodsInputOrderItem : returnGoodsInputOrderItems) {
			ScheduleGoodsStockDO goodsStock = goodsStockDAO.getBySkuId(
					returnGoodsInputOrderItem.getGoodsSkuId());
			goodsStock.setAvailableStockQuantity(goodsStock.getAvailableStockQuantity()
					+ returnGoodsInputOrderItem.getArrivalCount()); 
			goodsStock.setOutputStockQuantity(goodsStock.getOutputStockQuantity()
					- returnGoodsInputOrderItem.getArrivalCount());
			goodsStockDAO.update(goodsStock); 
		}
	}

	/**
	 * 更新货位库存
	 */
	@Override
	protected void updateGoodsAllocationStock() throws Exception {
		List<ReturnGoodsInputOrderItemDTO> items = returnGoodsInputOrder.getItems();
		
		OrderInfoDTO order = orderService.getOrderById(returnGoodsInputOrder.getOrderId());
		
		for(ReturnGoodsInputOrderItemDTO item : items) {
			OrderItemDTO targetOrderItem = null;
			
			for(OrderItemDTO orderItem : order.getOrderItems()) {
				if(orderItem.getGoodsSkuId().equals(item.getGoodsSkuId())) {
					targetOrderItem = orderItem;
					break;
				}
			}
			
			SaleDeliveryScheduleResult scheduleResult = saleDeliveryScheduler.getScheduleResult(targetOrderItem);
			
			for(ScheduleOrderPickingItemDTO pickingItem : scheduleResult.getPickingItems()) {
				ScheduleGoodsAllocationStockDO goodsAllocationStock = goodsAllocationStockDAO.getBySkuId(
						pickingItem.getGoodsAllocationId(), pickingItem.getGoodsSkuId());
				goodsAllocationStock.setAvailableStockQuantity(goodsAllocationStock.getAvailableStockQuantity() 
						+ pickingItem.getPickingCount());
				goodsAllocationStock.setOutputStockQuantity(goodsAllocationStock.getOutputStockQuantity()
						- pickingItem.getPickingCount()); 
				goodsAllocationStockDAO.update(goodsAllocationStock); 
			}
		}
	}
	
	/**
	 * 更新货位库存明细
	 */
	@Override
	protected void updateGoodsAllocationStockDetail() throws Exception {
		List<ReturnGoodsInputOrderItemDTO> items = returnGoodsInputOrder.getItems();
		
		for(ReturnGoodsInputOrderItemDTO item : items) {
			List<GoodsAllocationStockDetailDTO> stockDetails = item.getStockDetails();
			for(GoodsAllocationStockDetailDTO stockDetail : stockDetails) {
				stockDetailDAO.save(stockDetail.clone(ScheduleGoodsAllocationStockDetailDO.class));  
			}
		}
 	}
	
	/**
	 * 设置需要的参数
	 */
	@Override
	public void setParameter(Object parameter) {
		this.returnGoodsInputOrder = (ReturnGoodsInputOrderDTO) parameter;
	}
	
}
