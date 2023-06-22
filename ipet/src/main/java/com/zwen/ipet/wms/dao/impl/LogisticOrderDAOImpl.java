package com.zwen.ipet.wms.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zwen.ipet.wms.dao.LogisticOrderDAO;
import com.zwen.ipet.wms.domain.LogisticOrderDO;
import com.zwen.ipet.wms.mapper.LogisticOrderMapper;

/**
 * 物流单管理DAO组件
 * @author zwen
 *
 */
@Repository
public class LogisticOrderDAOImpl implements LogisticOrderDAO {

	/**
	 * 物流单管理mapper组件
	 */
	@Autowired
	private LogisticOrderMapper logisticOrderMapper;
	
	/**
	 * 新增物流单
	 * @param logisticOrder 物流单
	 */
	@Override
	public void save(LogisticOrderDO logisticOrder) throws Exception {
		logisticOrderMapper.save(logisticOrder); 
	}
	
	/**
	 * 根据销售出库单id查询物流单
	 * @param saleDeliveryOrderId 销售出库单id
	 * @return 物流单
	 */
	@Override
	public LogisticOrderDO getBySaleDeliveryOrderId(
			Long saleDeliveryOrderId) throws Exception {
		return logisticOrderMapper.getBySaleDeliveryOrderId(saleDeliveryOrderId);
	}
	
}
