package com.zwen.ipet.order.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zwen.ipet.commodity.domain.GoodsSkuDTO;
import com.zwen.ipet.commodity.service.CommodityService;
import com.zwen.ipet.common.json.JsonExtractor;
import com.zwen.ipet.order.domain.OrderItemDTO;
import com.zwen.ipet.promotion.domain.PromotionActivityDTO;

/**
 * 满赠类型的促销胡活动的处理器
 * @author zwen
 *
 */
@Component
public class ReachGiftPromotionActivityCalculator 
		extends AbstractGiftPromotionActivityCalculator 
		implements PromotionActivityCalculator {

	/**
	 * 商品中心接口
	 */
	@Autowired
	private CommodityService commodityService;
	
	@Autowired
	private JsonExtractor jsonExtractor;
	
	/**
	 * 计算促销活动的减免金额
	 */
	@Override
	public PromotionActivityResult calculate(OrderItemDTO item, 
			PromotionActivityDTO promotionActivity) throws Exception {
		Double totalAmount = item.getPurchaseQuantity() * item.getPurchasePrice();
		
		JSONObject rule = JSONObject.parseObject(promotionActivity.getRule());
		Double thresholdAmount = jsonExtractor.getDouble(rule, "thresholdAmount"); 
		JSONArray giftGoodsSkuIds = rule.getJSONArray("giftGoodsSkuIds");
		
		if(totalAmount > thresholdAmount) {
			PromotionActivityResult result = new PromotionActivityResult();
			
			for(int i = 0; i < giftGoodsSkuIds.size(); i++) {
				Long goodsSkuId = giftGoodsSkuIds.getLong(i);
				GoodsSkuDTO goodsSku = commodityService.getGoodsSkuById(goodsSkuId);
				result.getOrderItems().add(createOrderItem(goodsSku));
			} 
			
			return result;
		}
		
		return new PromotionActivityResult(); 
	}
	
}
