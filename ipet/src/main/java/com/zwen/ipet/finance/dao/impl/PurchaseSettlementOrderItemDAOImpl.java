package com.zwen.ipet.finance.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.finance.dao.PurchaseSettlementOrderItemDAO;
import com.zwen.ipet.finance.domain.PurchaseSettlementOrderItemDO;
import com.zwen.ipet.finance.mapper.PurchaseSettlementOrderItemMapper;

/**
 * 采购结算单条目管理DAO组件
 * @author zwen
 *
 */
@Repository
public class PurchaseSettlementOrderItemDAOImpl implements PurchaseSettlementOrderItemDAO {

	/**
	 * 采购结算单条目管理mapper组件
	 */
	@Autowired
	private PurchaseSettlementOrderItemMapper purchaseSettlementOrderItemMapper;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 批量新增采购结算单条目
	 * @param purchaseSettlementOrderId 采购结算单id
	 * @param purchaseSettlementOrderItems 采购结算单条目
	 * @throws Exception
	 */
	@Override
	public void batchSave(Long purchaseSettlementOrderId, 
			List<PurchaseSettlementOrderItemDO> purchaseSettlementOrderItems) throws Exception {
		for(PurchaseSettlementOrderItemDO purchaseSettlementOrderItem : purchaseSettlementOrderItems) {
			purchaseSettlementOrderItem.setGmtCreate(dateProvider.getCurrentTime());
			purchaseSettlementOrderItem.setGmtModified(dateProvider.getCurrentTime()); 
			purchaseSettlementOrderItem.setPurchaseSettlementOrderId(purchaseSettlementOrderId);
			purchaseSettlementOrderItemMapper.save(purchaseSettlementOrderItem); 
		}
	}
	
	/**
	 * 根据采购结算单id查询采购结算单条目
	 * @param purchaseSettlementOrderId 采购结算单id
	 * @return 采购结算单条目
	 */
	@Override
	public List<PurchaseSettlementOrderItemDO> listByPurchaseSettlementOrderId(
			Long purchaseSettlementOrderId) throws Exception {
		return purchaseSettlementOrderItemMapper.listByPurchaseSettlementOrderId(
				purchaseSettlementOrderId);
	}
	
}
