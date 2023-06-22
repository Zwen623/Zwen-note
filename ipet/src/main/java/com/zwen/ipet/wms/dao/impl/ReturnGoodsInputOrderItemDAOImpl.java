package com.zwen.ipet.wms.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.wms.dao.ReturnGoodsInputOrderItemDAO;
import com.zwen.ipet.wms.domain.ReturnGoodsInputOrderItemDO;
import com.zwen.ipet.wms.mapper.ReturnGoodsInputOrderItemMapper;

/**
 * 退货入库单条目管理DAO组件
 * @author zwen
 *
 */
@Repository
public class ReturnGoodsInputOrderItemDAOImpl implements ReturnGoodsInputOrderItemDAO {

	/**
	 * 退货入库单条目管理mapper组件
	 */
	@Autowired
	private ReturnGoodsInputOrderItemMapper returnGoodsInputOrderItemMapper;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 新增退货入库单条目
	 * @param returnGoodsInputOrderItem 退货入库单条目
	 */
	@Override
	public void save(ReturnGoodsInputOrderItemDO returnGoodsInputOrderItem) throws Exception {
		returnGoodsInputOrderItemMapper.save(returnGoodsInputOrderItem); 
	}
	
	/**
	 * 查询退货入库单条目
	 * @param returnGoodsInputOrderId 退货入库单id
	 * @return 退货入库单条目
	 */
	@Override
	public List<ReturnGoodsInputOrderItemDO> listByReturnGoodsInputOrderId(
			Long returnGoodsInputOrderId) throws Exception {
		return returnGoodsInputOrderItemMapper.listByReturnGoodsInputOrderId(returnGoodsInputOrderId);
	}
	
	/**
	 * 更新退货入库单条目
	 * @param returnGoodsInputOrderItem 退货入库单条目
	 */
	@Override
	public void update(ReturnGoodsInputOrderItemDO returnGoodsInputOrderItem) throws Exception {
		returnGoodsInputOrderItem.setGmtModified(dateProvider.getCurrentTime()); 
		returnGoodsInputOrderItemMapper.update(returnGoodsInputOrderItem); 
	}
	
}
