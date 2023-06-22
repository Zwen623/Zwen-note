package com.zwen.ipet.order.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.order.constant.OrderStatus;
import com.zwen.ipet.order.dao.OrderInfoDAO;
import com.zwen.ipet.order.domain.OrderInfoDTO;

/**
 * 退货已入库状态
 * @author zwen
 *
 */
@Component
public class FinishedInputReturnGoodsOrderState extends AbstractOrderState {

	@Autowired
	public FinishedInputReturnGoodsOrderState(DateProvider dateProvider, OrderInfoDAO orderInfoDAO) {
		super(dateProvider, orderInfoDAO);
	}
	
	@Override
	protected Integer getOrderStatus(OrderInfoDTO order) throws Exception {
		return OrderStatus.FINISHED_INPUT_RETURN_GOODS;
	}

}
