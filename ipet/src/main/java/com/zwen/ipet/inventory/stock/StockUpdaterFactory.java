package com.zwen.ipet.inventory.stock;

/**
 * 库存更新命令工厂接口
 * @author zwen
 *
 */
public interface StockUpdaterFactory<T> { 

	/**
	 * 创建一个库存更新命令
	 * @param parameter 参数对象
	 * @return 库存更新命令
	 */
	StockUpdater create(T parameter);
	
}
