package com.zwen.ipet.logistics.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zwen.ipet.commodity.domain.GoodsDTO;
import com.zwen.ipet.commodity.service.CommodityService;
import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.logistics.api.CreateEorderRequest;
import com.zwen.ipet.logistics.api.CreateEorderRequestBuilder;
import com.zwen.ipet.logistics.api.CreateEorderResponse;
import com.zwen.ipet.logistics.api.LogisticApi;
import com.zwen.ipet.logistics.domain.FreightTemplateDTO;
import com.zwen.ipet.logistics.service.FreightTemplateService;
import com.zwen.ipet.logistics.service.LogisticsService;
import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.order.domain.OrderItemDTO;
import com.zwen.ipet.wms.domain.LogisticOrderDTO;

/**
 * 物流中心接口
 * @author zwen
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LogisticsServiceImpl implements LogisticsService {
	
	private static final Logger logger = LoggerFactory.getLogger(LogisticsServiceImpl.class);
	
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	/**
	 * 商品中心接口
	 */
	@Autowired
	private CommodityService commodityService;
	/**
	 * 运费模板管理service组件
	 */
	@Autowired
	private FreightTemplateService freightTemplateService;
	/**
	 * 运费计算器工厂
	 */
	@Autowired
	private FreightCalculatorFactory freightCalculatorFactory;
	/**
	 * 物流api接口
	 */
	@Autowired
	private LogisticApi logisticApi;

	/**
	 * 计算商品sku的运费
	 * @param goodsSkuDTO 商品sku DTO
	 * @return 商品sku的运费
	 */
	@Override
	public Double calculateFreight(OrderInfoDTO order, OrderItemDTO orderItem) {
		try {
			// 获取商品对应的运费模板
			Long goodsId = orderItem.getGoodsId();
			GoodsDTO goods = commodityService.getGoodsById(goodsId);
			FreightTemplateDTO freightTemplate = freightTemplateService.getById(
					goods.getFreightTemplateId());
			
			// 获取运费计算器
			FreightCalculator freightCalculator = freightCalculatorFactory.get(freightTemplate);
			Double freight = freightCalculator.calculate(freightTemplate, order, orderItem);
			
			return freight;
		} catch (Exception e) {
			logger.error("error", e); 
			return 0.0;
		}
	}
	
	/**
	 * 申请物流单
	 * @param order 订单
	 * @return 物流单
	 */
	@Override
	public LogisticOrderDTO applyLogisticOrder(OrderInfoDTO order) {
		try {
			CreateEorderRequest request = CreateEorderRequestBuilder.get()
					.buildOrderRelatedInfo(order)
					.buildReceiver(order) 
					.buildGoodsList(order)
					.buildTotalDataMetric(order)
					.create();
			
			CreateEorderResponse response = logisticApi.createEOrder(request);
			
			LogisticOrderDTO logisticOrder = createLogisticOrder(response);
 			
			return logisticOrder;
		} catch (Exception e) {
			logger.error("error", e); 
			return null;
		}
	}
	
	/**
	 * 创建物流单
	 * @param response 创建电子面单响应结果
	 * @return 物流单
	 * @throws Exception
	 */
	private LogisticOrderDTO createLogisticOrder(
			CreateEorderResponse response) throws Exception {
		LogisticOrderDTO logisticOrder = new LogisticOrderDTO();
		logisticOrder.setLogisticCode(response.getLogisticCode());
		logisticOrder.setContent(response.getLogisticOrderContent()); 
		logisticOrder.setGmtCreate(dateProvider.getCurrentTime()); 
		logisticOrder.setGmtModified(dateProvider.getCurrentTime()); 
		
		return logisticOrder;
	}
	
	/**
	 * 获取订单的签收时间
	 * @param orderId 订单id
	 * @param orderNo 订单编号
	 * @return 签收时间
	 */
	@Override
	public Date getSignedTime(Long orderId, String orderNo) {
		try {
			return dateProvider.getCurrentTime();
		} catch (Exception e) {
			logger.error("error", e); 
			return null;
		}
	}
	
}
