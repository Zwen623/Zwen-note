package com.zwen.ipet.auth.service.impl;

/**
 * 权限操作接口
 * @author zwen
 *
 */
public interface PriorityOperation<T> { 
 
	/**
	 * 执行这个操作
	 * @param priority 权限
	 * @return 处理结果
	 * @throws Exception
	 */
	T doExecute(Priority priority) throws Exception;
	
}
