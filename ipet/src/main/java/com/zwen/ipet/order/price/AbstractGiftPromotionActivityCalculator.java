package com.zwen.ipet.order.price;

import com.zwen.ipet.commodity.domain.GoodsSkuDTO;
import com.zwen.ipet.order.domain.OrderItemDTO;

/**
 * 赠品类型的促销活动计算组件的基类
 * @author zwen
 *
 */
public class AbstractGiftPromotionActivityCalculator {

	/**
	 * 创建订单条目
	 * @param goodsSku 商品sku
	 * @return
	 */
	protected OrderItemDTO createOrderItem(GoodsSkuDTO goodsSku) {
		OrderItemDTO orderItem = new OrderItemDTO();
		orderItem.setGoodsId(goodsSku.getGoodsId()); 
		orderItem.setGoodsSkuId(goodsSku.getId()); 
		orderItem.setGoodsSkuCode(goodsSku.getGoodsSkuCode()); 
		orderItem.setGoodsName(goodsSku.getGoodsName()); 
		orderItem.setSaleProperties(goodsSku.getSaleProperties()); 
		orderItem.setGoodsGrossWeight(goodsSku.getGrossWeight()); 
		orderItem.setPurchaseQuantity(1L); 
		orderItem.setPurchasePrice(0.0); 
		orderItem.setPromotionActivityId(null); 
		orderItem.setGoodsLength(goodsSku.getGoodsLength()); 
		orderItem.setGoodsWidth(goodsSku.getGoodsWidth()); 
		orderItem.setGoodsHeight(goodsSku.getGoodsHeight()); 
		return orderItem;
	}
	
}
