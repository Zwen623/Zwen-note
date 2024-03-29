package com.zwen.ipet.inventory.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zwen.ipet.inventory.constant.GoodsStockUpdateOperation;
import com.zwen.ipet.inventory.dao.GoodsStockDAO;
import com.zwen.ipet.inventory.service.InventoryService;
import com.zwen.ipet.inventory.stock.CancelOrderStockUpdaterFactory;
import com.zwen.ipet.inventory.stock.PayOrderStockUpdaterFactory;
import com.zwen.ipet.inventory.stock.PurchaseInputStockUpdaterFactory;
import com.zwen.ipet.inventory.stock.ReturnGoodsInputStockUpdaterFactory;
import com.zwen.ipet.inventory.stock.StockUpdater;
import com.zwen.ipet.inventory.stock.SubmitOrderStockUpdaterFactory;
import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.inventory.async.StockUpdateMessage;
import com.zwen.ipet.inventory.async.StockUpdateQueue;
import com.zwen.ipet.inventory.async.StockUpdateResultManager;
import com.zwen.ipet.inventory.domain.GoodsStockDO;
import com.zwen.ipet.order.domain.OrderInfoDTO;
import com.zwen.ipet.wms.domain.PurchaseInputOrderDTO;
import com.zwen.ipet.wms.domain.ReturnGoodsInputOrderDTO;

/**
 * 库存中心对外接口service组件
 * @author zwen
 *
 */
@Service
public class InventoryServiceImpl implements InventoryService {
	
	private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);
	
	/**
	 * 采购入库库存更新命令工厂
	 */
	@Autowired
	private PurchaseInputStockUpdaterFactory<PurchaseInputOrderDTO> 
			purchaseInputStockUpdateCommandFactory;
	/**
	 * 退货入库库存更新命令工厂
	 */
	@Autowired
	private ReturnGoodsInputStockUpdaterFactory<ReturnGoodsInputOrderDTO> 
			returnGoodsInputStockUpdateCommandFactory;
	/**
	 * 提交订单库存更新组件工厂
	 */
	@Autowired
	private SubmitOrderStockUpdaterFactory<OrderInfoDTO> 
			submitOrderStockUpdaterFactory;
	/**
	 * 支付订单库存更新组件工厂
	 */
	@Autowired
	private PayOrderStockUpdaterFactory<OrderInfoDTO> 
			payOrderStockUpdaterFactory;
	/**
	 * 取消订单库存更新组件工厂
	 */
	@Autowired
	private CancelOrderStockUpdaterFactory<OrderInfoDTO> 
			cancelOrderStockUpdaterFactory;
	/**
	 * 商品库存管理模块DAO组件
	 */
	@Autowired
	private GoodsStockDAO goodsStockDAO;
	/**
	 * 商品库存更新队列
	 */
	@Autowired
	private StockUpdateQueue goodsStockUpdateQueue;
	/**
	 * 商品库存更新管理组件
	 */
	@Autowired
	private StockUpdateResultManager goodsStockUpdateManager;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 通知库存中心，“采购入库完成”事件发生了
	 * @param purchaseInputOrderDTO 采购入库单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informPurchaseInputFinished(
			PurchaseInputOrderDTO purchaseInputOrderDTO) {
		try {
			StockUpdater goodsStockUpdateCommand = 
					purchaseInputStockUpdateCommandFactory.create(purchaseInputOrderDTO);
			goodsStockUpdateCommand.updateGoodsStock();
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
		return true;
	}
	
	/**
	 * 通知库存中心，“完成退货入库”事件发生了
	 * @param returnGoodsInputOrderDTO 退货入库单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informReturnGoodsInputFinished(
			ReturnGoodsInputOrderDTO returnGoodsInputOrderDTO) {
		try {
			StockUpdater goodsStockUpdateCommand = 
					returnGoodsInputStockUpdateCommandFactory.create(returnGoodsInputOrderDTO);
			goodsStockUpdateCommand.updateGoodsStock();
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
		return true;
	}
	
	/**
	 * 通知库存中心，“提交订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informSubmitOrderEvent(OrderInfoDTO orderDTO) {
		try {
			// 更新本地库存
			StockUpdater goodsStockUpdateCommand = 
					submitOrderStockUpdaterFactory.create(orderDTO); 
			goodsStockUpdateCommand.updateGoodsStock();
			
			// 发送异步消息到内存队列
			StockUpdateMessage message = new StockUpdateMessage();
			message.setId(UUID.randomUUID().toString().replace("-", ""));   
			message.setOperation(GoodsStockUpdateOperation.SUBMIT_ORDER);
			message.setParameter(orderDTO);  
			
			goodsStockUpdateQueue.put(message);
			
			// 监听异步处理结果
			goodsStockUpdateManager.observe(message.getId());  
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
		return true;
	}
	
	/**
	 * 通知库存中心，“支付订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informPayOrderEvent(OrderInfoDTO orderDTO) {
		try {
			// 更新本地库存
			StockUpdater goodsStockUpdateCommand = 
					payOrderStockUpdaterFactory.create(orderDTO); 
			goodsStockUpdateCommand.updateGoodsStock();
			
			// 发送异步消息到内存队列
			StockUpdateMessage message = new StockUpdateMessage();
			message.setId(UUID.randomUUID().toString().replace("-", ""));
			message.setOperation(GoodsStockUpdateOperation.PAY_ORDER);
			message.setParameter(orderDTO);  
			
			goodsStockUpdateQueue.put(message);
			
			// 监听异步处理结果
			goodsStockUpdateManager.observe(message.getId());  
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
		return true;
	}
	
	/**
	 * 通知库存中心，“取消订单”事件发生了
	 * @param orderDTO 订单DTO
	 * @return 处理结果
	 */
	@Override
	public Boolean informCancelOrderEvent(OrderInfoDTO orderDTO) {
		try {
			// 更新本地库存
			StockUpdater goodsStockUpdateCommand = 
					cancelOrderStockUpdaterFactory.create(orderDTO); 
			goodsStockUpdateCommand.updateGoodsStock();
			
			// 发送异步消息到内存队列
			StockUpdateMessage message = new StockUpdateMessage();
			message.setId(UUID.randomUUID().toString().replace("-", ""));
			message.setOperation(GoodsStockUpdateOperation.CANCEL_ORDER);
			message.setParameter(orderDTO);  
			
			goodsStockUpdateQueue.put(message);
			
			// 监听异步处理结果
			goodsStockUpdateManager.observe(message.getId());  
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
		return true;
	}
	
	/**
	 * 查询商品sku的库存
	 * @param goodsSkuId 商品sku id
	 * @return 商品sku的库存
	 */
	@Override
	public Long getSaleStockQuantity(Long goodsSkuId) {
		try {
			GoodsStockDO goodsStockDO = goodsStockDAO
					.getGoodsStockBySkuId(goodsSkuId);
			if(goodsStockDO == null) {
				return 0L;
			}
			
			return goodsStockDO.getSaleStockQuantity();
		} catch (Exception e) {
			logger.error("error", e); 
		}
		return 0L;
	}
	
	/**
	 * 设置销售库存
	 * @param goodsSkuId 商品sku id
	 * @param saleStockQuantity 销售库存
	 * @return 处理结果
	 */
	@Override
	public Boolean setSaleStockQuantity(Long goodsSkuId, Long saleStockQuantity) {
		try {
			GoodsStockDO goodsStock = goodsStockDAO.getGoodsStockBySkuId(goodsSkuId);
			
			if(goodsStock == null) {
				goodsStock = new GoodsStockDO();
				goodsStock.setGoodsSkuId(goodsSkuId); 
				goodsStock.setSaleStockQuantity(saleStockQuantity); 
				goodsStock.setLockedStockQuantity(0L); 
				goodsStock.setSaledStockQuantity(0L);  
				goodsStock.setStockStatus(saleStockQuantity > 0L ? 1 : 0); 
				goodsStock.setGmtCreate(dateProvider.getCurrentTime()); 
				goodsStock.setGmtModified(dateProvider.getCurrentTime()); 
				goodsStockDAO.saveGoodsStock(goodsStock);
			} else {
				goodsStock.setSaleStockQuantity(saleStockQuantity); 
				goodsStockDAO.updateGoodsStock(goodsStock);
			}
			
			return true;
		} catch (Exception e) {
			logger.error("error", e); 
			return false;
		}
	}
	
}
