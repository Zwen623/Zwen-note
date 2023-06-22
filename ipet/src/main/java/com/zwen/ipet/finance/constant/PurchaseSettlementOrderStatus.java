package com.zwen.ipet.finance.constant;

/**
 * 采购结算单状态
 * @author zwen
 *
 */
public class PurchaseSettlementOrderStatus {

	/**
	 * 编辑中
	 */
	public static final Integer EDITING = 1;
	/**
	 * 待审核
	 */
	public static final Integer WAIT_FOR_APPROVE = 2;
	/**
	 * 已完成
	 */
	public static final Integer FINISHED = 3;
	
	private PurchaseSettlementOrderStatus() {
		
	}
	
}
