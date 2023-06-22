package com.zwen.ipet.order.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zwen.ipet.common.json.JsonExtractor;
import com.zwen.ipet.order.domain.OrderItemDTO;
import com.zwen.ipet.promotion.domain.PromotionActivityDTO;

/**
 * 满减类型的促销活动价格计算组件
 * @author zwen
 *
 */
@Component
public class ReachDiscountPromotionActivityCalculator implements PromotionActivityCalculator {

	@Autowired
	private JsonExtractor jsonExtractor;
	
	/**
	 * 计算促销活动的减免金额
	 */
	@Override
	public PromotionActivityResult calculate(OrderItemDTO item, 
			PromotionActivityDTO promotionActivity) throws Exception {
		Double totalAmount = item.getPurchaseQuantity() * item.getPurchasePrice();
		
		String rulesJson = promotionActivity.getRule();
		
		JSONArray rules = JSONArray.parseArray(rulesJson);
		
		for(int i = 0; i < rules.size(); i++) {
			JSONObject rule = rules.getJSONObject(i);
		
			Double thresholdAmount = jsonExtractor.getDouble(rule, "thresholdAmount"); 
			Double reduceAmount = jsonExtractor.getDouble(rule, "reduceAmount"); 
			
			if(totalAmount > thresholdAmount) {
				return new PromotionActivityResult(reduceAmount); 
			}
		}
		
		return new PromotionActivityResult(); 
	}

}
