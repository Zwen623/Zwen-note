package com.zwen.ipet.wms.dao;

import com.zwen.ipet.wms.domain.LogisticOrderDO;

/**
 * 物流单管理DAO接口
 * @author zwen
 *
 */
public interface LogisticOrderDAO {

	/**
	 * 新增物流单
	 * @param logisticOrder 物流单
	 * @throws Exception
	 */
	void save(LogisticOrderDO logisticOrder) throws Exception;
	
	/**
	 * 根据销售出库单id查询物流单
	 * @param saleDeliveryOrderId 销售出库单id
	 * @return 物流单
	 * @throws Exception
	 */
	LogisticOrderDO getBySaleDeliveryOrderId(Long saleDeliveryOrderId) throws Exception;
	
}
