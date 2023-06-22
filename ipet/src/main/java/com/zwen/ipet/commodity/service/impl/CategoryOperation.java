package com.zwen.ipet.commodity.service.impl;

/**
 * 类目操作
 * @author zwen
 *
 */
public interface CategoryOperation<T> { 

	/**
	 * 执行类目操作
	 * @param category 类目树节点
	 * @return 处理结果
	 * @throws Exception
	 */
	T doExecute(Category category) throws Exception;
	
}
