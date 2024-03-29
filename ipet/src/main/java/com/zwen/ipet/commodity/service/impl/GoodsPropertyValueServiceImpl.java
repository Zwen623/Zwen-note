package com.zwen.ipet.commodity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zwen.ipet.commodity.dao.GoodsPropertyValueDAO;
import com.zwen.ipet.commodity.domain.GoodsPropertyValueDO;
import com.zwen.ipet.commodity.domain.GoodsPropertyValueDTO;
import com.zwen.ipet.commodity.service.GoodsPropertyValueService;
import com.zwen.ipet.common.util.DateProvider;
import com.zwen.ipet.common.util.ObjectUtils;

/**
 * 商品属性值管理service组件
 * @author zwen
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsPropertyValueServiceImpl implements GoodsPropertyValueService {

	/**
	 * 商品属性值管理DAO组件
	 */
	@Autowired
	private GoodsPropertyValueDAO propertyValueDAO;
	/**
	 * 日期辅助组件
	 */
	@Autowired
	private DateProvider dateProvider;
	
	/**
	 * 根据商品id查询属性值
	 * @param goodsId 商品id
	 * @return 属性值
	 */
	@Override
	public List<GoodsPropertyValueDTO> listByGoodsId(Long goodsId) throws Exception {
		return ObjectUtils.convertList(propertyValueDAO.listByGoodsId(goodsId), 
				GoodsPropertyValueDTO.class);
	}
	
	/**
	 * 新增商品属性值
	 * @param goodsPropertyValue 商品属性值
	 */
	@Override
	public void batchSave(List<GoodsPropertyValueDTO> propertyValues) throws Exception {
		for(GoodsPropertyValueDTO propertyValue : propertyValues) {
			propertyValue.setGmtCreate(dateProvider.getCurrentTime());
			propertyValue.setGmtModified(dateProvider.getCurrentTime());
			propertyValueDAO.save(propertyValue.clone(GoodsPropertyValueDO.class));   
		}
	}
	
	/**
	 * 根据商品id删除属性值
	 * @param goodsId 商品id
	 */
	@Override
	public void removeByGoodsId(Long goodsId) throws Exception {
		propertyValueDAO.removeByGoodsId(goodsId); 
	}
	
}
