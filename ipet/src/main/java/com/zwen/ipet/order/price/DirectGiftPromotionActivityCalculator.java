package com.zwen.ipet.order.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zwen.ipet.commodity.domain.GoodsSkuDTO;
import com.zwen.ipet.commodity.service.CommodityService;
import com.zwen.ipet.order.domain.OrderItemDTO;
import com.zwen.ipet.promotion.domain.PromotionActivityDTO;

/**
 * 赠品促销类型的促销活动的价格计算组件
 * @author zwen
 *
 */
@Component
public class DirectGiftPromotionActivityCalculator 
		extends AbstractGiftPromotionActivityCalculator 
		implements PromotionActivityCalculator {

	/**
	 * 商品中心接口
	 */
	@Autowired
	private CommodityService commodityService;
	
	@Override
	public PromotionActivityResult calculate(OrderItemDTO item, 
			PromotionActivityDTO promotionActivity) {
		JSONObject rule = JSONObject.parseObject(promotionActivity.getRule());
		JSONArray giftGoodsSkuIds = rule.getJSONArray("giftGoodsSkuIds");
		
		PromotionActivityResult result = new PromotionActivityResult();
		
		for(int i = 0; i < giftGoodsSkuIds.size(); i++) {
			Long goodsSkuId = giftGoodsSkuIds.getLong(i);
			GoodsSkuDTO goodsSku = commodityService.getGoodsSkuById(goodsSkuId);
			result.getOrderItems().add(createOrderItem(goodsSku));
		} 
		
		return result;
	}

}
