package com.zwen.ipet.order.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.order.constant.ReturnGoodsApplyStatus;
import com.zwen.ipet.order.dao.ReturnGoodsApplyDAO;
import com.zwen.ipet.order.domain.ReturnGoodsApplyDO;
import com.zwen.ipet.order.mapper.ReturnGoodsApplyMapper;

/**
 * 退货申请管理DAO组件
 * @author zwen
 *
 */
@Repository
public class ReturnGoodsApplyDAOImpl implements ReturnGoodsApplyDAO {

	/**
	 * 退货申请管理mapper组件
	 */
	@Autowired
	private ReturnGoodsApplyMapper returnGoodsApplyMapper;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 新增退货申请
	 * @param apply 退货申请
	 */
	@Override
	public void save(ReturnGoodsApplyDO apply) throws Exception {
		apply.setReturnGoodsApplyStatus(ReturnGoodsApplyStatus.WAIT_FOR_APPROVE); 
		apply.setGmtCreate(dateProvider.getCurrentTime()); 
		apply.setGmtModified(dateProvider.getCurrentTime()); 
		returnGoodsApplyMapper.save(apply); 
	}
	
	/**
	 * 根据id查询退货申请
	 * @param id 退货申请id
	 * @return 退货申请
	 */
	@Override
	public ReturnGoodsApplyDO getByOrderInfoId(Long orderInfoId) throws Exception {
		return returnGoodsApplyMapper.getByOrderInfoId(orderInfoId);
	}
	
	/**
	 * 更新退货申请的状态
	 * @param apply 退货申请
	 */
	@Override
	public void update(ReturnGoodsApplyDO apply) throws Exception {
		apply.setGmtModified(dateProvider.getCurrentTime()); 
		returnGoodsApplyMapper.update(apply); 
	}
	
	/**
	 * 更新退货申请的状态
	 * @param orderInfoId 订单id
	 * @param returnGoodsApplyStatus 退货申请状态
	 * @throws Exception
	 */
	@Override
	public void updateStatus(Long orderInfoId, Integer returnGoodsApplyStatus) throws Exception {
		ReturnGoodsApplyDO apply = getByOrderInfoId(orderInfoId);
		apply.setReturnGoodsApplyStatus(returnGoodsApplyStatus); 
		returnGoodsApplyMapper.update(apply); 
	}
	
}
