package com.zwen.ipet.pay.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.zwen.ipet.common.constant.CollectionSize;
import com.zwen.ipet.common.util.ObjectUtils;
import com.zwen.ipet.pay.dao.PayTransactionDAO;
import com.zwen.ipet.pay.domain.PayTransactionDO;
import com.zwen.ipet.pay.domain.PayTransactionDTO;
import com.zwen.ipet.pay.domain.PayTransactionQuery;
import com.zwen.ipet.pay.service.PayTransactionService;

/**
 * 支付交易流水管理service组件
 * @author zwen
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PayTransactionServiceImpl implements PayTransactionService {
	
	/**
	 * 支付交易流水管理DAO组件
	 */
	@Autowired
	private PayTransactionDAO payTransactionDAO;

	/**
	 * 新增支付交易流水
	 * @param payTransaction 支付交易流水
	 */
	@Override
	public void save(PayTransactionDTO payTransaction) throws Exception {
		payTransactionDAO.save(payTransaction.clone(PayTransactionDO.class));  
	}
	
	/**
	 * 更新支付交易流水
	 * @param payTransaction 支付交易流水
	 * @throws Exception
	 */
	@Override
	public void update(PayTransactionDTO payTransaction) throws Exception {
		payTransactionDAO.update(payTransaction.clone(PayTransactionDO.class));  
	}
	
	/**
	 * 根据订单编号查询支付交易流水
	 * @param orderNo 订单编号
	 * @return 支付交易流水
	 * @throws Exception
	 */
	@Override
	public PayTransactionDTO getByOrderNo(String orderNo) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>(CollectionSize.DEFAULT);
		parameters.put("orderNo", orderNo);
		
		List<PayTransactionDO> payTransactions = payTransactionDAO.listByCondition(parameters);
		if(!CollectionUtils.isEmpty(payTransactions)) {
			return payTransactions.get(0).clone(PayTransactionDTO.class); 
		}
		
		return null;
	}
	
	/**
	 * 分页查询支付交易流水
	 * @param query 查询条件
	 * @return 支付交易流水
	 */
	@Override
	public List<PayTransactionDTO> listByPage(PayTransactionQuery query) throws Exception {
		return ObjectUtils.convertList(
				payTransactionDAO.listByPage(query), 
				PayTransactionDTO.class); 
	}
	
}
