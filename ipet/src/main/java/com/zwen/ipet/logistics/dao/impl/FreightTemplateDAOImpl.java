package com.zwen.ipet.logistics.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.logistics.dao.FreightTemplateDAO;
import com.zwen.ipet.logistics.domain.FreightTemplateDO;
import com.zwen.ipet.logistics.domain.FreightTemplateQuery;
import com.zwen.ipet.logistics.mapper.FreightTemplateMapper;

/**
 * 运费模板管理DAO组件
 * @author zwen
 *
 */
@Repository
public class FreightTemplateDAOImpl implements FreightTemplateDAO {
	
	/**
	 * 运费模板管理mapper组件
	 */
	@Autowired
	private FreightTemplateMapper freightTemplateMapper;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 新增运费模板
	 * @param freightTemplate 运费模板
	 */
	@Override
	public void save(FreightTemplateDO freightTemplate) throws Exception {
		freightTemplate.setGmtCreate(dateProvider.getCurrentTime()); 
		freightTemplate.setGmtModified(dateProvider.getCurrentTime()); 
		freightTemplateMapper.save(freightTemplate); 
	}
	
	/**
	 * 分页查询运费模板
	 * @param query 运费模板查询条件
	 * @return 运费模板
	 */
	@Override
	public List<FreightTemplateDO> listByPage(FreightTemplateQuery query) throws Exception {
		return freightTemplateMapper.listByPage(query);
	}
	
	/**
	 * 根据id查询运费模板
	 * @param query 运费模板查询条件
	 * @return 运费模板
	 */
	@Override
	public FreightTemplateDO getById(Long id) throws Exception {
		return freightTemplateMapper.getById(id);
	}
	
	/**
	 * 更新运费模板
	 * @param freightTemplate 运费模板
	 */
	@Override
	public void update(FreightTemplateDO freightTemplate) throws Exception {
		freightTemplateMapper.update(freightTemplate); 
	}
	
}
