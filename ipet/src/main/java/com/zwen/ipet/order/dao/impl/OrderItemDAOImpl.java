package com.zwen.ipet.order.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zwen.ipet.order.dao.OrderItemDAO;
import com.zwen.ipet.order.domain.OrderItemDO;
import com.zwen.ipet.order.mapper.OrderItemMapper;

/**
 * 订单条目管理DAO组件
 * @author zwen
 *
 */
@Repository
public class OrderItemDAOImpl implements OrderItemDAO {

	/**
	 * 订单条目管理mapper组件
	 */
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	/**
	 * 新增订单条目
	 * @param orderItem
	 */
	@Override
	public Long save(OrderItemDO orderItem) throws Exception {
		orderItemMapper.save(orderItem);
		return orderItem.getId();
	}
	
	/**
	 * 查询订单条目
	 * @param orderInfoId 订单id
	 * @return 订单条目
	 */
	@Override
	public List<OrderItemDO> listByOrderInfoId(Long orderInfoId) throws Exception {
		return orderItemMapper.listByOrderInfoId(orderInfoId);
	}
	
}
