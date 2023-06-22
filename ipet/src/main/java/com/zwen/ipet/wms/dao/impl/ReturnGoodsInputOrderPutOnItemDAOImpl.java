package com.zwen.ipet.wms.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.wms.dao.ReturnGoodsInputOrderPutOnItemDAO;
import com.zwen.ipet.wms.domain.ReturnGoodsInputOrderPutOnItemDO;
import com.zwen.ipet.wms.mapper.ReturnGoodsInputOrderPutOnItemMapper;

/**
 * 退货入库单上架条目管理DAO组件
 * @author zwen
 *
 */
@Repository
public class ReturnGoodsInputOrderPutOnItemDAOImpl implements ReturnGoodsInputOrderPutOnItemDAO {

	/**
	 * 上架条目管理mapper组件
	 */
	@Autowired
	private ReturnGoodsInputOrderPutOnItemMapper putOnItemMapper;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 根据退货入库单条目id查询上架条目
	 * @param returnGoodsInputOrderItemId 退货入库单条目id
	 * @return 上架条目
	 */
	@Override
	public List<ReturnGoodsInputOrderPutOnItemDO> listByReturnGoodsInputOrderItemId(
			Long returnGoodsInputOrderItemId) throws Exception {
		return putOnItemMapper.listByReturnGoodsInputOrderItemId(returnGoodsInputOrderItemId);
	}
	
	/**
	 * 新增退货入库单上架条目
	 * @param putOnItem 上架条目
	 */
	@Override
 	public void save(ReturnGoodsInputOrderPutOnItemDO putOnItem) throws Exception {
 		putOnItem.setGmtCreate(dateProvider.getCurrentTime());
 		putOnItem.setGmtModified(dateProvider.getCurrentTime()); 
 		putOnItemMapper.save(putOnItem); 
 	}
	
}
