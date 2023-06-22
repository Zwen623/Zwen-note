package com.zwen.ipet.wms.stock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zwen.ipet.common.util.ObjectUtils;
import com.zwen.ipet.schedule.domain.SaleDeliveryScheduleResult;
import com.zwen.ipet.schedule.domain.ScheduleOrderPickingItemDTO;
import com.zwen.ipet.schedule.service.ScheduleService;
import com.zwen.ipet.wms.dao.GoodsAllocationStockDetailDAO;
import com.zwen.ipet.wms.dao.WmsGoodsAllocationStockDAO;
import com.zwen.ipet.wms.dao.WmsGoodsStockDAO;
import com.zwen.ipet.wms.domain.GoodsAllocationStockDetailDO;
import com.zwen.ipet.wms.domain.GoodsAllocationStockDetailDTO;
import com.zwen.ipet.wms.domain.ReturnGoodsInputOrderDTO;
import com.zwen.ipet.wms.domain.ReturnGoodsInputOrderItemDTO;
import com.zwen.ipet.wms.domain.ReturnGoodsInputOrderPutOnItemDTO;
import com.zwen.ipet.wms.domain.WmsGoodsAllocationStockDO;
import com.zwen.ipet.wms.domain.WmsGoodsStockDO;

/**
 * 退货入库库存更新组件
 * @author zwen
 *
 */
@Component
@Scope("prototype") 
public class ReturnGoodsInputWmsStockUpdater extends AbstractWmsStockUpdater {

	/**
	 * 退货入库单
	 */
	private ReturnGoodsInputOrderDTO returnGoodsInputOrder;
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
	 * 调度中心接口
	 */
	@Autowired
	private ScheduleService scheduleService;
	
	/**
	 * 更新商品库存
	 */
	@Override
	protected void updateGoodsStock() throws Exception {
		List<ReturnGoodsInputOrderItemDTO> returnGoodsInputOrderItems = 
				returnGoodsInputOrder.getItems();
		
		for(ReturnGoodsInputOrderItemDTO returnGoodsInputOrderItem : returnGoodsInputOrderItems) {
			WmsGoodsStockDO goodsStock = goodsStockDAO.getBySkuId(
					returnGoodsInputOrderItem.getGoodsSkuId());
			
			Long availableStockQuantity = goodsStock.getAvailableStockQuantity() 
					+ returnGoodsInputOrderItem.getArrivalCount();
			goodsStock.setAvailableStockQuantity(availableStockQuantity); 
			
			Long outputStockQuantity = goodsStock.getOutputStockQuantity()
					- returnGoodsInputOrderItem.getArrivalCount();
			goodsStock.setOutputStockQuantity(outputStockQuantity);
			
			goodsStockDAO.update(goodsStock); 
		}
	}

	/**
	 * 更新货位库存
	 */
	@Override
	protected void updateGoodsAllocationStock() throws Exception {
		List<ReturnGoodsInputOrderItemDTO> items = returnGoodsInputOrder.getItems();
		
		for(ReturnGoodsInputOrderItemDTO item : items) {
			SaleDeliveryScheduleResult scheduleResult = scheduleService.getScheduleResult(
					returnGoodsInputOrder.getOrderId(), item.getGoodsSkuId());
			
			for(ScheduleOrderPickingItemDTO pickingItem : scheduleResult.getPickingItems()) {
				WmsGoodsAllocationStockDO goodsAllocationStock = goodsAllocationStockDAO
						.getBySkuId(pickingItem.getGoodsAllocationId(), pickingItem.getGoodsSkuId());
				
				Long availableStockQuantity = goodsAllocationStock.getAvailableStockQuantity() 
						+ pickingItem.getPickingCount();
				goodsAllocationStock.setAvailableStockQuantity(availableStockQuantity);
				
				Long outputStockQuantity = goodsAllocationStock.getOutputStockQuantity() 
						- pickingItem.getPickingCount();
				goodsAllocationStock.setOutputStockQuantity(outputStockQuantity); 
				
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
			List<ReturnGoodsInputOrderPutOnItemDTO> putOnItems = item.getPutOnItems();
			List<GoodsAllocationStockDetailDO> stockDetails = new ArrayList<GoodsAllocationStockDetailDO>();
			
			for(ReturnGoodsInputOrderPutOnItemDTO putOnItem : putOnItems) {
				GoodsAllocationStockDetailDO stockDetail = new GoodsAllocationStockDetailDO();
				stockDetail.setGoodsAllocationId(putOnItem.getGoodsAllocationId());
				stockDetail.setGoodsSkuId(putOnItem.getGoodsSkuId()); 
				stockDetail.setPutOnQuantity(putOnItem.getPutOnShelvesCount()); 
				stockDetail.setPutOnTime(putOnItem.getGmtCreate());  
				stockDetail.setCurrentStockQuantity(stockDetail.getPutOnQuantity()); 
				stockDetail.setLockedStockQuantity(0L); 
				
				stockDetailDAO.save(stockDetail); 
				
				stockDetails.add(stockDetail);
			} 
			
			item.setStockDetails(ObjectUtils.convertList(stockDetails, 
					GoodsAllocationStockDetailDTO.class));  
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
