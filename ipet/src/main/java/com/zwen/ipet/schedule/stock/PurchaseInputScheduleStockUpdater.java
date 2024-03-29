package com.zwen.ipet.schedule.stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zwen.ipet.schedule.dao.ScheduleGoodsAllocationStockDAO;
import com.zwen.ipet.schedule.dao.ScheduleGoodsAllocationStockDetailDAO;
import com.zwen.ipet.schedule.dao.ScheduleGoodsStockDAO;
import com.zwen.ipet.schedule.domain.ScheduleGoodsAllocationStockDO;
import com.zwen.ipet.schedule.domain.ScheduleGoodsAllocationStockDetailDO;
import com.zwen.ipet.schedule.domain.ScheduleGoodsStockDO;
import com.zwen.ipet.wms.domain.GoodsAllocationStockDetailDTO;
import com.zwen.ipet.wms.domain.PurchaseInputOrderDTO;
import com.zwen.ipet.wms.domain.PurchaseInputOrderItemDTO;
import com.zwen.ipet.wms.domain.PurchaseInputOrderPutOnItemDTO;

/**
 * 采购入库库存更新组件
 * @author zwen
 *
 */
@Component
@Scope("prototype") 
public class PurchaseInputScheduleStockUpdater extends AbstractScheduleStockUpdater {

	/**
	 * 采购入库单
	 */
	private PurchaseInputOrderDTO purchaseInputOrder;
	
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
	 * 更新商品库存
	 */
	@Override
	protected void updateGoodsStock() throws Exception {
		List<PurchaseInputOrderItemDTO> purchaseInputOrderItems = 
				purchaseInputOrder.getItems();
		for(PurchaseInputOrderItemDTO purchaseInputOrderItem : purchaseInputOrderItems) {
			ScheduleGoodsStockDO goodsStock = goodsStockDAO.getBySkuId(
					purchaseInputOrderItem.getGoodsSkuId());
			goodsStock.setAvailableStockQuantity(goodsStock.getAvailableStockQuantity()
					+ purchaseInputOrderItem.getArrivalCount()); 
			goodsStockDAO.update(goodsStock); 
		}
	}

	/**
	 * 更新货位库存
	 */
	@Override
	protected void updateGoodsAllocationStock() throws Exception {
		List<PurchaseInputOrderItemDTO> items = purchaseInputOrder.getItems();
		
		for(PurchaseInputOrderItemDTO item : items) {
			List<PurchaseInputOrderPutOnItemDTO> putOnItems = item.getPutOnItemDTOs();
			
			for(PurchaseInputOrderPutOnItemDTO putOnItem : putOnItems) {
				ScheduleGoodsAllocationStockDO goodsAllocationStock = goodsAllocationStockDAO
						.getBySkuId(putOnItem.getGoodsAllocationId(), putOnItem.getGoodsSkuId());
				goodsAllocationStock.setAvailableStockQuantity(goodsAllocationStock.getAvailableStockQuantity() 
						+ putOnItem.getPutOnShelvesCount());
				goodsAllocationStockDAO.update(goodsAllocationStock); 
			}
		}
	}
	
	/**
	 * 更新货位库存明细
	 */
	@Override
	protected void updateGoodsAllocationStockDetail() throws Exception {
		List<PurchaseInputOrderItemDTO> items = purchaseInputOrder.getItems();
		
		for(PurchaseInputOrderItemDTO item : items) {
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
		this.purchaseInputOrder = (PurchaseInputOrderDTO) parameter;
	}

}
