package com.zwen.ipet.logistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zwen.ipet.common.util.ObjectUtils;
import com.zwen.ipet.logistics.dao.FreightTemplateDAO;
import com.zwen.ipet.logistics.domain.FreightTemplateDO;
import com.zwen.ipet.logistics.domain.FreightTemplateDTO;
import com.zwen.ipet.logistics.domain.FreightTemplateQuery;
import com.zwen.ipet.logistics.service.FreightTemplateService;

/**
 * 运费模板管理service组件
 * @author zwen
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FreightTemplateServiceImpl implements FreightTemplateService {

	/**
	 * 运费模板管理DAO组件
	 */
	@Autowired
	private FreightTemplateDAO freightTemplateDAO;
	
	/**
	 * 新增运费模板
	 * @param freightTemplate 运费模板
	 */
	@Override
	public void save(FreightTemplateDTO freightTemplate) throws Exception {
		freightTemplateDAO.save(freightTemplate.clone(FreightTemplateDO.class));   
	}
	
	/**
	 * 分页查询运费模板
	 * @param query 运费模板查询条件
	 * @return 运费模板
	 */
	@Override
	public List<FreightTemplateDTO> listByPage(FreightTemplateQuery query) throws Exception {
		return ObjectUtils.convertList(
				freightTemplateDAO.listByPage(query), 
				FreightTemplateDTO.class);
	}
	
	/**
	 * 根据id查询运费模板
	 * @param query 运费模板查询条件
	 * @return 运费模板
	 */
	@Override
	public FreightTemplateDTO getById(Long id) throws Exception {
		return freightTemplateDAO.getById(id).clone(FreightTemplateDTO.class);
	}
	
	/**
	 * 更新运费模板
	 * @param freightTemplate 运费模板
	 */ 
	@Override
	public void update(FreightTemplateDTO freightTemplate) throws Exception {
		freightTemplateDAO.update(freightTemplate.clone(FreightTemplateDO.class)); 
	}
	
}
