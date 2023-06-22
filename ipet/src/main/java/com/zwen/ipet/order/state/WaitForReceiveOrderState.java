package com.zwen.ipet.order.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.order.constant.OrderStatus;
import com.zwen.ipet.order.dao.OrderInfoDAO;
import com.zwen.ipet.order.domain.OrderInfoDTO;

/**
 * 待收货状态
 * @author zwen
 *
 */
@Component
public class WaitForReceiveOrderState extends AbstractOrderState {

	@Autowired
	public WaitForReceiveOrderState(DateProvider dateProvider, OrderInfoDAO orderInfoDAO) {
		super(dateProvider, orderInfoDAO);
	}

	@Override
	protected Integer getOrderStatus(OrderInfoDTO order) throws Exception {
		return OrderStatus.WAIT_FOR_RECEIVE;
	}
	
	@Override
	public Boolean canConfirmReceipt(OrderInfoDTO order) throws Exception {
		return true;
	}

}
