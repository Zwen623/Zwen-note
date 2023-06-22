package com.zwen.ipet.logistics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zwen.ipet.common.json.JsonExtractor;
import com.zwen.ipet.logistics.domain.FreightTemplateDTO;
import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.order.domain.OrderItemDTO;

/**
 * 固定运费计算器
 * @author zwen
 *
 */
@Component
public class FixedFreightCalculator implements FreightCalculator {
	
	@Autowired
	private JsonExtractor jsonExtractor;

	/**
	 * 计算订单条目的运费
	 * @param freightTemplate 运费模板
	 * @param order 订单
	 * @param orderItem 订单条目
	 * @return 运费
	 * @throws Exception
	 */
	@Override
	public Double calculate(FreightTemplateDTO freightTemplate, 
			OrderInfoDTO order, OrderItemDTO orderItem) throws Exception {
		JSONObject rule = JSONObject.parseObject(freightTemplate.getRule());
		return jsonExtractor.getDouble(rule, "fixed_freight"); 
	}
	
}
