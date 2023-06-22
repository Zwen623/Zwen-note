package com.zwen.ipet.inventory.stock;

/**
 * 商品库存更新命令的接口
 * @author zwen
 *
 */
public interface StockUpdater {

	/**
	 * 更新商品库存
	 * @return 处理结果
	 */
	Boolean updateGoodsStock();
	
}
