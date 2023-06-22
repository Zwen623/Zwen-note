package com.zwen.ipet.commodity.dao;

import java.util.List;

import com.zwen.ipet.commodity.domain.PropertyGroupDO;

/**
 * 属性分组管理DAO组件接口
 * @author zwen
 *
 */
public interface PropertyGroupDAO {

	/**
	 * 新增属性分组
	 * @param group 属性分组
	 * @return 属性分组id
	 * @throws Exception
	 */
	Long save(PropertyGroupDO group) throws Exception;
	
	/**
	 * 根据类目id查询属性分组
	 * @param categoryId 类目id
	 * @return 属性分组
	 * @throws Exception
	 */
	List<PropertyGroupDO> listByCategoryId(Long categoryId) throws Exception;
	
	/**
	 * 根据类目id删除属性分组
	 * @param categoryId 类目id
	 * @throws Exception
	 */
	void removeByCategoryId(Long categoryId) throws Exception;
	
}
