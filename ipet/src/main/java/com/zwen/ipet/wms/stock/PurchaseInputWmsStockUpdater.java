package com.zwen.ipet.wms.stock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zwen.ipet.common.util.ObjectUtils;
import com.zwen.ipet.wms.dao.GoodsAllocationStockDetailDAO;
import com.zwen.ipet.wms.dao.WmsGoodsAllocationStockDAO;
import com.zwen.ipet.wms.dao.WmsGoodsStockDAO;
import com.zwen.ipet.wms.domain.GoodsAllocationStockDetailDO;
import com.zwen.ipet.wms.domain.GoodsAllocationStockDetailDTO;
import com.zwen.ipet.wms.domain.PurchaseInputOrderDTO;
import com.zwen.ipet.wms.domain.PurchaseInputOrderItemDTO;
import com.zwen.ipet.wms.domain.PurchaseInputOrderPutOnItemDTO;
import com.zwen.ipet.wms.domain.WmsGoodsAllocationStockDO;
import com.zwen.ipet.wms.domain.WmsGoodsStockDO;

/**
 * 采购入库库存更新组件
 * @author zwen
 *
 */
@Component
@Scope("prototype") 
public class PurchaseInputWmsStockUpdater extends AbstractWmsStockUpdater {

	/**
	 * 采购入库单
	 */
	private PurchaseInputOrderDTO purchaseInputOrder;
	
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
		List<PurchaseInputOrderItemDTO> purchaseInputOrderItems = 
				purchaseInputOrder.getItems();
		for(PurchaseInputOrderItemDTO purchaseInputOrderItem : purchaseInputOrderItems) {
			WmsGoodsStockDO goodsStock = goodsStockDAO.getBySkuId(
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
				WmsGoodsAllocationStockDO goodsAllocationStock = goodsAllocationStockDAO
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
			List<PurchaseInputOrderPutOnItemDTO> putOnItems = item.getPutOnItemDTOs();
			List<GoodsAllocationStockDetailDO> stockDetails = 
					new ArrayList<GoodsAllocationStockDetailDO>();
			
			for(PurchaseInputOrderPutOnItemDTO putOnItem : putOnItems) {
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
		this.purchaseInputOrder = (PurchaseInputOrderDTO) parameter;
	}

}
