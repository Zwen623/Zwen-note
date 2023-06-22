package com.zwen.ipet.inventory.stock;

import java.util.List;
import java.util.Map;

import com.zwen.ipet.inventory.dao.GoodsStockDAO;
import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.inventory.domain.GoodsStockDO;
import com.zwen.ipet.order.domain.OrderItemDTO;

/**
 * 支付订单库存更新组件
 * @author zwen
 *
 */
public class PayOrderStockUpdater extends AbstractStockUpdater {

	/**
	 * 订单条目DTO对象集合
	 */
	private Map<Long, OrderItemDTO> orderItemDTOMap;
	
	/**
	 * 构造函数
	 * @param goodsStockDOs 商品库存DO对象集合
	 * @param goodsStockDAO 商品库存管理模块DAO组件 
	 * @param dateProvider 日期辅助组件
	 */
	public PayOrderStockUpdater(
			List<GoodsStockDO> goodsStockDOs, 
			GoodsStockDAO goodsStockDAO,
			DateProvider dateProvider,
			Map<Long, OrderItemDTO> orderItemDTOMap) {
		super(goodsStockDOs, goodsStockDAO, dateProvider);
		this.orderItemDTOMap = orderItemDTOMap;
	}

	/**
	 * 更新销售库存
	 */
	@Override
	protected void updateSaleStockQuantity() throws Exception {

	}

	/**
	 * 更新锁定库存
	 */
	@Override
	protected void updateLockedStockQuantity() throws Exception {
		for(GoodsStockDO goodsStockDO : goodsStockDOs) {
			OrderItemDTO orderItemDTO = orderItemDTOMap.get(goodsStockDO.getGoodsSkuId());
			goodsStockDO.setLockedStockQuantity(goodsStockDO.getLockedStockQuantity() 
					- orderItemDTO.getPurchaseQuantity()); 
		}
	}
	
	/**
	 * 更新已销售库存
	 */
	@Override
	protected void updateSaledStockQuantity() throws Exception {
		for(GoodsStockDO goodsStockDO : goodsStockDOs) {
			OrderItemDTO orderItemDTO = orderItemDTOMap.get(goodsStockDO.getGoodsSkuId());
			goodsStockDO.setSaledStockQuantity(goodsStockDO.getSaledStockQuantity() 
					+ orderItemDTO.getPurchaseQuantity());  
		}
	}

}
