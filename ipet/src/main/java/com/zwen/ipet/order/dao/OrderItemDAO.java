package com.zwen.ipet.order.dao;

import java.util.List;

import com.zwen.ipet.order.domain.OrderItemDO;

/**
 * 订单条目管理DAO组件接口
 * @author zwen
 *
 */
public interface OrderItemDAO {

	/**
	 * 新增订单条目
	 * @param orderItem 订单条目
	 * @return 订单条目id
	 * @throws Exception
	 */
	Long save(OrderItemDO orderItem) throws Exception;
	
	/**
	 * 查询订单条目
	 * @param orderInfoId 订单id
	 * @return 订单条目
	 * @throws Exception
	 */
	List<OrderItemDO> listByOrderInfoId(Long orderInfoId) throws Exception;
	
}
