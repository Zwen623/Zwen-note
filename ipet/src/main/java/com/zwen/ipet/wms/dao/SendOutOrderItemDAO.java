package com.zwen.ipet.wms.dao;

import java.util.List;

import com.zwen.ipet.wms.domain.SendOutOrderItemDO;

/**
 * 发货单条目管理DAO接口
 * @author zwen
 *
 */
public interface SendOutOrderItemDAO {

	/**
	 * 新增发货单条目
	 * @param sendOutOrderItem 发货单条目
	 * @throws Exception
	 */
	void save(SendOutOrderItemDO sendOutOrderItem) throws Exception;
	
	/**
	 * 查询发货单条目
	 * @param sendOutOrderId 发货单id
	 * @return 发货单条目
	 * @throws Exception
	 */
	List<SendOutOrderItemDO> listByOrderInfoId(Long sendOutOrderId) throws Exception;
	
}
