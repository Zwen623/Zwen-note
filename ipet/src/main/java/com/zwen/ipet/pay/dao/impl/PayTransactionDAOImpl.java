package com.zwen.ipet.pay.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.pay.dao.PayTransactionDAO;
import com.zwen.ipet.pay.domain.PayTransactionDO;
import com.zwen.ipet.pay.domain.PayTransactionQuery;
import com.zwen.ipet.pay.mapper.PayTransactionMapper;

/**
 * 支付交易流水管理DAO组件
 * @author zwen
 *
 */
@Repository
public class PayTransactionDAOImpl implements PayTransactionDAO {

	/**
	 * 支付交易流水管理mapper组件
	 */
	@Autowired
	private PayTransactionMapper payTransactionMapper;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 新增支付交易流水
	 * @param payTransaction 支付交易流水
	 */
	@Override
	public void save(PayTransactionDO payTransaction) throws Exception {
		payTransaction.setGmtCreate(dateProvider.getCurrentTime());
		payTransaction.setGmtModified(dateProvider.getCurrentTime()); 
		payTransactionMapper.save(payTransaction); 
	}
	
	/**
	 * 根据条件查询支付交易流水
	 * @param parameters 查询条件
	 * @return 支付交易流水
	 * @throws Exception
	 */
	@Override
	public List<PayTransactionDO> listByCondition(Map<String, Object> parameters) throws Exception {
		return payTransactionMapper.listByCondition(parameters);
	}
	
	/**
	 * 更新支付交易流水
	 * @param payTransaction 支付交易流水
	 */
	@Override
	public void update(PayTransactionDO payTransaction) throws Exception {
		payTransaction.setGmtModified(dateProvider.getCurrentTime()); 
		payTransactionMapper.update(payTransaction); 
	}
	
	/**
	 * 分页查询支付交易流水
	 * @param query 查询条件
	 * @return 支付交易流水
	 */
	@Override
	public List<PayTransactionDO> listByPage(PayTransactionQuery query) throws Exception {
		return payTransactionMapper.listByPage(query);
	}
	
}
